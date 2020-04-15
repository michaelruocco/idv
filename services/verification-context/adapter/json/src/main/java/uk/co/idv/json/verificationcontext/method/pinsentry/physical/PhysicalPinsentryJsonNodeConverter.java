package uk.co.idv.json.verificationcontext.method.pinsentry.physical;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.Ineligible;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.PinsentryFunction;
import uk.co.idv.domain.entities.cardnumber.CardNumber;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical.PhysicalPinsentry;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical.PhysicalPinsentryEligible;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical.PhysicalPinsentryIneligible;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResults;
import uk.co.idv.json.verificationcontext.method.AbstractVerificationMethodJsonNodeConverter;
import uk.co.idv.utils.json.converter.jackson.JsonNodeConverter;

import java.util.Arrays;
import java.util.Collection;

import static uk.co.idv.json.verificationcontext.method.VerificationMethodJsonNodeConverter.extractEligible;
import static uk.co.idv.json.verificationcontext.method.VerificationMethodJsonNodeConverter.extractResults;
import static uk.co.idv.json.verificationcontext.method.pinsentry.PinsentryVerificationMethodJsonNodeConverter.extractFunction;

@Slf4j
public class PhysicalPinsentryJsonNodeConverter extends AbstractVerificationMethodJsonNodeConverter {

    public PhysicalPinsentryJsonNodeConverter() {
        super(PhysicalPinsentry.NAME);
    }

    @Override
    public VerificationMethod toMethod(final JsonNode node,
                                       final JsonParser parser,
                                       final DeserializationContext context) {
        final boolean eligible = extractEligible(node);
        final PinsentryFunction function = extractFunction(node);
        if (eligible) {
            final VerificationResults results = extractResults(node, parser);
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
