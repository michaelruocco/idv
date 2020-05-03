package uk.co.idv.json.verificationcontext.method.pushnotification;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.method.pushnotification.PushNotification;
import uk.co.idv.domain.entities.verificationcontext.method.pushnotification.PushNotificationMother;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResultsMother;
import uk.co.idv.utils.json.converter.jackson.ObjectMapperFactory;
import uk.co.mruoc.file.content.ContentLoader;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class PushNotificationSerializerTest {

    private static final ObjectMapper MAPPER = new ObjectMapperFactory(new PushNotificationMethodModule()).build();

    @Test
    void shouldSerializeEligiblePushNotification() throws JsonProcessingException {
        final VerificationMethod method = PushNotificationMother.eligible();

        final String json = MAPPER.writeValueAsString(method);

        final String expectedJson = loadFileContent("push-notification-eligible.json");
        assertThatJson(json).isEqualTo(expectedJson);
    }

    @Test
    void shouldSerializeEligiblePushNotificationWithResult() throws JsonProcessingException {
        final VerificationMethod method = PushNotificationMother.eligibleBuilder()
                .results(VerificationResultsMother.oneSuccessful(PushNotification.NAME))
                .build();

        final String json = MAPPER.writeValueAsString(method);

        final String expectedJson = loadFileContent("push-notification-eligible-with-result.json");
        assertThatJson(json).isEqualTo(expectedJson);
    }

    @Test
    void shouldSerializeIneligiblePushNotification() throws JsonProcessingException {
        final VerificationMethod method = PushNotificationMother.ineligible();

        final String json = MAPPER.writeValueAsString(method);

        final String expectedJson = loadFileContent("push-notification-ineligible.json");
        assertThatJson(json).isEqualTo(expectedJson);
    }

    private static String loadFileContent(final String name) {
        final String directory = "verification-context/method/pushnotification/%s";
        return ContentLoader.loadContentFromClasspath(String.format(directory, name));
    }

}
