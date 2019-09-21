package uk.co.mruoc.idv.identity.domain.service;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import uk.co.mruoc.idv.domain.model.channel.Channel;
import uk.co.mruoc.idv.identity.domain.model.Alias;

@Builder
@Getter
@EqualsAndHashCode
public class UpsertIdentityRequest {

    private final Channel channel;
    private final Alias providedAlias;

}
