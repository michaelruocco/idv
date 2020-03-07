package uk.co.idv.json.verificationcontext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.Module;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verificationcontext.VerificationContext;
import uk.co.idv.domain.entities.verificationcontext.VerificationContextMother;
import uk.co.idv.json.ObjectMapperFactory;
import uk.co.idv.json.activity.ActivityDeserializer;
import uk.co.idv.json.activity.ActivityModule;
import uk.co.idv.json.activity.LoginJsonNodeConverter;
import uk.co.idv.json.activity.OnlinePurchaseJsonNodeConverter;
import uk.co.idv.json.activity.simple.AllSimpleActivityJsonNodeConverter;
import uk.co.mruoc.file.content.ContentLoader;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class VerificationContextDeserializerTest {

    private static final ObjectMapper MAPPER = new ObjectMapperFactory(modules()).build();

    @Test
    void shouldDeserializeContext() throws JsonProcessingException {
        final String json = ContentLoader.loadContentFromClasspath("verification-context/verification-context.json");

        final VerificationContext context =  MAPPER.readValue(json, VerificationContext.class);

        assertThat(context).isEqualToComparingFieldByField(VerificationContextMother.fake());
    }

    private static List<Module> modules() {
        return ObjectMapperFactory.modules(new ActivityModule(buildActivityDeserializer()));
    }

    private static ActivityDeserializer buildActivityDeserializer() {
        return new ActivityDeserializer(
                new OnlinePurchaseJsonNodeConverter(),
                new LoginJsonNodeConverter(),
                new AllSimpleActivityJsonNodeConverter()
        );
    }

}
