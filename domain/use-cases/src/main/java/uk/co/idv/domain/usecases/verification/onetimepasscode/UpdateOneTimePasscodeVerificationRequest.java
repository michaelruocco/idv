package uk.co.idv.domain.usecases.verification.onetimepasscode;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
@EqualsAndHashCode
public class UpdateOneTimePasscodeVerificationRequest {

    private final UUID id;
    private final UUID contextId;
    private final UUID deliveryMethodId;

}
