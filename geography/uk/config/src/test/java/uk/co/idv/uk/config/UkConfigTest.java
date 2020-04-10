package uk.co.idv.uk.config;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.usecases.util.RandomIdGenerator;
import uk.co.idv.uk.api.lockout.policy.UkLockoutPolicyAttributesConverter;
import uk.co.idv.uk.domain.entities.lockout.UkLockoutPolicyProvider;


import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class UkConfigTest {

    private final UkConfig config = new UkConfig();

    @Test
    void shouldReturnObjectMapperWithUkApiModules() {
        final Collection<Module> expectedModules = new UkApiModuleProvider().getModules();

        final ObjectMapper mapper = config.jsonApiObjectMapper();

        assertThat(mapper.getRegisteredModuleIds()).isEqualTo(toIds(expectedModules));
    }

    @Test
    void shouldReturnObjectMapperWithUkModules() {
        final Collection<Module> expectedModules = new UkModuleProvider().getModules();

        final ObjectMapper mapper = config.objectMapper();

        assertThat(mapper.getRegisteredModuleIds()).isEqualTo(toIds(expectedModules));
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

    private static Set<Object> toIds(final Collection<Module> modules) {
        return modules.stream()
                .map(Module::getTypeId)
                .collect(Collectors.toSet());
    }

}
