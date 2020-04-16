package uk.co.idv.json.identity;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.domain.entities.identity.Identity;
import uk.co.idv.domain.entities.identity.alias.Alias;
import uk.co.idv.domain.entities.identity.alias.Aliases;
import uk.co.idv.domain.entities.phonenumber.PhoneNumbers;

import java.util.Collections;

public class IdentityModule extends SimpleModule {

    public IdentityModule() {
        super("identity-module", Version.unknownVersion());

        setMixInAnnotation(Identity.class, IdentityMixin.class);
        setMixInAnnotation(PhoneNumbers.class, PhoneNumbersMixin.class);

        addSerializer(Aliases.class, new AliasesSerializer());
        addSerializer(Alias.class, new AliasSerializer());

        addDeserializer(Alias.class, new AliasDeserializer());
        addDeserializer(Aliases.class, new AliasesDeserializer());
        addDeserializer(Identity.class, new IdentityDeserializer());
    }

    @Override
    public Iterable<? extends Module> getDependencies() {
        return Collections.singleton(new AliasModule());
    }

}
