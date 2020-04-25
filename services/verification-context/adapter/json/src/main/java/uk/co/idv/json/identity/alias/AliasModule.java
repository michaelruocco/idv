package uk.co.idv.json.identity.alias;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.domain.entities.identity.alias.Alias;
import uk.co.idv.domain.entities.identity.alias.Aliases;
import uk.co.idv.domain.entities.identity.alias.CreditCardNumber;
import uk.co.idv.domain.entities.identity.alias.DebitCardNumber;
import uk.co.idv.domain.entities.identity.alias.IdvId;
import uk.co.idv.json.identity.alias.creditcardnumber.CreditCardNumberDeserializer;
import uk.co.idv.json.identity.alias.debitcardnumber.DebitCardNumberDeserializer;
import uk.co.idv.json.identity.alias.idvid.IdvIdDeserializer;
import uk.co.idv.json.identity.alias.idvid.IdvIdMixin;

public class AliasModule extends SimpleModule {

    public AliasModule() {
        super("aliases-module", Version.unknownVersion());

        setMixInAnnotation(Aliases.class, AliasesMixin.class);
        setMixInAnnotation(Alias.class, AliasMixin.class);
        setMixInAnnotation(IdvId.class, IdvIdMixin.class);

        addDeserializer(Aliases.class, new AliasesDeserializer());

        addDeserializer(Alias.class, new AliasDeserializer());
        addDeserializer(IdvId.class, new IdvIdDeserializer());
        addDeserializer(CreditCardNumber.class, new CreditCardNumberDeserializer());
        addDeserializer(DebitCardNumber.class, new DebitCardNumberDeserializer());
    }

}
