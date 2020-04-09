package uk.co.idv.dynamo.table;

import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;

public class DefaultProvisionedThroughput extends ProvisionedThroughput {

    public DefaultProvisionedThroughput() {
        super(1L, 1L);
    }

}
