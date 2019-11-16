package uk.co.idv.uk.api.channel;

import com.fasterxml.jackson.databind.JsonNode;
import uk.co.idv.domain.entities.channel.Channel;
import uk.co.idv.json.channel.ChannelJsonNodeConverter;
import uk.co.idv.uk.domain.entities.channel.Rsa;

public class RsaJsonNodeConverter implements ChannelJsonNodeConverter {

    @Override
    public boolean supportsChannel(final String id) {
        return Rsa.ID.equals(id);
    }

    @Override
    public Channel toChannel(final JsonNode node) {
        return new Rsa();
    }

}
