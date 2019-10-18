package uk.co.mruoc.idv.identity.domain.service;

import lombok.Builder;
import lombok.Getter;
import uk.co.mruoc.idv.identity.domain.model.Alias;

@Builder
@Getter
public class LoadIdentityRequest {

    private final Alias alias;

}
