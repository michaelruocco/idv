package uk.co.idv.repository.dynamo;

import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;

public interface CreateTableRequestFactory {

    CreateTableRequest build();

}
