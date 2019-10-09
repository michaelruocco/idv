package uk.co.mruoc.idv.lockout.jsonapi;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import uk.co.mruoc.idv.lockout.domain.service.LockoutRequest;
import uk.co.mruoc.jsonapi.AbstractJsonApiDocumentDeserializer;

import java.io.IOException;

public class ResetLockoutStateDocumentDeserializer extends AbstractJsonApiDocumentDeserializer<ResetLockoutStateDocument> {

    protected ResetLockoutStateDocumentDeserializer() {
        super(ResetLockoutStateDocument.class);
    }

    @Override
    protected ResetLockoutStateDocument toDocument(final JsonParser parser,
                                                   final JsonNode attributesNode,
                                                   final String jsonApiType) throws IOException {
        final LockoutRequest attributes = attributesNode.traverse(parser.getCodec()).readValueAs(LockoutRequest.class);
        return new ResetLockoutStateDocument(attributes);
    }

}
