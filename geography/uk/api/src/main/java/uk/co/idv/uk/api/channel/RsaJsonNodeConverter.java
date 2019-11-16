package uk.co.idv.uk.api.channel;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.domain.entities.channel.Channel;
import uk.co.idv.json.channel.ChannelJsonNodeConverter;
import uk.co.idv.uk.domain.entities.channel.Rsa;

import java.util.Optional;

@Slf4j
public class RsaJsonNodeConverter implements ChannelJsonNodeConverter {

    @Override
    public boolean supportsChannel(final String id) {
        return Rsa.ID.equals(id);
    }

    @Override
    public Channel toChannel(final JsonNode node) {
        final Optional<JsonNode> issuerSessionId = Optional.ofNullable(node.get("issuerSessionId"));
        log.info("building rsa channel with issuerSessionId {}", issuerSessionId);
        return issuerSessionId
                .map(id -> new Rsa(id.asText()))
                .orElse(new Rsa());
    }

}
