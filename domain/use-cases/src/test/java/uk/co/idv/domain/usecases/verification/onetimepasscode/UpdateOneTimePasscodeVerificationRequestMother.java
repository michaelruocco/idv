package uk.co.idv.domain.usecases.verification.onetimepasscode;

import java.util.UUID;

public class UpdateOneTimePasscodeVerificationRequestMother {

    public static UpdateOneTimePasscodeVerificationRequest build() {
        return UpdateOneTimePasscodeVerificationRequest.builder()
                .id(UUID.fromString("b070c306-3004-48f9-b84a-97611bfed4ff"))
                .contextId(UUID.fromString("61152e11-3513-40be-bf11-a0c13a8cbbbe"))
                .deliveryMethodId(UUID.fromString("53acd147-ff5b-48fc-a12b-ed38dcff6a26"))
                .build();
    }

}
