package uk.co.idv.json.phonennumber;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.phonenumber.PhoneNumberMother;
import uk.co.idv.domain.entities.phonenumber.PhoneNumbers;
import uk.co.idv.json.phonenumber.PhoneNumberModule;
import uk.co.idv.utils.json.converter.jackson.ObjectMapperFactory;
import uk.co.mruoc.file.content.ContentLoader;

import static org.assertj.core.api.Assertions.assertThat;

class PhoneNumbersDeserializerTest {

    private static final ObjectMapper MAPPER = new ObjectMapperFactory(new PhoneNumberModule()).build();

    @Test
    void shouldDeserializePhoneNumbers() throws JsonProcessingException {
        final String json = ContentLoader.loadContentFromClasspath("phone-number/phone-numbers.json");

        final PhoneNumbers phoneNumbers = MAPPER.readValue(json, PhoneNumbers.class);

        assertThat(phoneNumbers).isEqualTo(PhoneNumberMother.twoNumbers());
    }

}
