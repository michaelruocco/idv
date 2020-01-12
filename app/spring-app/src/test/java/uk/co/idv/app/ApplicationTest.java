package uk.co.idv.app;

import lombok.extern.slf4j.Slf4j;
import org.junit.Rule;
import org.junit.contrib.java.lang.system.RestoreSystemProperties;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@Slf4j
class ApplicationTest {

    @Container
    public static final DynamoDbLocalContainer DYNAMO_DB = new DynamoDbLocalContainer();

    @Rule
    public final RestoreSystemProperties restore = new RestoreSystemProperties();

    @Test
    void shouldStartupWithStubProfile() {
        setSpringProfiles("stub, uk");
        setRandomServerPort();

        Application.main(new String[0]);
    }

    @Test
    void shouldStartupWithUkProfile() {
        setSpringProfiles("uk");
        setRandomServerPort();
        setDynamoDbProperties();

        Application.main(new String[0]);
    }

    private static void setSpringProfiles(final String profiles) {
        System.setProperty("spring.profiles.active", profiles);
    }

    private static void setRandomServerPort() {
        System.setProperty("server.port", "0");
    }

    private void setDynamoDbProperties() {
        System.setProperty("aws.dynamo.db.endpoint.uri", buildDynamoDbEndpointUri());
        System.setProperty("aws.accessKeyId", "abc");
        System.setProperty("aws.secretKey", "123");
    }

    private static String buildDynamoDbEndpointUri() {
        final String uri = String.format("http://%s:%s", DYNAMO_DB.getContainerIpAddress(), DYNAMO_DB.getFirstMappedPort());
        log.info("connecting to dynamo db using uri {}", uri);
        return uri;
    }

}
