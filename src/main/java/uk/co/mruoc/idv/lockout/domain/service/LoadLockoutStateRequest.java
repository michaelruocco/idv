package uk.co.mruoc.idv.lockout.domain.service;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class LoadLockoutStateRequest {

    private final UUID idvId;

}
