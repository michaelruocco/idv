package uk.co.idv.domain.entities.channel;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class SimpleChannel implements Channel {

    private final String id;

    public SimpleChannel(final String id) {
        this.id = id;
    }

}
