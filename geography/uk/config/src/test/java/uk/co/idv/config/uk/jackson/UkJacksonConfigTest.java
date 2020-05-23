package uk.co.idv.config.uk.jackson;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static org.assertj.core.api.Assertions.assertThat;

class UkJacksonConfigTest {

    private final UkJacksonConfig config = new UkJacksonConfig();

    @Test
    void shouldReturnObjectMapperWithUkApiModules() {
        final Collection<Object> expectedModuleIds = toIds(new UkApiIdvModule());

        final ObjectMapper mapper = config.apiObjectMapper();

        assertThat(mapper.getRegisteredModuleIds()).containsExactlyInAnyOrderElementsOf(expectedModuleIds);
    }

    @Test
    void shouldReturnObjectMapperWithUkModules() {
        final Collection<Object> expectedModuleIds = toIds( new UkIdvModule());

        final ObjectMapper mapper = config.persistenceObjectMapper();

        assertThat(mapper.getRegisteredModuleIds()).containsExactlyInAnyOrderElementsOf(expectedModuleIds);
    }

    private static Collection<Object> toIds(final Module module) {
        return toDependencyStream(module)
                .map(Module::getTypeId)
                .collect(Collectors.toSet());
    }

    private static Stream<Module> toDependencyStream(final Module module) {
        final Iterable<? extends Module> iterable = module.getDependencies();
        final Stream<? extends Module> dependencyStream = StreamSupport.stream(iterable.spliterator(), false)
                .flatMap(UkJacksonConfigTest::toDependencyStream);
        return Stream.concat(dependencyStream, Stream.of(module));
    }

}
