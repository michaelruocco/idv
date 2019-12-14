package uk.co.idv.repository.dynamo;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import lombok.Builder;
import uk.co.idv.repository.dynamo.identity.AliasMappingDocument;

@Builder
public class IdvTables {

    private final DynamoDBMapper mapper;
    private final DynamoTableCreator tableCreator;

    public void create() {
        createIdentityTable();
    }

    private void createIdentityTable() {
        final ProvisionedThroughput throughput = new ProvisionedThroughput(1L, 1L);
        final CreateTableRequest request = mapper.generateCreateTableRequest(AliasMappingDocument.class)
                .withProvisionedThroughput(throughput);
        request.getGlobalSecondaryIndexes().get(0).setProvisionedThroughput(throughput);
        tableCreator.create(request);
    }

}
