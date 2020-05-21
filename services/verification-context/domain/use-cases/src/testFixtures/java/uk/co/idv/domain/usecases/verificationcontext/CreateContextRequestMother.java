package uk.co.idv.domain.usecases.verificationcontext;


import uk.co.idv.domain.entities.activity.ActivityMother;
import uk.co.idv.domain.entities.channel.ChannelMother;
import uk.co.idv.domain.entities.identity.alias.AliasesMother;

public class CreateContextRequestMother {

    public static CreateContextRequest build() {
        return CreateContextRequest.builder()
                .channel(ChannelMother.fake())
                .activity(ActivityMother.fake())
                .providedAlias(AliasesMother.creditCardNumber())
                .build();
    }

}
