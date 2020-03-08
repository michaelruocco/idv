package uk.co.idv.json.cardnumber;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.cardnumber.CardNumber;
import uk.co.idv.domain.entities.cardnumber.CardNumberMother;
import uk.co.idv.json.ObjectMapperSingleton;
import uk.co.mruoc.file.content.ContentLoader;

import static org.assertj.core.api.Assertions.assertThat;

class CardNumberDeserializerTest {

    private static final ObjectMapper MAPPER = ObjectMapperSingleton.instance();

    @Test
    void shouldDeserializeCreditCardNumber() throws JsonProcessingException {
        final String json = ContentLoader.loadContentFromClasspath("cardnumber/credit-card-number.json");

        final CardNumber cardNumber =  MAPPER.readValue(json, CardNumber.class);

        assertThat(cardNumber).isEqualToComparingFieldByField(CardNumberMother.credit());
    }

    @Test
    void shouldDeserializeDebitCardNumber() throws JsonProcessingException {
        final String json = ContentLoader.loadContentFromClasspath("cardnumber/debit-card-number.json");

        final CardNumber cardNumber =  MAPPER.readValue(json, CardNumber.class);

        assertThat(cardNumber).isEqualToComparingFieldByField(CardNumberMother.debit());
    }

}
