package uk.co.idv.repository.dynamo;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.repository.dynamo.identity.alias.AliasMappingDocument;

import java.util.Collections;

@Builder
@Slf4j
public class IdvTables {

    private final DynamoDBMapper mapper;
    private final AmazonDynamoDB amazonDynamoDB;

    @Builder.Default
    private final String environment = AwsEnvironmentVariables.loadEnvironment();

    public void create() {
        createIdentityMappingTable();
        createVerificationContextTable();
        createVerificationAttemptsTable();
    }

    public Table getIdentityMapping() {
        return getTable(prefixEnvironment("identity-mapping"));
    }

    public Table getVerificationContext() {
        return getTable(prefixEnvironment("verification-context"));
    }

    public Table getVerificationAttempts() {
        return getTable(prefixEnvironment("verification-attempts"));
    }

    private void createIdentityMappingTable() {
        final ProvisionedThroughput throughput = new ProvisionedThroughput(1L, 1L);
        final CreateTableRequest request = mapper.generateCreateTableRequest(AliasMappingDocument.class)
                .withProvisionedThroughput(throughput);
        request.getGlobalSecondaryIndexes().get(0).setProvisionedThroughput(throughput);
        create(request);
    }

    private void createVerificationContextTable() {
        final String name = String.format("%s-verification-context", environment);
        createStandardTable(name, "id");
    }

    private void createVerificationAttemptsTable() {
        final String name = String.format("%s-verification-attempts", environment);
        createStandardTable(name, "id");
    }

    private void createStandardTable(final String tableName, final String idAttributeName) {
        final KeySchemaElement key = new KeySchemaElement()
                        .withAttributeName(idAttributeName)
                        .withKeyType(KeyType.HASH);

        final AttributeDefinition attribute = new AttributeDefinition()
                        .withAttributeName(idAttributeName)
                        .withAttributeType(ScalarAttributeType.S);

        final CreateTableRequest request = new CreateTableRequest()
                .withTableName(tableName)
                .withKeySchema(Collections.singleton(key))
                .withAttributeDefinitions(Collections.singleton(attribute))
                .withProvisionedThroughput(new ProvisionedThroughput(1L, 1L));

        create(request);
    }

    private void create(final CreateTableRequest request) {
        try {
            log.info("creating table with request {}", request);
            TableUtils.createTableIfNotExists(amazonDynamoDB, request);
            TableUtils.waitUntilActive(amazonDynamoDB, request.getTableName());
        } catch (final InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new DynamoTableCreationException(e);
        }
    }

    private Table getTable(final String name) {
        return new DynamoDB(amazonDynamoDB).getTable(name);
    }

    private String prefixEnvironment(final String name) {
        return String.format("%s-%s", environment, name);
    }

    public static class DynamoTableCreationException extends RuntimeException {

        public DynamoTableCreationException(final Throwable cause) {
            super(cause);
        }

    }

}
