package uk.co.idv.json.verificationcontext;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.domain.entities.activity.Activity;
import uk.co.idv.domain.entities.channel.Channel;
import uk.co.idv.domain.entities.identity.alias.Alias;
import uk.co.idv.domain.usecases.verificationcontext.CreateContextRequest;

import java.io.IOException;

public class CreateContextRequestDeserializer extends StdDeserializer<CreateContextRequest> {

    public CreateContextRequestDeserializer() {
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
