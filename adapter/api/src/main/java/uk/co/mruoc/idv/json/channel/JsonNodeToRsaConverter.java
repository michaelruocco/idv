package uk.co.mruoc.idv.json.channel;

import com.fasterxml.jackson.databind.JsonNode;
import uk.co.mruoc.idv.domain.model.channel.Channel;
import uk.co.mruoc.idv.domain.model.channel.Rsa;

public class JsonNodeToRsaConverter implements JsonNodeToChannelConverter {

    @Override
    public Channel toChannel(final JsonNode node) {
        return new Rsa();
    }

}
