package uk.co.idv.json.verificationcontext.method.pinsentry.physical;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.domain.entities.card.number.CardNumber;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.Ineligible;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.PinsentryFunction;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical.PhysicalPinsentry;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical.PhysicalPinsentryEligible;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical.PhysicalPinsentryIneligible;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResults;
import uk.co.idv.utils.json.converter.jackson.JsonNodeConverter;
import uk.co.idv.utils.json.converter.jackson.JsonParserConverter;

import java.util.Arrays;
import java.util.Collection;

import static uk.co.idv.json.verificationcontext.method.VerificationMethodJsonNodeConverter.isEligible;
import static uk.co.idv.json.verificationcontext.method.VerificationMethodJsonNodeConverter.toResults;
import static uk.co.idv.json.verificationcontext.method.pinsentry.PinsentryFunctionJsonNodeConverter.extractFunction;

public class PhysicalPinsentryDeserializer extends StdDeserializer<PhysicalPinsentry> {

    public PhysicalPinsentryDeserializer() {
        super(PhysicalPinsentry.class);
    }

    @Override
    public PhysicalPinsentry deserialize(final JsonParser parser, final DeserializationContext context) {
        final JsonNode node = JsonParserConverter.toNode(parser);
        final PinsentryFunction function = extractFunction(node);
        if (isEligible(node)) {
            final VerificationResults results = toResults(node, parser);
            final Collection<CardNumber> cardNumbers = extractCardNumbers(node, parser);
            return new PhysicalPinsentryEligible(function, cardNumbers, results);
        }
        final Ineligible reason = extractReason(node);
        return new PhysicalPinsentryIneligible(reason, function);
    }

    private static Collection<CardNumber> extractCardNumbers(final JsonNode node, final JsonParser parser) {
        final CardNumber[] cardNumbers = JsonNodeConverter.toObject(node.get("cardNumbers"), parser, CardNumber[].class);
        return Arrays.asList(cardNumbers);
    }

    private Ineligible extractReason(final JsonNode node) {
        final String reason = node.get("reason").asText();
        return new Ineligible(reason);
    }

}
