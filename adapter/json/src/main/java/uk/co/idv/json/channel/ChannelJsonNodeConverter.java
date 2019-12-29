package uk.co.idv.json.channel;

import com.fasterxml.jackson.databind.JsonNode;
import uk.co.idv.domain.entities.channel.Channel;

public interface ChannelJsonNodeConverter {

    boolean supportsChannel(final String id);

    Channel toChannel(final JsonNode node);

}
