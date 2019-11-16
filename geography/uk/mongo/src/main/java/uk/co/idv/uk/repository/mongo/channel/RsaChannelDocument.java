package uk.co.idv.uk.repository.mongo.channel;

import lombok.Data;
import lombok.EqualsAndHashCode;
import uk.co.idv.repository.mongo.channel.ChannelDocument;

@Data
@EqualsAndHashCode(callSuper = true)
public class RsaChannelDocument extends ChannelDocument {

    private String issuerSessionId;

}
