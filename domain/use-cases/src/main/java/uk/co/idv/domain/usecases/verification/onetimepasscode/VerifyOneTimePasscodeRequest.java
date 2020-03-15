package uk.co.idv.domain.usecases.verification.onetimepasscode;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Collection;
import java.util.UUID;

@Builder
@Getter
@EqualsAndHashCode
public class VerifyOneTimePasscodeRequest {

    private final UUID id;
    private final Collection<String> passcodes;

}
