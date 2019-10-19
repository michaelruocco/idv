package uk.co.mruoc.idv.domain.exception;

public class ChannelNotSupportedException extends RuntimeException {

    public ChannelNotSupportedException(final String channelId) {
        super(channelId);
    }

}
