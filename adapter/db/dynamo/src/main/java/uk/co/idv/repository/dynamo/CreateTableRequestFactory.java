package uk.co.idv.repository.dynamo;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public abstract class AbstractDynamoTableFactory implements DynamoTableFactory {

    private final AmazonDynamoDB amazonDynamoDB;
    private final String environment;

    private Table table;

    @Override
    public Table createOrGetTable() {
        if (table == null) {
            table = create(buildCreateTableRequest());
        }
        return table;
    }

    protected abstract CreateTableRequest buildCreateTableRequest();

    protected String prefixEnvironment(final String name) {
        return String.format("%s-%s", environment, name);
    }

    private Table create(final CreateTableRequest request) {
        try {
            log.info("creating table with request {}", request);
            TableUtils.createTableIfNotExists(amazonDynamoDB, request);
            TableUtils.waitUntilActive(amazonDynamoDB, request.getTableName());
            return new DynamoDB(amazonDynamoDB).getTable(request.getTableName());
        } catch (final InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IdvTables.DynamoTableCreationException(e);
        }
    }

}
