package uk.co.idv.dynamo.ttl;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.AmazonDynamoDBException;
import com.amazonaws.services.dynamodbv2.model.UpdateTimeToLiveRequest;
import com.amazonaws.services.dynamodbv2.model.UpdateTimeToLiveResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public class DynamoTimeToLiveService {

    private final AmazonDynamoDB client;

    public Optional<UpdateTimeToLiveResult> updateTimeToLive(final UpdateTimeToLiveRequest request) {
        try {
            log.info("adding time to live to table {}", request.getTableName());
            return Optional.of(client.updateTimeToLive(request));
        } catch (final AmazonDynamoDBException e) {
            if (isAlreadyExistsException(e)) {
                log(e);
                return Optional.empty();
            }
            throw e;
        }
    }

    private boolean isAlreadyExistsException(final AmazonDynamoDBException e) {
        return e.getErrorMessage().equals("TimeToLive is already enabled");
    }

    private void log(final AmazonDynamoDBException e) {
        log.debug(e.getErrorMessage(), e);
        log.warn(e.getErrorMessage());
    }


}
