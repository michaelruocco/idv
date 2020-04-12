package uk.co.idv.json.identity;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.domain.entities.identity.alias.Alias;
import uk.co.idv.domain.entities.identity.alias.Aliases;

public class AliasModule extends SimpleModule {

    public AliasModule() {
        super("aliases-module", Version.unknownVersion());

        addSerializer(Aliases.class, new AliasesSerializer());
        addSerializer(Alias.class, new AliasSerializer());

        addDeserializer(Alias.class, new AliasDeserializer());
        addDeserializer(Aliases.class, new AliasesDeserializer());
    }

}
