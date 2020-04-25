package uk.co.idv.utils.json.converter.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class ObjectMapperFactoryTest {

    @Test
    void shouldReturnMapperWithNonEmptySerializationInclusion() {
        final ObjectMapperFactory factory = new ObjectMapperFactory();

        final ObjectMapper mapper = factory.build();

        final SerializationConfig config = mapper.getSerializationConfig();
        final JsonInclude.Value inclusion = config.getDefaultPropertyInclusion();
        assertThat(inclusion.getContentInclusion()).isEqualTo(JsonInclude.Include.NON_EMPTY);
        assertThat(inclusion.getValueInclusion()).isEqualTo(JsonInclude.Include.NON_EMPTY);
    }

    @Test
    void shouldReturnMapperWithWriteDatesAsTimestampsDisabled() {
        final ObjectMapperFactory factory = new ObjectMapperFactory();

        final ObjectMapper mapper = factory.build();

        final SerializationConfig config = mapper.getSerializationConfig();
        assertThat(config.isEnabled(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)).isFalse();
    }

    @Test
    void shouldReturnMapperWithProvidedModulesRegistered() {
        final Module module = new FakeModule();
        final ObjectMapperFactory factory = new ObjectMapperFactory(module);

        final ObjectMapper mapper = factory.build();

        final Set<Object> moduleIds = mapper.getRegisteredModuleIds();
        assertThat(moduleIds).containsExactly(module.getTypeId());
    }

}
