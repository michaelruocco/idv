package uk.co.idv.utils.aws.system;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.ClearSystemProperty;
import org.junitpioneer.jupiter.SetSystemProperty;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class SystemPropertyLoaderTest {

    @Test
    @ClearSystemProperty(key = "test.property")
    void shouldEmptyOptionalIfPropertyNotSet() {
        final Optional<String> value = SystemPropertyLoader.load("test.property");

        assertThat(value).isEmpty();
    }

    @Test
    @SetSystemProperty(key = "test.property", value = "my-value")
    void shouldPropertyValueIfPropertySet() {
        final Optional<String> value = SystemPropertyLoader.load("test.property");

        assertThat(value).contains("my-value");
    }

}
