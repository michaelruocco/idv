package uk.co.idv.repository.dynamo.identity.alias;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Data;
import uk.co.idv.repository.dynamo.IdvTables;

@DynamoDBTable(tableName = IdvTables.Names.IDENTITY_MAPPING)
@Data
public class AliasMappingDocument {

    @DynamoDBHashKey
    private String alias;

    @DynamoDBIndexHashKey(globalSecondaryIndexName = "idv-id-index")
    private String idvId;

}
