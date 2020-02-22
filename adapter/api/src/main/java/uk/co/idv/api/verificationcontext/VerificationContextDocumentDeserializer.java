package uk.co.idv.api.verificationcontext;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import uk.co.idv.domain.entities.activity.Activity;
import uk.co.idv.domain.entities.channel.Channel;
import uk.co.idv.domain.entities.identity.Identity;
import uk.co.idv.domain.entities.identity.alias.Alias;
import uk.co.idv.domain.entities.verificationcontext.VerificationContext;
import uk.co.idv.domain.entities.verificationcontext.VerificationSequences;
import uk.co.mruoc.jsonapi.ApiDataDocumentRequest;
import uk.co.mruoc.jsonapi.ApiDocumentDeserializer;
import uk.co.mruoc.jsonapi.ApiDocumentFactory;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.time.Instant;
import java.util.UUID;

public class VerificationContextDocumentDeserializer extends ApiDocumentDeserializer<VerificationContextDocument> {

    public VerificationContextDocumentDeserializer() {
        super(VerificationContextDocument.class, new DocumentFactory());
    }

    private static class DocumentFactory implements ApiDocumentFactory<VerificationContextDocument> {

        @Override
        public VerificationContextDocument build(final ApiDataDocumentRequest request) {
            try {
                final VerificationContext attributes = toAttributes(request);
                return new VerificationContextDocument(attributes);
            } catch (final IOException e) {
                throw new UncheckedIOException(e);
            }
        }

        private VerificationContext toAttributes(final ApiDataDocumentRequest request) throws IOException {
            final JsonNode dataNode = request.getDataNode();
            final UUID id = toId(dataNode);
            final JsonNode attributesNode = extractAttributes(dataNode);
            final JsonParser parser = request.getParser();
            return VerificationContext.builder()
                    .id(id)
                    .channel(attributesNode.get("channel").traverse(parser.getCodec()).readValueAs(Channel.class))
                    .providedAlias(attributesNode.get("providedAlias").traverse(parser.getCodec()).readValueAs(Alias.class))
                    .identity(attributesNode.get("identity").traverse(parser.getCodec()).readValueAs(Identity.class))
                    .activity(attributesNode.get("activity").traverse(parser.getCodec()).readValueAs(Activity.class))
                    .created(Instant.parse(attributesNode.get("created").asText()))
                    .expiry(Instant.parse(attributesNode.get("expiry").asText()))
                    .sequences(attributesNode.get("sequences").traverse(parser.getCodec()).readValueAs(VerificationSequences.class))
                    .build();
        }

        private static UUID toId(final JsonNode node) {
            return UUID.fromString(node.get("id").asText());
        }

        private static JsonNode extractAttributes(final JsonNode node) {
            return node.get("attributes");
        }

    }

}
