package uk.co.idv.repository.dynamo.identity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Data;

@DynamoDBTable(tableName = "identity-mapping")
@Data
public class AliasMappingDocument {

    @DynamoDBHashKey
    private String alias;

    @DynamoDBIndexHashKey(globalSecondaryIndexName = "idv-id-index")
    private String idvId;

}
