package uk.co.idv.json.verificationcontext.method.pinsentry.mobile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.mobile.MobilePinsentry;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.mobile.MobilePinsentryEligible;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.mobile.MobilePinsentryIneligible;
import uk.co.idv.domain.entities.verificationcontext.result.FakeVerificationResultSuccessful;
import uk.co.idv.json.ObjectMapperSingleton;
import uk.co.mruoc.file.content.ContentLoader;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.idv.domain.entities.verificationcontext.method.pinsentry.PinsentryFunction.RESPOND;

class MobilePinsentryDeserializerTest {

    private static final ObjectMapper MAPPER = ObjectMapperSingleton.instance();

    @Test
    void shouldDeserializeIneligible() throws JsonProcessingException {
        final String json = loadFileContent("mobile-pinsentry-ineligible.json");

        final VerificationMethod method =  MAPPER.readValue(json, VerificationMethod.class);

        final VerificationMethod expectedMethod = new MobilePinsentryIneligible(RESPOND);
        assertThat(method).isEqualToComparingFieldByField(expectedMethod);
    }

    @Test
    void shouldDeserializeEligible() throws JsonProcessingException {
        final String json = loadFileContent("mobile-pinsentry-eligible.json");

        final VerificationMethod method = MAPPER.readValue(json, VerificationMethod.class);

        final VerificationMethod expectedMethod = new MobilePinsentryEligible(RESPOND);
        assertThat(method).isEqualToComparingFieldByField(expectedMethod);
    }

    @Test
    void shouldDeserializeEligibleWithResult() throws JsonProcessingException {
        final String json = loadFileContent("mobile-pinsentry-eligible-with-result.json");

        final VerificationMethod method = MAPPER.readValue(json, VerificationMethod.class);

        final VerificationMethod expectedMethod = new MobilePinsentryEligible(RESPOND, new FakeVerificationResultSuccessful(MobilePinsentry.NAME));
        assertThat(method).isEqualToComparingFieldByField(expectedMethod);
    }

    private static String loadFileContent(final String name) {
        final String directory = "verification-context/method/pinsentry/mobile/%s";
        return ContentLoader.loadContentFromClasspath(String.format(directory, name));
    }

}
