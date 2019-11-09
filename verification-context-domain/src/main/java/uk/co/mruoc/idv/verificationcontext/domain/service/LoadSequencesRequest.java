package uk.co.mruoc.idv.verificationcontext.domain.service;

import lombok.Builder;
import lombok.Getter;
import uk.co.idv.domain.entities.activity.Activity;
import uk.co.idv.domain.entities.channel.Channel;
import uk.co.idv.domain.entities.identity.Alias;
import uk.co.idv.domain.entities.identity.Identity;

@Builder
@Getter
public class LoadSequencesRequest {

    private final Channel channel;
    private final Activity activity;
    private final Alias providedAlias;
    private final Identity identity;

}
