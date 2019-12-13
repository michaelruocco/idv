package uk.co.idv.repository.dynamo.identity;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

@Builder
@Slf4j
public class TableCreator {

    private final AmazonDynamoDB amazonDynamoDB;
    private final DynamoDBMapper mapper;

    public void create() {
        try {
            CreateTableRequest request = mapper.generateCreateTableRequest(IdentityDocument.class)
                    .withProvisionedThroughput(new ProvisionedThroughput(1L, 1L));
            log.info("creating table with request {}", request);
            TableUtils.createTableIfNotExists(amazonDynamoDB, request);
            TableUtils.waitUntilActive(amazonDynamoDB, request.getTableName());
        } catch (final InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new DynamoTableCreationException(e);
        }
    }

    private static class DynamoTableCreationException extends RuntimeException {

        private DynamoTableCreationException(final Throwable cause) {
            super(cause);
        }

    }

}
