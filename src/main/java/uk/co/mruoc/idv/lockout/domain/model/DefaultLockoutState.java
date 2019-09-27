package uk.co.mruoc.idv.lockout.domain.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DefaultLockoutState implements LockoutState {

    private final VerificationAttempts attempts;

}
