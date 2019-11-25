package uk.co.idv.uk.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.usecases.util.RandomIdGenerator;
import uk.co.idv.uk.api.channel.UkObjectMapperSingleton;
import uk.co.idv.uk.api.lockout.policy.UkLockoutPolicyAttributesConverter;
import uk.co.idv.uk.domain.entities.lockout.UkLockoutPolicyProvider;
import uk.co.idv.uk.repository.mongo.activity.UkActivityDocumentConverterDelegator;
import uk.co.idv.uk.repository.mongo.channel.UkChannelDocumentConverterDelegator;
import uk.co.idv.uk.repository.mongo.lockout.UkLockoutPolicyDocumentConverterDelegator;

import static org.assertj.core.api.Assertions.assertThat;

class UkConfigTest {

    private final UkConfig config = new UkConfig();

    @Test
    void shouldUkObjectMapper() {
        final ObjectMapper expectedMapper = UkObjectMapperSingleton.get();

        final ObjectMapper mapper = config.objectMapper();

        assertThat(mapper).isEqualTo(expectedMapper);
    }

    @Test
    void shouldReturnRandomIdGenerator() {
        assertThat(config.idGenerator()).isInstanceOf(RandomIdGenerator.class);
    }

    @Test
    void shouldReturnUkChannelDocumentConverterDelegator() {
        assertThat(config.channelDocumentConverterDelegator()).isInstanceOf(UkChannelDocumentConverterDelegator.class);
    }

    @Test
    void activityDocumentConverterDelegator() {
        assertThat(config.activityDocumentConverterDelegator()).isInstanceOf(UkActivityDocumentConverterDelegator.class);
    }

    @Test
    void shouldReturnUkLockoutPolicyProvider() {
        assertThat(config.lockoutPolicyProvider()).isInstanceOf(UkLockoutPolicyProvider.class);
    }

    @Test
    void lockoutPolicyDocumentConverterDelegator() {
        assertThat(config.lockoutPolicyDocumentConverterDelegator()).isInstanceOf(UkLockoutPolicyDocumentConverterDelegator.class);
    }

    @Test
    void lockoutPolicyAttributesConverter() {
        assertThat(config.lockoutPolicyAttributesConverter()).isInstanceOf(UkLockoutPolicyAttributesConverter.class);
    }

}
