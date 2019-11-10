package uk.co.idv.json.verificationcontext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.mruoc.file.content.ContentLoader;
import uk.co.idv.domain.entities.verificationcontext.method.pushnotification.PushNotification;
import uk.co.idv.domain.entities.verificationcontext.method.pushnotification.PushNotificationEligible;
import uk.co.idv.domain.entities.verificationcontext.method.pushnotification.PushNotificationIneligible;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.result.FakeVerificationResultSuccessful;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResultSuccessful;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class PushNotificationSerializerTest {

    private static final ObjectMapper MAPPER = buildMapper();

    @Test
    void shouldSerializeEligiblePushNotification() throws JsonProcessingException {
        final VerificationMethod method = new PushNotificationEligible();

        final String json = MAPPER.writeValueAsString(method);

        final String expectedJson = ContentLoader.loadContentFromClasspath("verification-context/push-notification-eligible.json");
        assertThatJson(json).isEqualTo(expectedJson);
    }

    @Test
    void shouldSerializeEligiblePushNotificationWithResult() throws JsonProcessingException {
        final VerificationResultSuccessful result = new FakeVerificationResultSuccessful(PushNotification.NAME);
        final VerificationMethod method = new PushNotificationEligible(result);

        final String json = MAPPER.writeValueAsString(method);

        final String expectedJson = ContentLoader.loadContentFromClasspath("verification-context/push-notification-eligible-with-result.json");
        assertThatJson(json).isEqualTo(expectedJson);
    }

    @Test
    void shouldSerializeIneligiblePushNotification() throws JsonProcessingException {
        final VerificationMethod method = new PushNotificationIneligible();

        final String json = MAPPER.writeValueAsString(method);

        final String expectedJson = ContentLoader.loadContentFromClasspath("verification-context/push-notification-ineligible.json");
        assertThatJson(json).isEqualTo(expectedJson);
    }

    private static ObjectMapper buildMapper() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new VerificationContextModule());
        return mapper;
    }

}
