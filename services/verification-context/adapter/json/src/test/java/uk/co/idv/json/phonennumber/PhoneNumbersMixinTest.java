package uk.co.idv.json.phonennumber;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.phonenumber.PhoneNumberMother;
import uk.co.idv.domain.entities.phonenumber.PhoneNumbers;
import uk.co.idv.json.phonenumber.PhoneNumberModule;
import uk.co.idv.utils.json.converter.jackson.ObjectMapperFactory;
import uk.co.mruoc.file.content.ContentLoader;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class PhoneNumbersMixinTest {

    private static final ObjectMapper MAPPER = new ObjectMapperFactory(new PhoneNumberModule()).build();

    @Test
    void shouldSerializePhoneNumbers() throws JsonProcessingException {
        final PhoneNumbers phoneNumbers = PhoneNumberMother.two();

        final String json = MAPPER.writeValueAsString(phoneNumbers);

        final String expectedJson = ContentLoader.loadContentFromClasspath("phone-number/phone-numbers.json");
        assertThatJson(json).isEqualTo(expectedJson);
    }

}
