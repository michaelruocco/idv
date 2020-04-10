package uk.co.idv.repository.dynamo.lockout.policy;

import com.amazonaws.services.dynamodbv2.model.Projection;
import com.amazonaws.services.dynamodbv2.model.ProjectionType;

public class IncludeBodyProjection extends Projection {

    public IncludeBodyProjection() {
        withProjectionType(ProjectionType.INCLUDE);
        withNonKeyAttributes("body");
    }

}
