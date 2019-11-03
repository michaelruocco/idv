package uk.co.mruoc.idv.lockout.domain.model;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.lockout.domain.service.LockoutPolicyParametersMother;
import uk.co.mruoc.idv.lockout.domain.service.LockoutRequest;
import uk.co.mruoc.idv.lockout.jsonapi.LockoutRequestMother;

import java.util.Collections;
import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.assertThat;

class LockoutRequestPredicateFactoryTest {

    private final LockoutRequestPredicateFactory factory = new LockoutRequestPredicateFactory();

    @Test
    void shouldBuildPredicateThatReturnsTrueIfChannelIdActivityNamesAndAliasTypesMatch() {
        final LockoutPolicyParameters parameters = LockoutPolicyParametersMother.fake();

        final Predicate<LockoutRequest> predicate = factory.build(parameters);

        final LockoutRequest request = LockoutRequestMother.fake();
        assertThat(predicate.test(request)).isTrue();
    }

    @Test
    void shouldBuildPredicateThatReturnsFalseIfChannelIdsDoNotMatch() {
        final LockoutPolicyParameters parameters = LockoutPolicyParametersMother.fakeBuilder()
                .channelIds(Collections.singleton("not-matching"))
                .build();

        final Predicate<LockoutRequest> predicate = factory.build(parameters);

        final LockoutRequest request = LockoutRequestMother.fake();
        assertThat(predicate.test(request)).isFalse();
    }

    @Test
    void shouldBuildPredicateThatReturnsFalseIfActivityNamesDoNotMatch() {
        final LockoutPolicyParameters parameters = LockoutPolicyParametersMother.fakeBuilder()
                .activityNames(Collections.singleton("not-matching"))
                .build();

        final Predicate<LockoutRequest> predicate = factory.build(parameters);

        final LockoutRequest request = LockoutRequestMother.fake();
        assertThat(predicate.test(request)).isFalse();
    }

    @Test
    void shouldBuildPredicateThatReturnsFalseIfAliasTypesDoNotMatch() {
        final LockoutPolicyParameters parameters = LockoutPolicyParametersMother.fakeBuilder()
                .aliasTypes(Collections.singleton("not-matching"))
                .build();

        final Predicate<LockoutRequest> predicate = factory.build(parameters);

        final LockoutRequest request = LockoutRequestMother.fake();
        assertThat(predicate.test(request)).isFalse();
    }

}