package uk.co.idv.domain.entities.lockout.policy;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.identity.alias.Alias;
import uk.co.idv.domain.entities.identity.alias.AliasesMother;
import uk.co.idv.domain.entities.lockout.DefaultLockoutRequest;
import uk.co.idv.domain.entities.lockout.LockoutRequest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class AliasLevelLockoutPolicyParametersTest {

    private final AliasLevelLockoutPolicyParameters parameters = new CustomAliasLevelLockoutPolicyParameters();

    @Test
    void shouldReturnAliasType() {
        assertThat(parameters.getAliasType()).isEqualTo(CustomAliasLevelLockoutPolicyParameters.ALIAS_TYPE);
    }

    @Test
    void shouldReturnUseAliasLevelLockingTrue() {
        final LockoutPolicyParameters aliasLevelParameters = new CustomAliasLevelLockoutPolicyParameters();

        final boolean useAliasLevelLocking = aliasLevelParameters.isAliasLevel();

        assertThat(useAliasLevelLocking).isTrue();
    }

    @Test
    void shouldReturnAppliesToTrueIfChannelAndActivityAndAliasTypeMatch() {
        final Alias alias = AliasesMother.creditCardNumber();
        final LockoutPolicyParameters aliasLevelParameters = new CustomAliasLevelLockoutPolicyParameters(alias.getType());
        final LockoutRequest request = DefaultLockoutRequest.builder()
                .channelId(CustomAliasLevelLockoutPolicyParameters.CHANNEL_ID)
                .activityName(CustomAliasLevelLockoutPolicyParameters.ACTIVITY_NAME)
                .alias(alias)
                .build();

        final boolean applies = aliasLevelParameters.appliesTo(request);

        assertThat(applies).isTrue();
    }

    @Test
    void shouldReturnAppliesToFalseIfChannelIdDoesNotMatch() {
        final Alias alias = AliasesMother.creditCardNumber();
        final LockoutPolicyParameters aliasLevelParameters = new CustomAliasLevelLockoutPolicyParameters(alias.getType());
        final LockoutRequest request = DefaultLockoutRequest.builder()
                .channelId("other-channel")
                .activityName(CustomAliasLevelLockoutPolicyParameters.ACTIVITY_NAME)
                .alias(alias)
                .build();

        final boolean applies = aliasLevelParameters.appliesTo(request);

        assertThat(applies).isFalse();
    }

    @Test
    void shouldReturnAppliesToFalseIfActivityNameDoesNotMatch() {
        final Alias alias = AliasesMother.creditCardNumber();
        final LockoutPolicyParameters aliasLevelParameters = new CustomAliasLevelLockoutPolicyParameters(alias.getType());
        final LockoutRequest request = DefaultLockoutRequest.builder()
                .channelId(CustomAliasLevelLockoutPolicyParameters.CHANNEL_ID)
                .activityName("other-activity")
                .alias(alias)
                .build();

        final boolean applies = aliasLevelParameters.appliesTo(request);

        assertThat(applies).isFalse();
    }

    @Test
    void shouldReturnAppliesToFalseIfAliasTypeDoesNotMatch() {
        final LockoutPolicyParameters aliasLevelParameters = new CustomAliasLevelLockoutPolicyParameters();
        final LockoutRequest request = DefaultLockoutRequest.builder()
                .channelId(CustomAliasLevelLockoutPolicyParameters.CHANNEL_ID)
                .activityName(CustomAliasLevelLockoutPolicyParameters.ACTIVITY_NAME)
                .alias(AliasesMother.debitCardNumber())
                .build();

        final boolean applies = aliasLevelParameters.appliesTo(request);

        assertThat(applies).isFalse();
    }

    private static class CustomAliasLevelLockoutPolicyParameters extends AliasLevelLockoutPolicyParameters {

        private static final UUID ID = UUID.fromString("0addd5b5-24ef-4298-9ebd-ed6f506f59c2");
        private static final String LOCKOUT_TYPE = "lockout-type";
        private static final String RECORD_ATTEMPT_STRATEGY_TYPE = "record-attempt-strategy-type";
        private static final String CHANNEL_ID = "fake-channel";
        private static final String ACTIVITY_NAME = "fake-activity";
        private static final String ALIAS_TYPE = "fake-alias-type";

        private CustomAliasLevelLockoutPolicyParameters() {
            this(ALIAS_TYPE);
        }

        private CustomAliasLevelLockoutPolicyParameters(final String aliasType) {
            super(ID,
                    LOCKOUT_TYPE,
                    RECORD_ATTEMPT_STRATEGY_TYPE,
                    CHANNEL_ID,
                    ACTIVITY_NAME,
                    aliasType);
        }

    }

}
