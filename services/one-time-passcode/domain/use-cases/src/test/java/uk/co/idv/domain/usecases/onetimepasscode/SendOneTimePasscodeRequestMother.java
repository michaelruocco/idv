package uk.co.idv.domain.usecases.onetimepasscode;

import java.util.UUID;

public class SendOneTimePasscodeRequestMother {

    public static SendOneTimePasscodeRequest build() {
        return SendOneTimePasscodeRequest.builder()
                .contextId(UUID.fromString("61152e11-3513-40be-bf11-a0c13a8cbbbe"))
                .deliveryMethodId(UUID.fromString("53acd147-ff5b-48fc-a12b-ed38dcff6a26"))
                .build();
    }

}
