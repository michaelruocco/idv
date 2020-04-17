package uk.co.idv.json.phonenumber;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.domain.entities.phonenumber.MobilePhoneNumber;
import uk.co.idv.domain.entities.phonenumber.OtherPhoneNumber;
import uk.co.idv.domain.entities.phonenumber.PhoneNumber;
import uk.co.idv.utils.json.converter.jackson.JsonParserConverter;

public class PhoneNumberDeserializer extends StdDeserializer<PhoneNumber> {

    protected PhoneNumberDeserializer() {
        super(PhoneNumber.class);
    }

    @Override
    public PhoneNumber deserialize(final JsonParser parser, final DeserializationContext context) {
        final JsonNode node = JsonParserConverter.toNode(parser);
        final String type = node.get("type").asText();
        final String value = node.get("value").asText();
        if (MobilePhoneNumber.TYPE.equals(type)) {
            return new MobilePhoneNumber(value);
        }
        return new OtherPhoneNumber(value);
    }

}
