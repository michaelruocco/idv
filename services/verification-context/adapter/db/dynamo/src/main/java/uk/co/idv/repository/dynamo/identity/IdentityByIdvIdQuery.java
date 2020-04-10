package uk.co.idv.repository.dynamo.identity;

import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;

public class IdentityByIdvIdQuery extends QuerySpec {

    public IdentityByIdvIdQuery(final String idvId) {
        withKeyConditionExpression("idvId = :idvId");
        withValueMap(toValueMap(idvId));
    }

    private static ValueMap toValueMap(final String idvId) {
        return new ValueMap().withString(":idvId", idvId);
    }

}
