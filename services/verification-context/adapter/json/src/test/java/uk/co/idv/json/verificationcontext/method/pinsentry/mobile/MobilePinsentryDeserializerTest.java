package uk.co.idv.json.verificationcontext.method.pinsentry.mobile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.mobile.MobilePinsentry;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.mobile.MobilePinsentryMother;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResultsMother;
import uk.co.idv.utils.json.converter.jackson.ObjectMapperFactory;
import uk.co.mruoc.file.content.ContentLoader;

import static org.assertj.core.api.Assertions.assertThat;

class MobilePinsentryDeserializerTest {

    private static final ObjectMapper MAPPER = new ObjectMapperFactory(new MobilePinsentryMethodModule()).build();

    @Test
    void shouldDeserializeIneligible() throws JsonProcessingException {
        final String json = loadFileContent("mobile-pinsentry-ineligible.json");

        final MobilePinsentry method = (MobilePinsentry) MAPPER.readValue(json, VerificationMethod.class);

        final MobilePinsentry expectedMethod = MobilePinsentryMother.ineligible();
        assertThat(method).isEqualToIgnoringGivenFields(expectedMethod, "params");
        assertThat(method.getParams()).isEqualToComparingFieldByField(expectedMethod.getParams());
    }

    @Test
    void shouldDeserializeEligible() throws JsonProcessingException {
        final String json = loadFileContent("mobile-pinsentry-eligible.json");

        final VerificationMethod method = MAPPER.readValue(json, VerificationMethod.class);

        final VerificationMethod expectedMethod = MobilePinsentryMother.eligible();
        assertThat(method).isEqualToComparingFieldByField(expectedMethod);
    }

    @Test
    void shouldDeserializeEligibleWithResult() throws JsonProcessingException {
        final String json = loadFileContent("mobile-pinsentry-eligible-with-result.json");

        final MobilePinsentry method = (MobilePinsentry) MAPPER.readValue(json, VerificationMethod.class);

        final MobilePinsentry expectedMethod = MobilePinsentryMother.eligibleBuilder()
                .results(VerificationResultsMother.oneSuccessful(MobilePinsentry.NAME))
                .build();
        assertThat(method).isEqualToIgnoringGivenFields(expectedMethod, "params");
        assertThat(method.getParams()).isEqualTo(expectedMethod.getParams());
    }

    private static String loadFileContent(final String name) {
        final String directory = "verification-context/method/pinsentry/mobile/%s";
        return ContentLoader.loadContentFromClasspath(String.format(directory, name));
    }

}
