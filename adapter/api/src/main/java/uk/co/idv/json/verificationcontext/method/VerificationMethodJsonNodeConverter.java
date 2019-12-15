package uk.co.idv.json.verificationcontext.method;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import uk.co.idv.domain.entities.channel.Channel;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;

import java.io.IOException;

public interface VerificationMethodJsonNodeConverter {

    boolean supportsMethod(final String name);

    VerificationMethod toMethod(final JsonNode node, final JsonParser parser, final DeserializationContext context);

}
