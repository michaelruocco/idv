package uk.co.idv.domain.usecases.verificationcontext.sequence;

import uk.co.idv.domain.entities.activity.ActivityMother;
import uk.co.idv.domain.entities.channel.ChannelMother;
import uk.co.idv.domain.entities.identity.IdentityMother;
import uk.co.idv.domain.entities.identity.alias.AliasesMother;

public class LoadSequencesRequestMother {

    public static LoadSequencesRequest build() {
        return LoadSequencesRequest.builder()
                .channel(ChannelMother.fake())
                .activity(ActivityMother.fake())
                .providedAlias(AliasesMother.creditCardNumber())
                .identity(IdentityMother.build())
                .build();
    }

}
