package uk.co.idv.domain.usecases.verification.onetimepasscode;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class UpdateOneTimePasscodeVerificationRequest {

    private final UUID id;
    private final UUID contextId;
    private final UUID deliveryMethodId;

}
