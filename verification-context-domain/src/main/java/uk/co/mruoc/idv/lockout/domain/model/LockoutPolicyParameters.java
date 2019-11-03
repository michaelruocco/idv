package uk.co.mruoc.idv.lockout.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.UUID;

@RequiredArgsConstructor
@Getter
@Builder
public class LockoutPolicyParameters {

    private final UUID id;
    private final String lockoutType;
    private final String recordAttemptStrategyType;
    private final Collection<String> channelIds;
    private final Collection<String> activityNames;
    private final Collection<String> aliasTypes;

}
