package uk.co.idv.repository.dynamo;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Index;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.GlobalSecondaryIndex;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.Projection;
import com.amazonaws.services.dynamodbv2.model.ProjectionType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.repository.dynamo.identity.alias.AliasMappingDocument;

import java.util.Arrays;
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
        createLockoutPolicyTable();
    }

    public Table getVerificationContext() {
        return getTable(prefixEnvironment(Names.VERIFICATION_CONTEXT));
    }

    public Table getVerificationAttempts() {
        return getTable(prefixEnvironment(Names.VERIFICATION_ATTEMPTS));
    }

    public Table getLockoutPolicies() {
        return getTable(prefixEnvironment(Names.LOCKOUT_POLICIES));
    }

    public Index getLockoutPoliciesChannelIdIndex() {
        return getLockoutPolicies().getIndex("channel-id-index");
    }

    private void createIdentityMappingTable() {
        final ProvisionedThroughput throughput = new ProvisionedThroughput(1L, 1L);
        final CreateTableRequest request = mapper.generateCreateTableRequest(AliasMappingDocument.class)
                .withProvisionedThroughput(throughput);
        request.getGlobalSecondaryIndexes().get(0).setProvisionedThroughput(throughput);
        create(request);
    }

    private void createVerificationContextTable() {
        final String name = String.format("%s-%s", environment, Names.VERIFICATION_CONTEXT);
        createStandardTable(name, "id");
    }

    private void createVerificationAttemptsTable() {
        final String name = String.format("%s-%s", environment, Names.VERIFICATION_ATTEMPTS);
        createStandardTable(name, "id");
    }

    private void createLockoutPolicyTable() {
        final String tableName = String.format("%s-%s", environment, Names.LOCKOUT_POLICIES);

        final String idAttributeName = "id";
        final KeySchemaElement key = new KeySchemaElement()
                .withAttributeName(idAttributeName)
                .withKeyType(KeyType.HASH);

        final AttributeDefinition attribute = new AttributeDefinition()
                .withAttributeName(idAttributeName)
                .withAttributeType(ScalarAttributeType.S);

        final GlobalSecondaryIndex index = new GlobalSecondaryIndex()
                .withIndexName("channel-id-index")
                .withKeySchema(new KeySchemaElement()
                        .withAttributeName("channelId")
                        .withKeyType(KeyType.HASH))
                .withProjection(new Projection().withProjectionType(ProjectionType.KEYS_ONLY))
                .withProvisionedThroughput(new ProvisionedThroughput(1L, 1L));

        final AttributeDefinition channelAttribute = new AttributeDefinition()
                .withAttributeName("channelId")
                .withAttributeType(ScalarAttributeType.S);

        final CreateTableRequest request = new CreateTableRequest()
                .withTableName(tableName)
                .withKeySchema(Collections.singleton(key))
                .withAttributeDefinitions(Arrays.asList(attribute, channelAttribute))
                .withGlobalSecondaryIndexes(index)
                .withProvisionedThroughput(new ProvisionedThroughput(1L, 1L));

        create(request);
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

    public interface Names {

        String IDENTITY_MAPPING = "identity-mapping";
        String VERIFICATION_CONTEXT = "verification-context";
        String VERIFICATION_ATTEMPTS = "verification-attempts";
        String LOCKOUT_POLICIES = "lockout-policies";

    }

}
