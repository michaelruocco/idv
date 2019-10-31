package uk.co.mruoc.idv.verificationcontext.jsonapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.zalando.jackson.datatype.money.MoneyModule;
import uk.co.mruoc.file.content.ContentLoader;
import uk.co.mruoc.idv.api.IdvModule;
import uk.co.mruoc.idv.identity.api.IdentityModule;
import uk.co.mruoc.idv.verificationcontext.domain.model.FakeVerificationContext;
import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationContext;
import uk.co.mruoc.jsonapi.ApiModule;

import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class VerificationContextDocumentSerializerTest {

    private static final ObjectMapper MAPPER = buildMapper();

    @Test
    void shouldSerializeContext() throws JsonProcessingException {
        final VerificationContext context = new FakeVerificationContext();
        final VerificationContextDocument document = new VerificationContextDocument(context);

        final String json = MAPPER.writeValueAsString(document);

        final String expectedJson = ContentLoader.loadContentFromClasspath("verification-context/json-api/verification-context-document.json");
        System.out.println(json);
        assertThatJson(json).isEqualTo(expectedJson);
    }

    private static ObjectMapper buildMapper() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new ApiModule());
        mapper.registerModule(new JsonApiVerificationContextModule());
        mapper.registerModule(new IdvModule());
        mapper.registerModule(new IdentityModule());
        mapper.registerModule(new MoneyModule());
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }

}
