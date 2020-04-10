package uk.co.idv.domain.usecases.verificationcontext;

import lombok.Builder;
import lombok.Getter;
import uk.co.idv.domain.entities.activity.Activity;
import uk.co.idv.domain.entities.channel.Channel;
import uk.co.idv.domain.entities.identity.alias.Alias;
import uk.co.idv.domain.entities.identity.Identity;

@Builder
@Getter
public class LoadSequencesRequest {

    private final Channel channel;
    private final Activity activity;
    private final Alias providedAlias;
    private final Identity identity;

}
