package uk.co.idv.json.identity;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.domain.entities.identity.Identity;
import uk.co.idv.domain.entities.identity.alias.Aliases;
import uk.co.idv.domain.entities.phonenumber.PhoneNumbers;
import uk.co.idv.utils.json.converter.jackson.JsonNodeConverter;

import java.io.IOException;
import java.util.Collections;

public class IdentityDeserializer extends StdDeserializer<Identity> {

    protected IdentityDeserializer() {
        super(Identity.class);
    }

    @Override
    public Identity deserialize(final JsonParser parser, final DeserializationContext context) throws IOException {
        final JsonNode node = parser.getCodec().readTree(parser);
        final Aliases aliases = JsonNodeConverter.toObject(node.get("aliases"), parser, Aliases.class);
        return Identity.builder()
                .aliases(aliases)
                //TODO properly deserialize phone numbers and accounts
                .phoneNumbers(new PhoneNumbers())
                .accounts(Collections.emptyList())
                .build();
    }

}
