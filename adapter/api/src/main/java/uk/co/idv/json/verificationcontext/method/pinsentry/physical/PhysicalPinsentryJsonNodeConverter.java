package uk.co.idv.json.verificationcontext.method.pinsentry.physical;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.Ineligible;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.PinsentryFunction;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical.CardNumber;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical.PhysicalPinsentry;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical.PhysicalPinsentryEligible;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical.PhysicalPinsentryIneligible;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResults;
import uk.co.idv.json.verificationcontext.method.VerificationMethodJsonNodeConverter;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Arrays;
import java.util.Collection;

@Slf4j
public class PhysicalPinsentryJsonNodeConverter implements VerificationMethodJsonNodeConverter {

    @Override
    public boolean supportsMethod(final String name) {
        boolean supported = PhysicalPinsentry.NAME.equals(name);
        log.info("returning supported {} for method name {}", supported, name);
        return supported;
    }

    @Override
    public VerificationMethod toMethod(final JsonNode node,
                                       final JsonParser parser,
                                       final DeserializationContext context) {
        try {
            final boolean eligible = node.get("eligible").asBoolean();
            final PinsentryFunction function = PinsentryFunction.valueOf(node.get("function").asText().toUpperCase());
            if (eligible) {
                final VerificationResults results = node.get("results").traverse(parser.getCodec()).readValueAs(VerificationResults.class);
                final Collection<CardNumber> cardNumbers = Arrays.asList(node.get("cardNumbers").traverse(parser.getCodec()).readValueAs(CardNumber[].class));
                return new PhysicalPinsentryEligible(function, cardNumbers);
            }
            final String reason = node.get("reason").asText();
            return new PhysicalPinsentryIneligible(new Ineligible(reason), function);
        } catch (final IOException e) {
            throw new UncheckedIOException(e);
        }
    }

}
