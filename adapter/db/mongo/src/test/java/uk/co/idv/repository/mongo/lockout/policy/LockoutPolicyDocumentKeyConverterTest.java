package uk.co.idv.repository.mongo.lockout.policy;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.identity.alias.AliasesMother;
import uk.co.idv.domain.entities.lockout.LockoutRequest;
import uk.co.idv.domain.entities.lockout.LockoutRequestMother;
import uk.co.idv.domain.entities.lockout.policy.LockoutLevel;
import uk.co.idv.domain.entities.lockout.policy.LockoutLevelMother;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

class LockoutPolicyDocumentKeyConverterTest {

    private static final String DEFAULT_KEY = "fake-channel*fake-activity";
    private static final String ALIAS_KEY = "fake-channel*fake-activity*fake-alias-type";

    private final LockoutPolicyDocumentKeyConverter converter = new LockoutPolicyDocumentKeyConverter();

    @Test
    void shouldConvertAliasLockoutLevelToKey() {
        final LockoutLevel level = LockoutLevelMother.aliasLockoutLevel();

        final String key = converter.toKey(level);

        assertThat(key).isEqualTo(ALIAS_KEY);
    }

    @Test
    void shouldConvertDefaultLockoutLevelToKey() {
        final LockoutLevel level = LockoutLevelMother.defaultLockoutLevel();

        final String key = converter.toKey(level);

        assertThat(key).isEqualTo(DEFAULT_KEY);
    }

    @Test
    void shouldConvertLockoutRequestToCollectionOfKeys() {
        final LockoutRequest request = LockoutRequestMother.fakeBuilder()
                .alias(AliasesMother.fake())
                .build();

        final Collection<String> keys = converter.toKeys(request);

        assertThat(keys).containsExactly(
                ALIAS_KEY,
                DEFAULT_KEY
        );
    }

    @Test
    void shouldConvertAliasKeyToAliasLockoutLevel() {
        final LockoutLevel level = converter.toLevel(ALIAS_KEY);

        assertThat(level).isEqualToComparingFieldByField(LockoutLevelMother.aliasLockoutLevel());
    }

    @Test
    void shouldConvertDefaultKeyToDefaultLockoutLevel() {
        final LockoutLevel level = converter.toLevel(DEFAULT_KEY);

        assertThat(level).isEqualToComparingFieldByField(LockoutLevelMother.defaultLockoutLevel());
    }

}
