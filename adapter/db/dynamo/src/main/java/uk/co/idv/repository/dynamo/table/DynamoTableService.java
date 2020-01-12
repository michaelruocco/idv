package uk.co.idv.repository.dynamo.table;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.UpdateTimeToLiveRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
public class DynamoTableService {

    private static final Map<String, Table> tables = new HashMap<>();

    private final AmazonDynamoDB amazonDynamoDB;
    private final DynamoTableCreator tableCreator;

    public DynamoTableService(final AmazonDynamoDB amazonDynamoDB) {
        this(amazonDynamoDB, new DynamoTableCreator(amazonDynamoDB));
    }

    public Table getOrCreateTable(final CreateTableRequest request) {
        final String tableName = request.getTableName();
        if (tables.containsKey(tableName)) {
            log.info("table {} already created, returning from cache", tableName);
            return tables.get(tableName);
        }
        final Table table = tableCreator.create(request);
        tables.put(request.getTableName(), table);
        return table;
    }

    public void addTimeToLive(final UpdateTimeToLiveRequest request) {
        log.info("adding time to live to table {}", request.getTableName());
        amazonDynamoDB.updateTimeToLive(request);
    }

}
