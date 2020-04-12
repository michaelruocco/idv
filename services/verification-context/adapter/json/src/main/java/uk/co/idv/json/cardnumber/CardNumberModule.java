package uk.co.idv.json.cardnumber;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.domain.entities.cardnumber.CardNumber;

public class CardNumberModule extends SimpleModule {

    public CardNumberModule() {
        super("card-number-module", Version.unknownVersion());

        addDeserializer(CardNumber.class, new CardNumberDeserializer());
    }

}
