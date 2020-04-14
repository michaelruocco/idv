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

import static uk.co.idv.utils.json.converter.jackson.JsonNodeConverter.toInstant;
import static uk.co.idv.utils.json.converter.jackson.JsonNodeConverter.toObject;
import static uk.co.idv.utils.json.converter.jackson.JsonNodeConverter.toUUID;

public class VerificationContextDocumentDeserializer extends ApiDocumentDeserializer<VerificationContextDocument> {

    public VerificationContextDocumentDeserializer() {
        super(VerificationContextDocument.class, new DocumentFactory());
    }

    private static class DocumentFactory implements ApiDocumentFactory<VerificationContextDocument> {

        @Override
        public VerificationContextDocument build(final ApiDataDocumentRequest request) throws IOException {
            final VerificationContext attributes = toAttributes(request);
            return new VerificationContextDocument(attributes);
        }

        private VerificationContext toAttributes(final ApiDataDocumentRequest request) throws IOException {
            final JsonNode data = request.getDataNode();
            final JsonNode attributes = data.get("attributes");
            final JsonParser parser = request.getParser();
            return VerificationContext.builder()
                    .id(toUUID(data.get("id")))
                    .channel(toObject(attributes.get("channel"), parser, Channel.class))
                    .providedAlias(toObject(attributes.get("providedAlias"), parser, Alias.class))
                    .identity(toObject(attributes.get("identity"), parser, Identity.class))
                    .activity(toObject(attributes.get("activity"), parser, Activity.class))
                    .created(toInstant(attributes.get("created")))
                    .expiry(toInstant(attributes.get("expiry")))
                    .sequences(toObject(attributes.get("sequences"), parser, VerificationSequences.class))
                    .build();
        }

    }

}
