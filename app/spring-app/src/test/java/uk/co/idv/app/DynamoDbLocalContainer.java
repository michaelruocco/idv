package uk.co.idv.app;

import org.testcontainers.containers.GenericContainer;

public class DynamoDbLocalContainer extends GenericContainer<DynamoDbLocalContainer> {

    public DynamoDbLocalContainer() {
        super("amazon/dynamodb-local:latest");
        withExposedPorts(8000);
    }

}
