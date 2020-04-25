package uk.co.idv.json.verificationcontext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.Module;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verificationcontext.VerificationContext;
import uk.co.idv.domain.entities.verificationcontext.VerificationContextMother;
import uk.co.idv.utils.json.converter.jackson.ObjectMapperFactory;
import uk.co.mruoc.file.content.ContentLoader;

import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

class VerificationContextDeserializerTest {

    private static final ObjectMapper MAPPER = new ObjectMapperFactory(modules()).build();

    @Test
    void shouldDeserializeContext() throws JsonProcessingException {
        final String json = ContentLoader.loadContentFromClasspath("verification-context/verification-context.json");

        final VerificationContext context =  MAPPER.readValue(json, VerificationContext.class);

        assertThat(context).isEqualToComparingFieldByField(VerificationContextMother.fake());
    }

    private static Collection<Module> modules() {
        return Arrays.asList(
                new VerificationContextModule()
        );
    }

}
