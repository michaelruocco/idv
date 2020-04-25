package uk.co.idv.json.verificationcontext.method.pinsentry.mobile;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.PinsentryFunction;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.mobile.MobilePinsentry;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.mobile.MobilePinsentryEligible;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.mobile.MobilePinsentryIneligible;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical.PhysicalPinsentry;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResults;
import uk.co.idv.utils.json.converter.jackson.JsonParserConverter;

import static uk.co.idv.json.verificationcontext.method.VerificationMethodJsonNodeConverter.isEligible;
import static uk.co.idv.json.verificationcontext.method.VerificationMethodJsonNodeConverter.toResults;
import static uk.co.idv.json.verificationcontext.method.pinsentry.PinsentryFunctionJsonNodeConverter.extractFunction;

public class MobilePinsentryDeserializer extends StdDeserializer<MobilePinsentry> {

    public MobilePinsentryDeserializer() {
        super(PhysicalPinsentry.class);
    }

    @Override
    public MobilePinsentry deserialize(final JsonParser parser, final DeserializationContext context) {
        final JsonNode node = JsonParserConverter.toNode(parser);
        final PinsentryFunction function = extractFunction(node);
        if (isEligible(node)) {
            final VerificationResults results = toResults(node, parser);
            return new MobilePinsentryEligible(function, results);
        }
        return new MobilePinsentryIneligible(function);
    }

}
