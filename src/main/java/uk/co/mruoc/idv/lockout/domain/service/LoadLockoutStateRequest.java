package uk.co.mruoc.idv.lockout.domain.service;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
@EqualsAndHashCode
public class LoadLockoutStateRequest {

    private final UUID idvId;

}
