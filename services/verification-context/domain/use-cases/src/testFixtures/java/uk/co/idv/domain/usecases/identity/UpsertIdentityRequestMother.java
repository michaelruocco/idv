package uk.co.idv.domain.usecases.identity;

import uk.co.idv.domain.entities.channel.ChannelMother;
import uk.co.idv.domain.entities.identity.alias.Alias;
import uk.co.idv.domain.entities.identity.alias.AliasesMother;

public class UpsertIdentityRequestMother {

    public static UpsertIdentityRequest build() {
        return withProvidedAlias(AliasesMother.creditCardNumber());
    }

    public static UpsertIdentityRequest withProvidedAlias(final Alias providedAlias) {
        return UpsertIdentityRequest.builder()
                .providedAlias(providedAlias)
                .channel(ChannelMother.fake())
                .build();
    }

}
