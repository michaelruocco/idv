package uk.co.idv.repository.dynamo.identity;

import com.amazonaws.services.dynamodbv2.model.Projection;
import com.amazonaws.services.dynamodbv2.model.ProjectionType;

public class KeysOnlyProjection extends Projection {

    public KeysOnlyProjection() {
        withProjectionType(ProjectionType.KEYS_ONLY);
    }

}
