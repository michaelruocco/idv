package uk.co.idv.repository.dynamo.onetimepasscode;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.domain.usecases.onetimepasscode.OneTimePasscodeVerificationDao;
import uk.co.idv.dynamo.ttl.DefaultTimeToLiveRequest;
import uk.co.idv.dynamo.table.DynamoTableCreator;
import uk.co.idv.dynamo.table.DynamoTableService;
import uk.co.idv.dynamo.ttl.DynamoTimeToLiveService;
import uk.co.idv.dynamo.ttl.EpochSecondProvider;
import uk.co.idv.dynamo.ttl.OneHourTimeToLiveCalculator;
import uk.co.idv.utils.aws.system.AwsSystemProperties;
import uk.co.idv.utils.json.converter.JsonConverter;

@RequiredArgsConstructor
@Slf4j
public class OneTimePasscodeDynamoConfig {

    private final String environment;
    private final DynamoTableService tableService;
    private final DynamoTimeToLiveService timeToLiveService;

    public OneTimePasscodeDynamoConfig(final AmazonDynamoDB client) {
        this(AwsSystemProperties.loadEnvironment(),
                new DynamoTableService(new DynamoTableCreator(client)),
                new DynamoTimeToLiveService(client)
        );
    }

    public OneTimePasscodeVerificationDao verificationDao(final JsonConverter jsonConverter,
                                                          final EpochSecondProvider epochSecondProvider) {
        final CreateTableRequest createTableRequest = new OneTimePasscodeVerificationCreateTableRequest(environment);
        final OneTimePasscodeVerificationItemConverter itemConverter = verificationItemConverter(jsonConverter, epochSecondProvider);
        final OneTimePasscodeVerificationDao dao = DynamoOneTimePasscodeVerificationDao.builder()
                .itemConverter(itemConverter)
                .table(tableService.getOrCreateTable(createTableRequest))
                .build();
        timeToLiveService.updateTimeToLive(new DefaultTimeToLiveRequest(createTableRequest.getTableName()));
        return dao;
    }

    private OneTimePasscodeVerificationItemConverter verificationItemConverter(final JsonConverter jsonConverter,
                                                                               final EpochSecondProvider epochSecondProvider) {
        return OneTimePasscodeVerificationItemConverter.builder()
                .jsonConverter(jsonConverter)
                .timeToLiveCalculator(new OneHourTimeToLiveCalculator(epochSecondProvider))
                .build();
    }

}
