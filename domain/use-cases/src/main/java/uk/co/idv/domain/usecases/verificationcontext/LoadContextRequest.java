package uk.co.idv.domain.usecases.verificationcontext;


import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class LoadContextRequest {

    private final UUID id;

}
