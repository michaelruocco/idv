package uk.co.idv.json.identity.alias;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.domain.entities.identity.alias.Alias;
import uk.co.idv.domain.entities.identity.alias.Aliases;
import uk.co.idv.utils.json.converter.jackson.JsonNodeConverter;
import uk.co.idv.utils.json.converter.jackson.JsonParserConverter;

import java.util.ArrayList;
import java.util.Collection;

public class AliasesDeserializer extends StdDeserializer<Aliases> {

    public AliasesDeserializer() {
        super(Aliases.class);
    }

    @Override
    public Aliases deserialize(final JsonParser parser, final DeserializationContext context) {
        final JsonNode node = JsonParserConverter.toNode(parser);
        final Collection<Alias> aliases = new ArrayList<>();
        for (JsonNode arrayNode : node) {
            aliases.add(JsonNodeConverter.toObject(arrayNode, parser, Alias.class));
        }
        return Aliases.with(aliases);
    }

}
