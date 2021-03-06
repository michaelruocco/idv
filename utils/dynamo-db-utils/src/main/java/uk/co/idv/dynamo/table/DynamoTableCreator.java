package uk.co.idv.dynamo.table;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class DynamoTableCreator {

    private final AmazonDynamoDB client;

    public Table create(final CreateTableRequest request) {
        try {
            final String tableName = request.getTableName();
            log.info("creating table {}", tableName);
            TableUtils.createTableIfNotExists(client, request);
            TableUtils.waitUntilActive(client, tableName);
            return new DynamoDB(client).getTable(tableName);
        } catch (final InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new DynamoTableCreationException(e);
        }
    }

    public static class DynamoTableCreationException extends RuntimeException {

        public DynamoTableCreationException(final Throwable cause) {
            super(cause);
        }

    }

}
