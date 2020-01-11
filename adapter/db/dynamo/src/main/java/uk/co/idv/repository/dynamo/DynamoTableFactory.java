package uk.co.idv.repository.dynamo;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.UpdateTimeToLiveRequest;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
public class DynamoTableFactory {

    private static final Map<String, Table> tables = new HashMap<>();

    private final AmazonDynamoDB amazonDynamoDB;

    public Table createOrGetTable(final CreateTableRequest request) {
        if (tables.containsKey(request.getTableName())) {
            return tables.get(request.getTableName());
        }
        final Table table = create(request);
        tables.put(request.getTableName(), table);
        return table;
    }

    private Table create(final CreateTableRequest request) {
        try {
            final String tableName = request.getTableName();
            log.info("creating table {}", tableName);
            TableUtils.createTableIfNotExists(amazonDynamoDB, request);
            TableUtils.waitUntilActive(amazonDynamoDB, tableName);
            return new DynamoDB(amazonDynamoDB).getTable(tableName);
        } catch (final InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new DynamoTableCreationException(e);
        }
    }

    public void addTimeToLive(final UpdateTimeToLiveRequest request) {
        log.info("adding time to live request to table {}", request.getTableName());
        amazonDynamoDB.updateTimeToLive(request);
    }

    public static class DynamoTableCreationException extends RuntimeException {

        public DynamoTableCreationException(final Throwable cause) {
            super(cause);
        }

    }

}
