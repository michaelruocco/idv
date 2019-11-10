package uk.co.idv.domain.usecases.identity;

import lombok.Builder;
import lombok.Getter;
import uk.co.idv.domain.entities.identity.alias.Alias;

@Builder
@Getter
public class LoadIdentityRequest {

    private final Alias alias;

}
