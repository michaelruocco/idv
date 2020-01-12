package uk.co.idv.repository.dynamo.table;

import com.amazonaws.services.dynamodbv2.model.TimeToLiveSpecification;
import com.amazonaws.services.dynamodbv2.model.UpdateTimeToLiveRequest;

public class IdvTimeToLiveRequest extends UpdateTimeToLiveRequest {

    public IdvTimeToLiveRequest(final String tableName) {
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
