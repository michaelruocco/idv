package uk.co.idv.repository.dynamo.identity;

import com.amazonaws.services.dynamodbv2.model.Projection;
import com.amazonaws.services.dynamodbv2.model.ProjectionType;

public class IdentityProjection extends Projection {

    public IdentityProjection() {
        withProjectionType(ProjectionType.INCLUDE);
        withNonKeyAttributes("body");
    }

}
