package uk.co.idv.json.phonennumber;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.phonenumber.PhoneNumber;
import uk.co.idv.domain.entities.phonenumber.PhoneNumberMother;
import uk.co.idv.json.phonenumber.PhoneNumberModule;
import uk.co.idv.utils.json.converter.jackson.ObjectMapperFactory;
import uk.co.mruoc.file.content.ContentLoader;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class PhoneNumberMixinTest {

    private static final ObjectMapper MAPPER = new ObjectMapperFactory(new PhoneNumberModule()).build();

    @Test
    void shouldSerializeMobileNumber() throws JsonProcessingException {
        final PhoneNumber phoneNumber = PhoneNumberMother.mobile();

        final String json = MAPPER.writeValueAsString(phoneNumber);

        final String expectedJson = ContentLoader.loadContentFromClasspath("phone-number/mobile-number.json");
        assertThatJson(json).isEqualTo(expectedJson);
    }

    @Test
    void shouldSerializeOtherNumber() throws JsonProcessingException {
        final PhoneNumber phoneNumber = PhoneNumberMother.other();

        final String json = MAPPER.writeValueAsString(phoneNumber);

        final String expectedJson = ContentLoader.loadContentFromClasspath("phone-number/other-number.json");
        assertThatJson(json).isEqualTo(expectedJson);
    }

}
