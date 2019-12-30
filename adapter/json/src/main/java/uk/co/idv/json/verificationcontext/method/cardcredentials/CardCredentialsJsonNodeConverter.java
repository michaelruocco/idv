package uk.co.idv.json.verificationcontext.method.cardcredentials;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.method.cardcredentials.CardCredentials;
import uk.co.idv.domain.entities.verificationcontext.method.cardcredentials.CardCredentialsEligible;
import uk.co.idv.domain.entities.verificationcontext.method.cardcredentials.CardCredentialsIneligible;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResults;
import uk.co.idv.json.verificationcontext.method.VerificationMethodJsonNodeConverter;

import java.io.IOException;
import java.io.UncheckedIOException;

@Slf4j
public class CardCredentialsJsonNodeConverter implements VerificationMethodJsonNodeConverter {

    @Override
    public boolean supportsMethod(final String name) {
        boolean supported = CardCredentials.NAME.equals(name);
        log.info("returning supported {} for method name {}", supported, name);
        return supported;
    }

    @Override
    public VerificationMethod toMethod(final JsonNode node,
                                       final JsonParser parser,
                                       final DeserializationContext context) {
        try {
            final boolean eligible = node.get("eligible").asBoolean();
            if (eligible) {
                final VerificationResults results = node.get("results").traverse(parser.getCodec()).readValueAs(VerificationResults.class);
                return new CardCredentialsEligible(results);
            }
            return new CardCredentialsIneligible();
        } catch (final IOException e) {
            throw new UncheckedIOException(e);
        }
    }

}
