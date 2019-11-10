package uk.co.idv.json.channel;

import com.fasterxml.jackson.databind.JsonNode;
import uk.co.idv.domain.entities.channel.Channel;
import uk.co.idv.domain.entities.channel.Rsa;

public class JsonNodeToRsaConverter implements JsonNodeToChannelConverter {

    @Override
    public Channel toChannel(final JsonNode node) {
        return new Rsa();
    }

}
