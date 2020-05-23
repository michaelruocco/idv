package uk.co.idv.app.config.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import uk.co.idv.config.uk.jackson.UkJacksonConfig;
import uk.co.idv.utils.json.converter.JsonConverter;
import uk.co.idv.utils.json.converter.jackson.JacksonJsonConverter;

@Configuration
public class JacksonConfig {

    private final UkJacksonConfig ukConfig = new UkJacksonConfig();

    @Primary
    @Bean
    public ObjectMapper apiObjectMapper() {
        return ukConfig.apiObjectMapper();
    }

    @Bean
    public ObjectMapper persistenceObjectMapper() {
        return ukConfig.persistenceObjectMapper();
    }

    @Bean
    public JsonConverter jsonConverter(@Qualifier("persistenceObjectMapper") final ObjectMapper mapper) {
        return new JacksonJsonConverter(mapper);
    }

}
