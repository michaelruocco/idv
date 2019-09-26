package uk.co.mruoc.idv.verificationcontext.jsonapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.zalando.jackson.datatype.money.MoneyModule;
import uk.co.mruoc.file.content.ContentLoader;
import uk.co.mruoc.idv.api.IdvModule;
import uk.co.mruoc.idv.domain.model.activity.FakeActivity;
import uk.co.mruoc.idv.domain.model.channel.FakeChannel;
import uk.co.mruoc.idv.identity.api.IdentityModule;
import uk.co.mruoc.idv.identity.domain.model.FakeCreditCardNumber;
import uk.co.mruoc.idv.identity.domain.model.FakeIdentity;
import uk.co.mruoc.idv.verificationcontext.domain.model.SingleMethodSequence;
import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationContext;
import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationSequences;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.FakeVerificationMethodEligible;
import uk.co.mruoc.jsonapi.JsonApiModule;

import java.time.Instant;
import java.util.UUID;

import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class VerificationContextDocumentSerializerTest {

    private static final ObjectMapper MAPPER = buildMapper();

    @Test
    void shouldSerializeContext() throws JsonProcessingException {
        final VerificationContext context = VerificationContext.builder()
                .id(UUID.fromString("eaca769b-c8ac-42fc-ba6a-97e6f1be36f8"))
                .channel(new FakeChannel())
                .providedAlias(new FakeCreditCardNumber())
                .identity(new FakeIdentity(UUID.fromString("d23d923a-521a-422d-9ba9-8cb1c756dbee")))
                .activity(new FakeActivity(Instant.parse("2019-09-21T20:40:29.061224Z")))
                .created(Instant.parse("2019-09-21T20:43:32.233721Z"))
                .expiry(Instant.parse("2019-09-21T20:48:32.233721Z"))
                .sequences(new VerificationSequences(new SingleMethodSequence(new FakeVerificationMethodEligible())))
                .build();
        final VerificationContextDocument document = new VerificationContextDocument(context);

        final String json = MAPPER.writeValueAsString(document);

        final String expectedJson = ContentLoader.loadContentFromClasspath("verification-context/json-api/verification-context-document.json");
        assertThatJson(json).isEqualTo(expectedJson);
    }

    private static ObjectMapper buildMapper() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JsonApiModule());
        mapper.registerModule(new JsonApiVerificationContextModule());
        mapper.registerModule(new IdvModule());
        mapper.registerModule(new IdentityModule());
        mapper.registerModule(new MoneyModule());
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }

}
