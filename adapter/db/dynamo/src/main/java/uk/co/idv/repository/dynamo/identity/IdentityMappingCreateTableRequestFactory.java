package uk.co.idv.repository.dynamo.identity;

import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.GlobalSecondaryIndex;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.Projection;
import com.amazonaws.services.dynamodbv2.model.ProjectionType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
import uk.co.idv.repository.dynamo.CreateTableRequestFactory;

import java.util.Arrays;
import java.util.Collections;

public class IdentityMappingCreateTableRequestFactory implements CreateTableRequestFactory {

    private final String tableName;

    public IdentityMappingCreateTableRequestFactory(final String environment) {
        this.tableName = environment + "-identity-mapping";
    }

    @Override
    public CreateTableRequest build() {
        final KeySchemaElement key = new KeySchemaElement("alias", KeyType.HASH);

        final AttributeDefinition alias = new AttributeDefinition(key.getAttributeName(), ScalarAttributeType.S);
        final AttributeDefinition idvId = new AttributeDefinition("idvId", ScalarAttributeType.S);

        return new CreateTableRequest()
                .withTableName(tableName)
                .withKeySchema(Collections.singleton(key))
                .withAttributeDefinitions(Arrays.asList(alias, idvId))
                .withGlobalSecondaryIndexes(buildIdvIdIndex(idvId))
                .withProvisionedThroughput(new ProvisionedThroughput(1L, 1L));
    }

    private static GlobalSecondaryIndex buildIdvIdIndex(final AttributeDefinition idvId) {
        return new GlobalSecondaryIndex()
                .withIndexName("idvIdIndex")
                .withKeySchema(new KeySchemaElement(idvId.getAttributeName(), KeyType.HASH))
                .withProjection(new Projection().withProjectionType(ProjectionType.KEYS_ONLY))
                .withProvisionedThroughput(new ProvisionedThroughput(1L, 1L));
    }

}
