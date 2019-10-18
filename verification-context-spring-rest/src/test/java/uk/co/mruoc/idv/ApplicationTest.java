package uk.co.mruoc.idv;

import org.junit.Rule;
import org.junit.contrib.java.lang.system.RestoreSystemProperties;
import org.junit.jupiter.api.Test;

class ApplicationTest {

    @Rule
    public final RestoreSystemProperties restore = new RestoreSystemProperties();

    @Test
    void shouldStartupWithStubProfile() {
        setSpringProfiles("stub");
        setRandomPort();

        Application.main(new String[0]);
    }

    @Test
    void shouldStartupWithInMemoryMongoProfile() {
        setSpringProfiles("in-memory-mongo");
        setRandomPort();

        Application.main(new String[0]);
    }

    private static void setSpringProfiles(final String profiles) {
        System.setProperty("spring.profiles.active", profiles);
    }

    private static void setRandomPort() {
        System.setProperty("server.port", "0");
    }

}
