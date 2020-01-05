package uk.co.idv.uk.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.usecases.util.RandomIdGenerator;
import uk.co.idv.uk.api.channel.UkObjectMapperSingleton;
import uk.co.idv.uk.api.lockout.policy.UkLockoutPolicyAttributesConverter;
import uk.co.idv.uk.domain.entities.lockout.UkLockoutPolicyProvider;

import static org.assertj.core.api.Assertions.assertThat;

class UkConfigTest {

    private final UkConfig config = new UkConfig();

    @Test
    void shouldReturnUkJsonApiObjectMapper() {
        final ObjectMapper expectedMapper = UkObjectMapperSingleton.jsonApiInstance();

        final ObjectMapper mapper = config.jsonApiObjectMapper();

        assertThat(mapper).isEqualTo(expectedMapper);
    }

    @Test
    void shouldReturnUkObjectMapper() {
        final ObjectMapper expectedMapper = UkObjectMapperSingleton.instance();

        final ObjectMapper mapper = config.objectMapper();

        assertThat(mapper).isEqualTo(expectedMapper);
    }

    @Test
    void shouldReturnRandomIdGenerator() {
        assertThat(config.idGenerator()).isInstanceOf(RandomIdGenerator.class);
    }

    @Test
    void shouldReturnUkLockoutPolicyProvider() {
        assertThat(config.lockoutPolicyProvider()).isInstanceOf(UkLockoutPolicyProvider.class);
    }

    @Test
    void lockoutPolicyAttributesConverter() {
        assertThat(config.lockoutPolicyAttributesConverter()).isInstanceOf(UkLockoutPolicyAttributesConverter.class);
    }

}
