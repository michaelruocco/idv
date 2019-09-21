package uk.co.mruoc.idv.verificationcontext.domain.service;


import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
@EqualsAndHashCode
public class GetContextRequest {

    private final UUID id;

}
