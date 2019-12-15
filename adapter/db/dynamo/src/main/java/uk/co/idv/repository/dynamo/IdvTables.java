package uk.co.idv.repository.dynamo;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
import lombok.Builder;
import uk.co.idv.repository.dynamo.identity.alias.AliasMappingDocument;

import java.util.Collection;
import java.util.Collections;

@Builder
public class IdvTables {

    private final DynamoDBMapper mapper;
    private final DynamoTableCreator tableCreator;

    public void create() {
        createIdentityTable();
        createVerificationContextTable();
    }

    private void createIdentityTable() {
        final ProvisionedThroughput throughput = new ProvisionedThroughput(1L, 1L);
        final CreateTableRequest request = mapper.generateCreateTableRequest(AliasMappingDocument.class)
                .withProvisionedThroughput(throughput);
        request.getGlobalSecondaryIndexes().get(0).setProvisionedThroughput(throughput);
        tableCreator.create(request);
    }

    private void createVerificationContextTable() {
        Collection<KeySchemaElement> key = Collections.singleton(
                new KeySchemaElement().withAttributeName("id").withKeyType(KeyType.HASH)
        );

        Collection<AttributeDefinition> attributes = Collections.singleton(
                new AttributeDefinition().withAttributeName("id").withAttributeType(ScalarAttributeType.S)//,
        );

        CreateTableRequest request = new CreateTableRequest()
                .withTableName("dev-verification-context")
                .withKeySchema(key)
                .withAttributeDefinitions(attributes)
                .withProvisionedThroughput(new ProvisionedThroughput(1L, 1L));
        tableCreator.create(request);
    }

}
