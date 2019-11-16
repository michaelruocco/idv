package uk.co.idv.repository.mongo.channel;

import lombok.extern.slf4j.Slf4j;
import uk.co.idv.domain.usecases.exception.ChannelNotSupportedException;
import uk.co.idv.domain.entities.channel.Channel;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

@Slf4j
public class ChannelDocumentConverterDelegator {

    private final Collection<ChannelDocumentConverter> converters;

    public ChannelDocumentConverterDelegator(final ChannelDocumentConverter... converters) {
        this(Arrays.asList(converters));
    }

    public ChannelDocumentConverterDelegator(final Collection<ChannelDocumentConverter> converters) {
        this.converters = converters;
    }

    public Channel toChannel(final ChannelDocument document) {
        final String id = document.getId();
        final Optional<ChannelDocumentConverter> documentConverter= findConverter(id);
        log.info("found converter {} for channel id {}", documentConverter, id);
        return documentConverter.map(converter -> converter.toChannel(document))
                .orElseThrow(() -> new ChannelNotSupportedException(id));
    }

    public ChannelDocument toDocument(final Channel channel) {
        final String id = channel.getId();
        return findConverter(id)
                .map(converter -> converter.toDocument(channel))
                .orElseThrow(() -> new ChannelNotSupportedException(id));
    }

    private Optional<ChannelDocumentConverter> findConverter(final String id) {
        return converters.stream().filter(converter -> converter.supportsChannel(id)).findFirst();
    }

}
