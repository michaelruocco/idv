package uk.co.idv.domain.entities.policy;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.identity.alias.Alias;
import uk.co.idv.domain.entities.identity.alias.AliasesMother;
import uk.co.idv.domain.entities.lockout.DefaultLockoutRequest;
import uk.co.idv.domain.entities.lockout.LockoutRequest;

import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultPolicyLevelTest {

    private static final String CHANNEL_ID = "channel-id";
    private static final String ACTIVITY_NAME = "activity-name";
    private static final Collection<String> ACTIVITY_NAMES = Collections.singleton(ACTIVITY_NAME);
    private static final Alias ALIAS = AliasesMother.creditCardNumber();
    private static final Collection<String> ALIAS_TYPES = Collections.singleton(ALIAS.getType());

    @Test
    void shouldNotBeAliasLevelIfAliasTypesIsEmpty() {
        final PolicyLevel level = DefaultPolicyLevel.builder().build();

        final boolean includesAlias = level.isAliasLevel();

        assertThat(includesAlias).isFalse();
    }

    @Test
    void shouldNotBeAliasLevelIfAliasTypesContainsAll() {
        final PolicyLevel level = DefaultPolicyLevel.builder()
                .aliasTypes(Collections.singleton(PolicyLevel.ALL))
                .build();

        final boolean includesAlias = level.isAliasLevel();

        assertThat(includesAlias).isFalse();
    }

    @Test
    void shouldNotBeAliasLevelIfAliasTypeIsSet() {
        final PolicyLevel level = DefaultPolicyLevel.builder()
                .aliasTypes(Collections.singleton("alias-type"))
                .build();

        final boolean includesAlias = level.isAliasLevel();

        assertThat(includesAlias).isTrue();
    }

    @Test
    void shouldReturnChannelId() {
        final PolicyLevel level = DefaultPolicyLevel.builder()
                .channelId(CHANNEL_ID)
                .build();

        assertThat(level.getChannelId()).isEqualTo(CHANNEL_ID);
    }

    @Test
    void shouldReturnActivityName() {
        final PolicyLevel level = DefaultPolicyLevel.builder()
                .activityNames(ACTIVITY_NAMES)
                .build();

        assertThat(level.getActivityNames()).isEqualTo(ACTIVITY_NAMES);
    }

    @Test
    void shouldReturnAliasType() {
        final PolicyLevel level = DefaultPolicyLevel.builder()
                .aliasTypes(ALIAS_TYPES)
                .build();

        assertThat(level.getAliasTypes()).containsExactlyElementsOf(ALIAS_TYPES);
    }

    @Test
    void shouldNotApplyToRequestWithDifferentChannelId() {
        final PolicyLevel level = DefaultPolicyLevel.builder()
                .channelId(CHANNEL_ID)
                .activityNames(ACTIVITY_NAMES)
                .aliasTypes(ALIAS_TYPES)
                .build();

        final LockoutRequest request = DefaultLockoutRequest.builder()
                .channelId("different-channel")
                .activityName(ACTIVITY_NAME)
                .alias(ALIAS)
                .build();

        final boolean applies = level.appliesTo(request);

        assertThat(applies).isFalse();
    }

    @Test
    void shouldNotApplyToRequestWithDifferentActivityName() {
        final PolicyLevel level = DefaultPolicyLevel.builder()
                .channelId(CHANNEL_ID)
                .activityNames(ACTIVITY_NAMES)
                .aliasTypes(ALIAS_TYPES)
                .build();

        final LockoutRequest request = DefaultLockoutRequest.builder()
                .channelId(CHANNEL_ID)
                .activityName("different-activity")
                .alias(ALIAS)
                .build();

        final boolean applies = level.appliesTo(request);

        assertThat(applies).isFalse();
    }

    @Test
    void shouldNotApplyToRequestWithDifferentAliasType() {
        final PolicyLevel level = DefaultPolicyLevel.builder()
                .channelId(CHANNEL_ID)
                .activityNames(ACTIVITY_NAMES)
                .aliasTypes(Collections.singleton("different-alias-type"))
                .build();

        final LockoutRequest request = DefaultLockoutRequest.builder()
                .channelId(CHANNEL_ID)
                .activityName(ACTIVITY_NAME)
                .alias(ALIAS)
                .build();

        final boolean applies = level.appliesTo(request);

        assertThat(applies).isFalse();
    }

    @Test
    void shouldApplyToRequestWithSameChannelIdAndActivityName() {
        final PolicyLevel level = DefaultPolicyLevel.builder()
                .channelId(CHANNEL_ID)
                .activityNames(ACTIVITY_NAMES)
                .aliasTypes(ALIAS_TYPES)
                .build();

        final LockoutRequest request = DefaultLockoutRequest.builder()
                .channelId(CHANNEL_ID)
                .activityName(ACTIVITY_NAME)
                .alias(ALIAS)
                .build();

        final boolean applies = level.appliesTo(request);

        assertThat(applies).isTrue();
    }

    @Test
    void shouldApplyToRequestWithSameChannelIdAndAllActivityNamesAndAliasTypes() {
        final PolicyLevel level = DefaultPolicyLevel.builder()
                .channelId(CHANNEL_ID)
                .activityNames(Collections.singleton(PolicyLevel.ALL))
                .aliasTypes(Collections.singleton(PolicyLevel.ALL))
                .build();

        final LockoutRequest request = DefaultLockoutRequest.builder()
                .channelId(CHANNEL_ID)
                .activityName(ACTIVITY_NAME)
                .alias(ALIAS)
                .build();

        final boolean applies = level.appliesTo(request);

        assertThat(applies).isTrue();
    }

    @Test
    void shouldTestEquals() {
        EqualsVerifier.forClass(DefaultPolicyLevel.class)
                .suppress(Warning.STRICT_INHERITANCE)
                .verify();
    }

}
