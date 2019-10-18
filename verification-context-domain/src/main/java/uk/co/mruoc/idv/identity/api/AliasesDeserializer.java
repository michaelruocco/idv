package uk.co.mruoc.idv.identity.api;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.mruoc.idv.identity.domain.model.Alias;
import uk.co.mruoc.idv.identity.domain.model.Aliases;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class AliasesDeserializer extends StdDeserializer<Aliases> {

    public AliasesDeserializer() {
        super(Aliases.class);
    }

    @Override
    public Aliases deserialize(final JsonParser parser, final DeserializationContext context) throws IOException {
        final JsonNode node = parser.getCodec().readTree(parser);
        final Collection<Alias> aliases = new ArrayList<>();
        for (JsonNode arrayNode : node) {
            aliases.add(toAlias(parser, arrayNode));
        }
        return Aliases.with(aliases);
    }

    private Alias toAlias(final JsonParser parser, final JsonNode node) throws IOException {
        return node.traverse(parser.getCodec()).readValueAs(Alias.class);
    }

}
