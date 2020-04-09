package uk.co.idv.dynamo.ttl;

import com.amazonaws.services.dynamodbv2.model.TimeToLiveSpecification;
import com.amazonaws.services.dynamodbv2.model.UpdateTimeToLiveRequest;

public class DefaultTimeToLiveRequest extends UpdateTimeToLiveRequest {

    public DefaultTimeToLiveRequest(final String tableName) {
        setTableName(tableName);
        withTimeToLiveSpecification(buildSpec());
    }

    private TimeToLiveSpecification buildSpec() {
        final TimeToLiveSpecification spec = new TimeToLiveSpecification();
        spec.setAttributeName("ttl");
        spec.setEnabled(true);
        return spec;
    }

}
