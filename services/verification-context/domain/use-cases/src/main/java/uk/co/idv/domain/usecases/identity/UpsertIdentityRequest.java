package uk.co.idv.domain.usecases.identity;

import lombok.Builder;
import lombok.Getter;
import uk.co.idv.domain.entities.channel.Channel;
import uk.co.idv.domain.entities.identity.alias.Alias;

@Builder
@Getter
public class UpsertIdentityRequest {

    private final Channel channel;
    private final Alias providedAlias;

}
