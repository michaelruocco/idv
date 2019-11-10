package uk.co.idv.json.identity;

import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.domain.entities.identity.alias.Alias;
import uk.co.idv.domain.entities.identity.alias.Aliases;
import uk.co.idv.domain.entities.identity.Identity;

public class IdentityModule extends SimpleModule {

    public IdentityModule() {
        setMixInAnnotation(Identity.class, IdentityMixin.class);

        addSerializer(Aliases.class, new AliasesSerializer());
        addSerializer(Alias.class, new AliasSerializer());

        addDeserializer(Alias.class, new AliasDeserializer());
        addDeserializer(Aliases.class, new AliasesDeserializer());
    }

}
