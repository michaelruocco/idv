package uk.co.idv.domain.usecases.exception;

public class ChannelNotSupportedException extends RuntimeException {

    public ChannelNotSupportedException(final String channelId) {
        super(channelId);
    }

}
