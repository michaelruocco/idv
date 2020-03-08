package uk.co.idv.json.cardnumber;

import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.domain.entities.cardnumber.CardNumber;

public class CardNumberModule extends SimpleModule {

    public CardNumberModule() {
        addDeserializer(CardNumber.class, new CardNumberDeserializer());
    }

}
