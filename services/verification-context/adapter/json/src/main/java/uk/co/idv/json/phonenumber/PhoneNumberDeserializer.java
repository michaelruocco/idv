package uk.co.idv.json.phonenumber;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.domain.entities.phonenumber.MobilePhoneNumber;
import uk.co.idv.domain.entities.phonenumber.OtherPhoneNumber;
import uk.co.idv.domain.entities.phonenumber.PhoneNumber;
import uk.co.idv.utils.json.converter.jackson.JsonNodeConverter;
import uk.co.idv.utils.json.converter.jackson.JsonParserConverter;

import java.util.UUID;

public class PhoneNumberDeserializer extends StdDeserializer<PhoneNumber> {

    protected PhoneNumberDeserializer() {
        super(PhoneNumber.class);
    }

    @Override
    public PhoneNumber deserialize(final JsonParser parser, final DeserializationContext context) {
        final JsonNode node = JsonParserConverter.toNode(parser);
        final String type = node.get("type").asText();
        if (isMobile(type)) {
            return toMobileNumber(node);
        }
        return toOtherNumber(node);
    }

    private boolean isMobile(final String type) {
        return MobilePhoneNumber.TYPE.equals(type);
    }

    private static PhoneNumber toMobileNumber(final JsonNode node) {
        return MobilePhoneNumber.builder()
                .id(extractId(node))
                .value(extractValue(node))
                .build();
    }

    private static PhoneNumber toOtherNumber(final JsonNode node) {
        return OtherPhoneNumber.builder()
                .id(extractId(node))
                .value(extractValue(node))
                .build();
    }

    private static UUID extractId(final JsonNode node) {
        return JsonNodeConverter.toUUID(node.get("id"));
    }

    private static String extractValue(final JsonNode node) {
        return node.get("value").asText();
    }

}
