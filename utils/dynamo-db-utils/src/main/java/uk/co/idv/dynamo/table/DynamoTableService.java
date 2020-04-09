package uk.co.idv.dynamo.table;

import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public class DynamoTableService {

    private final Map<String, Table> tables = new HashMap<>();
    private final DynamoTableCreator tableCreator;

    public Table getOrCreateTable(final CreateTableRequest request) {
        final String tableName = request.getTableName();
        return getTable(tableName).orElseGet(() -> createTable(request));
    }

    public void recreateTable(final CreateTableRequest request) {
        final Optional<Table> table = getTable(request.getTableName());
        if (table.isPresent()) {
            deleteAndRecreate(table.get(), request);
            return;
        }
        createTable(request);
    }

    private void deleteAndRecreate(final Table table, final CreateTableRequest request) {
        delete(table);
        createTable(request);
    }

    private void delete(final Table table) {
        table.delete();
        tables.remove(table.getTableName());
    }

    private Optional<Table> getTable(final String name) {
        if (tables.containsKey(name)) {
            log.info("table {} already created, returning from cache", name);
            return Optional.of(tables.get(name));
        }
        return Optional.empty();
    }

    private Table createTable(final CreateTableRequest request) {
        final Table table = tableCreator.create(request);
        tables.put(request.getTableName(), table);
        return table;
    }

}
