package uk.co.idv.json.verificationcontext.method.pinsentry.mobile;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.PinsentryFunction;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.mobile.MobilePinsentry;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.mobile.MobilePinsentryEligible;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.mobile.MobilePinsentryIneligible;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResults;
import uk.co.idv.json.verificationcontext.method.AbstractVerificationMethodJsonNodeConverter;

import static uk.co.idv.json.verificationcontext.method.pinsentry.PinsentryVerificationMethodJsonNodeConverter.extractFunction;
import static uk.co.idv.json.verificationcontext.method.VerificationMethodJsonNodeConverter.extractEligible;
import static uk.co.idv.json.verificationcontext.method.VerificationMethodJsonNodeConverter.extractResults;

@Slf4j
public class MobilePinsentryJsonNodeConverter extends AbstractVerificationMethodJsonNodeConverter {

    public MobilePinsentryJsonNodeConverter() {
        super(MobilePinsentry.NAME);
    }

    @Override
    public VerificationMethod toMethod(final JsonNode node,
                                       final JsonParser parser,
                                       final DeserializationContext context) {
        final boolean eligible = extractEligible(node);
        final PinsentryFunction function = extractFunction(node);
        if (eligible) {
            final VerificationResults results = extractResults(node, parser);
            return new MobilePinsentryEligible(function, results);
        }
        return new MobilePinsentryIneligible(function);
    }

}
