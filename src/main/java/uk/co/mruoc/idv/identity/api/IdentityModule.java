package uk.co.mruoc.idv.identity.api;

import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.mruoc.idv.identity.domain.model.Alias;
import uk.co.mruoc.idv.identity.domain.model.Aliases;
import uk.co.mruoc.idv.identity.domain.model.Identity;

public class IdentityModule extends SimpleModule {

    public IdentityModule() {
        setMixInAnnotation(Identity.class, IdentityMixin.class);
        addSerializer(Aliases.class, new AliasesSerializer());
        addSerializer(Alias.class, new AliasSerializer());
    }

}
