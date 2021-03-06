package uk.co.idv.app;

import lombok.extern.slf4j.Slf4j;
import org.junit.Rule;
import org.junit.contrib.java.lang.system.RestoreSystemProperties;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import uk.co.idv.dynamo.test.DynamoDbLocalContainer;
import uk.co.idv.onetimepasscode.sender.sns.SnsLocalContainer;
import uk.co.idv.repository.redis.verificationcontext.RedissonLocalContainer;

@Testcontainers
@Slf4j
class ApplicationTest {

    @Container
    public static final DynamoDbLocalContainer DYNAMO_DB = new DynamoDbLocalContainer();

    @Container
    public static final SnsLocalContainer SNS = new SnsLocalContainer();

    @Container
    public static final RedissonLocalContainer REDIS = new RedissonLocalContainer();

    @Rule
    public final RestoreSystemProperties restore = new RestoreSystemProperties();

    @Test
    void shouldStartupWithStubProfile() {
        setSpringProfiles("stubbed, uk");
        setRandomServerPort();

        Application.main(new String[0]);
    }

    @Test
    void shouldStartupWithUkProfile() {
        setSpringProfiles("uk");
        setRandomServerPort();
        setProperties();

        Application.main(new String[0]);
    }

    private static void setSpringProfiles(final String profiles) {
        System.setProperty("spring.profiles.active", profiles);
    }

    private static void setRandomServerPort() {
        System.setProperty("server.port", "0");
    }

    private void setProperties() {
        System.setProperty("aws.accessKeyId", "abc");
        System.setProperty("aws.secretKey", "123");
        System.setProperty("aws.dynamo.db.endpoint.uri", DYNAMO_DB.buildEndpointUri());
        System.setProperty("aws.sns.endpoint.uri", SNS.buildEndpointUri());
        System.setProperty("redis.endpoint.uri", REDIS.buildEndpointUri());
    }

}
