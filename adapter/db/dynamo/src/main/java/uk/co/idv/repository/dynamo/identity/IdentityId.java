package uk.co.idv.repository.dynamo.identity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Data;

import java.io.Serializable;

@DynamoDBTable(tableName = "identity-alias")
@Data
public class IdentityId implements Serializable {

    @DynamoDBHashKey
    private String alias;

    @DynamoDBRangeKey
    @DynamoDBIndexHashKey(globalSecondaryIndexName = "idv-id-index")
    private String idvId;

}
