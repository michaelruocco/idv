package uk.co.idv.json.identity;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.domain.entities.identity.Identity;
import uk.co.idv.domain.entities.identity.alias.Aliases;

import java.io.IOException;

public class IdentityDeserializer extends StdDeserializer<Identity> {

    protected IdentityDeserializer() {
        super(Identity.class);
    }

    @Override
    public Identity deserialize(final JsonParser parser, final DeserializationContext context) throws IOException {
        final JsonNode node = parser.getCodec().readTree(parser);
        final Aliases aliases = node.get("aliases").traverse(parser.getCodec()).readValueAs(Aliases.class);
        return new Identity(aliases);
    }

}
