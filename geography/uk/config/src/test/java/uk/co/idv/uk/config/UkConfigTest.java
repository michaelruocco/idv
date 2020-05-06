package uk.co.idv.uk.config;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.usecases.util.id.RandomIdGenerator;
import uk.co.idv.uk.domain.entities.policy.lockout.UkLockoutPolicyProvider;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static org.assertj.core.api.Assertions.assertThat;

class UkConfigTest {

    private final UkConfig config = new UkConfig();

    @Test
    void shouldReturnObjectMapperWithUkApiModules() {
        final Collection<Object> expectedModuleIds = toIds(new UkApiIdvModule());

        final ObjectMapper mapper = config.jsonApiObjectMapper();

        assertThat(mapper.getRegisteredModuleIds()).containsExactlyInAnyOrderElementsOf(expectedModuleIds);
    }

    @Test
    void shouldReturnObjectMapperWithUkModules() {
        final Collection<Object> expectedModuleIds = toIds( new UkIdvModule());

        final ObjectMapper mapper = config.objectMapper();

        assertThat(mapper.getRegisteredModuleIds()).containsExactlyInAnyOrderElementsOf(expectedModuleIds);
    }

    @Test
    void shouldReturnRandomIdGenerator() {
        assertThat(config.idGenerator()).isInstanceOf(RandomIdGenerator.class);
    }

    @Test
    void shouldReturnUkLockoutPolicyProvider() {
        assertThat(config.lockoutPolicyProvider()).isInstanceOf(UkLockoutPolicyProvider.class);
    }

    private static Collection<Object> toIds(final Module module) {
        return toDependencyStream(module)
                .map(Module::getTypeId)
                .collect(Collectors.toSet());
    }

    private static Stream<Module> toDependencyStream(final Module module) {
        final Iterable<? extends Module> iterable = module.getDependencies();
        final Stream<? extends Module> dependencyStream = StreamSupport.stream(iterable.spliterator(), false)
                .flatMap(UkConfigTest::toDependencyStream);
        return Stream.concat(dependencyStream, Stream.of(module));
    }

}
