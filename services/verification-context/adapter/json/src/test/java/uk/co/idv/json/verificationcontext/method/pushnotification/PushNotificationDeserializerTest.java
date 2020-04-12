package uk.co.idv.json.verificationcontext.method.pushnotification;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.method.pushnotification.PushNotification;
import uk.co.idv.domain.entities.verificationcontext.method.pushnotification.PushNotificationEligible;
import uk.co.idv.domain.entities.verificationcontext.method.pushnotification.PushNotificationIneligible;
import uk.co.idv.domain.entities.verificationcontext.result.FakeVerificationResultSuccessful;
import uk.co.idv.json.verificationcontext.VerificationContextObjectMapperFactory;
import uk.co.mruoc.file.content.ContentLoader;

import static org.assertj.core.api.Assertions.assertThat;

class PushNotificationDeserializerTest {

    private static final ObjectMapper MAPPER = new VerificationContextObjectMapperFactory().build();

    @Test
    void shouldDeserializeIneligible() throws JsonProcessingException {
        final String json = loadFileContent("push-notification-ineligible.json");

        final VerificationMethod method =  MAPPER.readValue(json, VerificationMethod.class);

        assertThat(method).isEqualToComparingFieldByField(new PushNotificationIneligible());
    }

    @Test
    void shouldDeserializeEligible() throws JsonProcessingException {
        final String json = loadFileContent("push-notification-eligible.json");

        final VerificationMethod method =  MAPPER.readValue(json, VerificationMethod.class);

        assertThat(method).isEqualToComparingFieldByField(new PushNotificationEligible());
    }

    @Test
    void shouldDeserializeEligibleWithResult() throws JsonProcessingException {
        final String json = loadFileContent("push-notification-eligible-with-result.json");

        final VerificationMethod method =  MAPPER.readValue(json, VerificationMethod.class);

        final VerificationMethod expectedMethod = new PushNotificationEligible(new FakeVerificationResultSuccessful(PushNotification.NAME));
        assertThat(method).isEqualToComparingFieldByField(expectedMethod);
    }

    private static String loadFileContent(final String name) {
        final String directory = "verification-context/method/pushnotification/%s";
        return ContentLoader.loadContentFromClasspath(String.format(directory, name));
    }

}
