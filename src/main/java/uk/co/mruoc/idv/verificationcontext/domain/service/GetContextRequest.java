package uk.co.mruoc.idv.verificationcontext.domain.service;


import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class GetContextRequest {

    private final UUID id;

}
