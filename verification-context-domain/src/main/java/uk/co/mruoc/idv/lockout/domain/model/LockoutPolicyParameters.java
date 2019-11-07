package uk.co.mruoc.idv.lockout.domain.model;

import uk.co.mruoc.idv.lockout.domain.service.LockoutRequest;

import java.util.Collection;
import java.util.UUID;

public interface LockoutPolicyParameters {

    UUID getId();

    String getRecordAttemptStrategyType();

    String getLockoutType();

    boolean appliesTo(LockoutRequest request);

    boolean useAliasLevelLocking();

    Collection<String> getChannelIds();

    Collection<String> getActivityNames();

    Collection<String> getAliasTypes();

}
