package uk.co.idv.domain.usecases.onetimepasscode.send;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
@EqualsAndHashCode
public class ResendOneTimePasscodeRequest {

    private final UUID id;
    private final UUID contextId;
    private final UUID deliveryMethodId;

}
