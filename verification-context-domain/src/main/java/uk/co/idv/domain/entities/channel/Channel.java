package uk.co.idv.domain.entities.channel;

public interface Channel {

    String getId();

    default boolean hasId(final String id) {
        return getId().equals(id);
    }

}
