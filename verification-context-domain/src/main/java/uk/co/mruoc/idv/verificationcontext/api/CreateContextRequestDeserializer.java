package uk.co.mruoc.idv.verificationcontext.api;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.mruoc.idv.domain.model.activity.Activity;
import uk.co.mruoc.idv.domain.model.channel.Channel;
import uk.co.mruoc.idv.identity.domain.model.Alias;
import uk.co.mruoc.idv.verificationcontext.domain.service.CreateContextRequest;

import java.io.IOException;

public class CreateContextRequestDeserializer extends StdDeserializer<CreateContextRequest> {

    CreateContextRequestDeserializer() {
        super(CreateContextRequest.class);
    }

    @Override
    public CreateContextRequest deserialize(final JsonParser parser,final DeserializationContext context) throws IOException {
        final JsonNode node = parser.getCodec().readTree(parser);
        return CreateContextRequest.builder()
                .channel(toChannel(parser, node.get("channel")))
                .activity(toActivity(parser, node.get("activity")))
                .providedAlias(toAlias(parser, node.get("providedAlias")))
                .build();
    }

    private static Channel toChannel(final JsonParser parser, final JsonNode node) throws IOException {
        return node.traverse(parser.getCodec()).readValueAs(Channel.class);
    }

    private static Activity toActivity(final JsonParser parser, final JsonNode node) throws IOException {
        return node.traverse(parser.getCodec()).readValueAs(Activity.class);
    }

    private static Alias toAlias(final JsonParser parser, final JsonNode node) throws IOException {
        return node.traverse(parser.getCodec()).readValueAs(Alias.class);
    }

}
