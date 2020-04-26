package uk.co.idv.domain.usecases.lockout.policy;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicyMother;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicyProvider;
import uk.co.idv.domain.usecases.lockout.policy.LockoutPolicyService.LockoutPoliciesAlreadyExistException;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class InitialLockoutPolicyCreatorTest {

    private final LockoutPolicyProvider provider = mock(LockoutPolicyProvider.class);
    private final LockoutPolicyService service = mock(LockoutPolicyService.class);

    private final InitialLockoutPolicyCreator creator = new InitialLockoutPolicyCreator(provider, service);

    @Test
    void shouldReturnAllCreatedTrueIfNoExceptionsThrown() {
        final LockoutPolicy policy1 = LockoutPolicyMother.hardLockoutPolicy();
        final LockoutPolicy policy2 = LockoutPolicyMother.softLockoutPolicy();
        given(provider.getPolicies()).willReturn(Arrays.asList(policy1, policy2));

        boolean allCreated = creator.create();

        assertThat(allCreated).isTrue();
    }

    @Test
    void shouldCreateAllProvidedPolicies() {
        final LockoutPolicy policy1 = LockoutPolicyMother.hardLockoutPolicy();
        final LockoutPolicy policy2 = LockoutPolicyMother.softLockoutPolicy();
        given(provider.getPolicies()).willReturn(Arrays.asList(policy1, policy2));

        creator.create();

        verify(service).create(policy1);
        verify(service).create(policy2);
    }

    @Test
    void shouldReturnFalseIfAnyPoliciesAlreadyExist() {
        final LockoutPolicy policy = LockoutPolicyMother.hardLockoutPolicy();
        given(provider.getPolicies()).willReturn(Collections.singleton(policy));
        doThrow(LockoutPoliciesAlreadyExistException.class).when(service).create(policy);

        boolean allCreated = creator.create();

        assertThat(allCreated).isFalse();
    }

}
