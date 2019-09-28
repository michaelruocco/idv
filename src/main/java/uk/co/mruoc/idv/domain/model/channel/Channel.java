package uk.co.mruoc.idv.domain.model.channel;

public interface Channel {

    String getId();

    default boolean hasId(final String id) {
        return getId().equals(id);
    }

}
