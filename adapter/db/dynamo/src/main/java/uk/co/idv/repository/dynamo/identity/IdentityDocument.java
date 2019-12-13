package uk.co.idv.repository.dynamo.identity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import org.springframework.data.annotation.Id;

@DynamoDBTable(tableName = "identity-alias")
public class IdentityDocument {

    @Id
    private IdentityId identityId;

    @DynamoDBHashKey(attributeName = "alias")
    public String getAlias() {
        return identityId != null ? identityId.getAlias() : null;
    }

    public void setAlias(final String alias) {
        createIdentityIdIfNull();
        identityId.setAlias(alias);
    }

    @DynamoDBRangeKey(attributeName = "idvId")
    public String getIdvId() {
        return identityId != null ? identityId.getIdvId() : null;
    }

    public void setIdvId(final String idvId) {
        createIdentityIdIfNull();
        identityId.setIdvId(idvId);
    }

    private void createIdentityIdIfNull() {
        if (identityId == null) {
            identityId = new IdentityId();
        }
    }

}
