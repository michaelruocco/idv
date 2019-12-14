package uk.co.idv.app;

import lombok.extern.slf4j.Slf4j;
import org.junit.Rule;
import org.junit.contrib.java.lang.system.RestoreSystemProperties;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static uk.co.idv.app.environment.WithEnvironmentVariables.withEnvironmentVariable;


@Testcontainers
@Slf4j
class ApplicationTest {

    @Rule
    public final RestoreSystemProperties restore = new RestoreSystemProperties();

    @Container
    public final GenericContainer dynamo = new GenericContainer<>("amazon/dynamodb-local:latest")
            .withExposedPorts(8000);

    @Test
    void shouldStartupWithStubProfile() {
        setSpringProfiles("stub, uk");
        setRandomServerPort();

        Application.main(new String[0]);
    }

    @Test
    void shouldStartupWithInMemoryMongoProfile() {
        setSpringProfiles("in-memory-mongo, uk");
        setRandomServerPort();
        withEnvironmentVariable("DYNAMO_DB_URI", buildDynamoDbUri())
                .execute(() -> {
                            Application.main(new String[0]);
                        }
                );
    }

    private String buildDynamoDbUri() {
        final String uri = String.format("http://%s:%s", dynamo.getContainerIpAddress(), dynamo.getFirstMappedPort());
        log.info("connecting to dynamo db using uri {}", uri);
        return uri;
    }

    private static void setSpringProfiles(final String profiles) {
        System.setProperty("spring.profiles.active", profiles);
    }

    private static void setRandomServerPort() {
        System.setProperty("server.port", "0");
    }

}
