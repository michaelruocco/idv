package uk.co.idv.domain.usecases.policy;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.policy.Policy;
import uk.co.idv.domain.entities.policy.PolicyProvider;
import uk.co.idv.domain.usecases.policy.PolicyService.PoliciesAlreadyExistException;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class DefaultPolicyCreatorTest {

    private final PolicyProvider<Policy> provider = mock(PolicyProvider.class);
    private final PolicyService<Policy> service = mock(PolicyService.class);

    private final DefaultPolicyCreator<Policy> creator = new DefaultPolicyCreator<>(provider, service);

    @Test
    void shouldReturnAllCreatedTrueIfNoExceptionsThrown() {
        final Policy policy1 = mock(Policy.class);
        final Policy policy2 = mock(Policy.class);
        given(provider.getPolicies()).willReturn(Arrays.asList(policy1, policy2));

        boolean allCreated = creator.create();

        assertThat(allCreated).isTrue();
    }

    @Test
    void shouldCreateAllProvidedPolicies() {
        final Policy policy1 = mock(Policy.class);
        final Policy policy2 = mock(Policy.class);
        given(provider.getPolicies()).willReturn(Arrays.asList(policy1, policy2));

        creator.create();

        verify(service).create(policy1);
        verify(service).create(policy2);
    }

    @Test
    void shouldReturnFalseIfAnyPoliciesAlreadyExist() {
        final Policy policy = mock(Policy.class);
        given(provider.getPolicies()).willReturn(Collections.singleton(policy));
        doThrow(PoliciesAlreadyExistException.class).when(service).create(policy);

        boolean allCreated = creator.create();

        assertThat(allCreated).isFalse();
    }

}
