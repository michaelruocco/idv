package uk.co.idv.json.verificationcontext.method.pinsentry.mobile;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.mobile.MobilePinsentry;
import uk.co.idv.utils.json.converter.jackson.JsonParserConverter;

import static uk.co.idv.json.verificationcontext.method.VerificationMethodJsonNodeConverter.toEligibility;
import static uk.co.idv.json.verificationcontext.method.VerificationMethodJsonNodeConverter.toResults;
import static uk.co.idv.json.verificationcontext.method.pinsentry.PinsentryFunctionJsonNodeConverter.extractParams;

public class MobilePinsentryDeserializer extends StdDeserializer<MobilePinsentry> {

    public MobilePinsentryDeserializer() {
        super(MobilePinsentry.class);
    }

    @Override
    public MobilePinsentry deserialize(final JsonParser parser, final DeserializationContext context) {
        final JsonNode node = JsonParserConverter.toNode(parser);
        return MobilePinsentry.builder()
                .params(extractParams(node, parser))
                .eligibility(toEligibility(node, parser))
                .results(toResults(node, parser))
                .build();
    }

}
