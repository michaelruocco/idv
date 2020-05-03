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

import static org.assertj.core.api.Assertions.assertThat;

class PushNotificationDeserializerTest {

    private static final ObjectMapper MAPPER = new ObjectMapperFactory(new PushNotificationMethodModule()).build();

    @Test
    void shouldDeserializeIneligible() throws JsonProcessingException {
        final String json = loadFileContent("push-notification-ineligible.json");

        final PushNotification method = (PushNotification) MAPPER.readValue(json, VerificationMethod.class);

        final PushNotification expectedMethod = PushNotificationMother.ineligible();
        assertThat(method).isEqualToIgnoringGivenFields(expectedMethod, "params");
        assertThat(method.getParams()).isEqualToComparingFieldByField(expectedMethod.getParams());
    }

    @Test
    void shouldDeserializeEligible() throws JsonProcessingException {
        final String json = loadFileContent("push-notification-eligible.json");

        final VerificationMethod method = MAPPER.readValue(json, VerificationMethod.class);

        assertThat(method).isEqualToComparingFieldByField(PushNotificationMother.eligible());
    }

    @Test
    void shouldDeserializeEligibleWithResult() throws JsonProcessingException {
        final String json = loadFileContent("push-notification-eligible-with-result.json");

        final VerificationMethod method = MAPPER.readValue(json, VerificationMethod.class);

        final VerificationMethod expectedMethod = PushNotificationMother.eligibleBuilder()
                .results(VerificationResultsMother.oneSuccessful(PushNotification.NAME))
                .build();
        assertThat(method).isEqualToComparingFieldByField(expectedMethod);
    }

    private static String loadFileContent(final String name) {
        final String directory = "verification-context/method/pushnotification/%s";
        return ContentLoader.loadContentFromClasspath(String.format(directory, name));
    }

}
