package uk.co.idv.utils.json.converter.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Collection;

import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;

@RequiredArgsConstructor
@Slf4j
public class ObjectMapperFactory {

    private final Collection<Module> modules;

    public ObjectMapperFactory(final Module... modules) {
        this(Arrays.asList(modules));
    }

    public ObjectMapper build() {
        return customize(new ObjectMapper());
    }

    private ObjectMapper customize(final ObjectMapper mapper) {
        log.info("registering jackson modules {}", modules);
        return mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY)
                .disable(WRITE_DATES_AS_TIMESTAMPS)
                .registerModules(modules);
    }

}
