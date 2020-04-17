package uk.co.idv.json.phonennumber;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.phonenumber.PhoneNumber;
import uk.co.idv.domain.entities.phonenumber.PhoneNumberMother;
import uk.co.idv.json.phonenumber.PhoneNumberModule;
import uk.co.idv.utils.json.converter.jackson.ObjectMapperFactory;
import uk.co.mruoc.file.content.ContentLoader;

import static org.assertj.core.api.Assertions.assertThat;

class PhoneNumberDeserializerTest {

    private static final ObjectMapper MAPPER = new ObjectMapperFactory(new PhoneNumberModule()).build();

    @Test
    void shouldDeserializeMobileNumber() throws JsonProcessingException {
        final String json = ContentLoader.loadContentFromClasspath("phone-number/mobile-number.json");

        final PhoneNumber phoneNumber = MAPPER.readValue(json, PhoneNumber.class);

        assertThat(phoneNumber).isEqualTo(PhoneNumberMother.mobile());
    }

    @Test
    void shouldDeserializeOtherNumber() throws JsonProcessingException {
        final String json = ContentLoader.loadContentFromClasspath("phone-number/other-number.json");

        final PhoneNumber phoneNumber = MAPPER.readValue(json, PhoneNumber.class);

        assertThat(phoneNumber).isEqualTo(PhoneNumberMother.other());
    }

}
