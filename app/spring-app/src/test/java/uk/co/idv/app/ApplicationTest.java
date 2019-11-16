package uk.co.idv.app;

import org.junit.Rule;
import org.junit.contrib.java.lang.system.EnvironmentVariables;
import org.junit.contrib.java.lang.system.RestoreSystemProperties;
import org.junit.jupiter.api.Test;

class ApplicationTest {

    @Rule
    public final RestoreSystemProperties restore = new RestoreSystemProperties();

    @Rule
    public final EnvironmentVariables environmentVariables  = new EnvironmentVariables();

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

        Application.main(new String[0]);
    }

    private static void setSpringProfiles(final String profiles) {
        System.setProperty("spring.profiles.active", profiles);
    }

    private static void setRandomServerPort() {
        System.setProperty("server.port", "0");
    }

}
