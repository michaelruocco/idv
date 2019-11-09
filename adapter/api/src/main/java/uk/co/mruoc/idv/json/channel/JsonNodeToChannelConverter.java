package uk.co.mruoc.idv.json.channel;

import com.fasterxml.jackson.databind.JsonNode;
import uk.co.mruoc.idv.domain.model.channel.Channel;

public interface JsonNodeToChannelConverter {

    Channel toChannel(final JsonNode node);

}
