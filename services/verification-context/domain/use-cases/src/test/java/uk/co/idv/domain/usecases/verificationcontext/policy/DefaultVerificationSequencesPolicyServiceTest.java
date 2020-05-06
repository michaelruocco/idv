package uk.co.idv.domain.usecases.verificationcontext.policy;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.policy.PolicyLevel;
import uk.co.idv.domain.entities.policy.PolicyLevelConverter;
import uk.co.idv.domain.entities.policy.PolicyRequest;
import uk.co.idv.domain.entities.verificationcontext.sequence.policy.VerificationSequencesPolicy;
import uk.co.idv.domain.usecases.verificationcontext.policy.VerificationSequencePolicyService.RequestedVerificationSequencesPolicyNotFoundException;
import uk.co.idv.domain.usecases.verificationcontext.policy.VerificationSequencePolicyService.VerificationSequencesPolicyAlreadyExistsException;
import uk.co.idv.domain.usecases.verificationcontext.policy.VerificationSequencePolicyService.VerificationSequencesPolicyNotFoundException;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class DefaultVerificationSequencesPolicyServiceTest {

    private final VerificationSequencesPolicy policy = mock(VerificationSequencesPolicy.class);
    private final PolicyLevelConverter policyLevelConverter = mock(PolicyLevelConverter.class);
    private final MultipleVerificationSequencesPoliciesHandler multiplePoliciesHandler = mock(MultipleVerificationSequencesPoliciesHandler.class);

    private final VerificationSequencePolicyDao dao = mock(VerificationSequencePolicyDao.class);

    private final VerificationSequencePolicyService service = new DefaultVerificationSequencesPolicyService(dao, policyLevelConverter, multiplePoliciesHandler);

    @Test
    void shouldCreatePolicyIfNoPoliciesAlreadyExistForSameLevel() {
        final PolicyRequest request = mock(PolicyRequest.class);
        final PolicyLevel level = mock(PolicyLevel.class);
        given(policy.getLevel()).willReturn(level);
        given(policyLevelConverter.toPolicyRequests(level)).willReturn(Collections.singleton(request));
        given(dao.load(request)).willReturn(Collections.emptyList());

        service.create(policy);

        verify(dao).save(policy);
    }

    @Test
    void shouldThrowExceptionIfPolicyAlreadyExistsForSameLevel() {
        final PolicyRequest request = mock(PolicyRequest.class);
        final PolicyLevel level = mock(PolicyLevel.class);
        final UUID id = UUID.randomUUID();
        given(policy.getId()).willReturn(id);
        given(policy.getLevel()).willReturn(level);
        given(policy.appliesTo(request)).willReturn(true);
        given(policyLevelConverter.toPolicyRequests(level)).willReturn(Collections.singleton(request));
        given(dao.load(request)).willReturn(Collections.singleton(policy));

        final Throwable error = catchThrowable(() -> service.create(policy));

        assertThat(error)
                .isInstanceOf(VerificationSequencesPolicyAlreadyExistsException.class)
                .hasMessage(String.format("verification sequences policy %s already exists for same lockout level", id.toString()));
    }

    @Test
    void shouldCreateMultiplePolicies() {
        final VerificationSequencesPolicy policy1 = mock(VerificationSequencesPolicy.class);
        final PolicyLevel level = mock(PolicyLevel.class);
        final PolicyRequest request = mock(PolicyRequest.class);
        given(policy.getLevel()).willReturn(level);
        given(policy1.getLevel()).willReturn(level);
        given(policyLevelConverter.toPolicyRequests(level)).willReturn(Collections.singleton(request));
        given(dao.load(request)).willReturn(Collections.emptyList());

        service.create(Arrays.asList(policy, policy1));

        verify(dao).save(policy);
        verify(dao).save(policy1);
    }

    @Test
    void shouldThrowExceptionIfPolicyDoesNotAlreadyExist() {
        final UUID id = UUID.randomUUID();
        given(policy.getId()).willReturn(id);
        given(dao.load(id)).willReturn(Optional.empty());

        final Throwable error = catchThrowable(() -> service.update(policy));

        assertThat(error)
                .isInstanceOf(VerificationSequencesPolicyNotFoundException.class)
                .hasMessage(id.toString());
    }

    @Test
    void shouldUpdatePolicy() {
        final UUID id = UUID.randomUUID();
        given(policy.getId()).willReturn(id);
        given(dao.load(id)).willReturn(Optional.of(policy));

        service.update(policy);

        verify(dao).save(policy);
    }

    @Test
    void shouldReturnAllPolicies() {
        final Collection<VerificationSequencesPolicy> expectedPolicies = Collections.singleton(policy);
        given(dao.load()).willReturn(expectedPolicies);

        final Collection<VerificationSequencesPolicy> policies = service.loadAll();

        assertThat(policies).isEqualTo(expectedPolicies);
    }

    @Test
    void shouldThrowExceptionIfPolicyNotFoundById() {
        final UUID id = UUID.randomUUID();
        given(dao.load(id)).willReturn(Optional.empty());

        final Throwable error = catchThrowable(() -> service.load(id));

        assertThat(error)
                .isInstanceOf(VerificationSequencesPolicyNotFoundException.class)
                .hasMessage(id.toString());
    }

    @Test
    void shouldReturnPolicyById() {
        final UUID id = UUID.randomUUID();
        given(dao.load(id)).willReturn(Optional.of(policy));

        final VerificationSequencesPolicy loadedPolicy = service.load(id);

        assertThat(loadedPolicy).isEqualTo(policy);
    }

    @Test
    void shouldLoadPolicyApplicableToRequest() {
        final PolicyRequest request = mock(PolicyRequest.class);
        final PolicyLevel level = mock(PolicyLevel.class);
        final UUID id = UUID.randomUUID();
        given(policy.getId()).willReturn(id);
        given(policy.appliesTo(request)).willReturn(true);
        given(policyLevelConverter.toPolicyRequests(level)).willReturn(Collections.singleton(request));
        final List<VerificationSequencesPolicy> policies = Collections.singletonList(policy);
        given(multiplePoliciesHandler.extractPolicy(policies)).willReturn(Optional.of(policy));
        given(dao.load(request)).willReturn(policies);

        final VerificationSequencesPolicy loadedPolicy = service.load(request);

        assertThat(loadedPolicy).isEqualTo(policy);
    }

    @Test
    void shouldThrowExceptionIfApplicablePolicyNotFound() {
        final PolicyRequest request = mock(PolicyRequest.class);
        final PolicyLevel level = mock(PolicyLevel.class);
        final UUID id = UUID.randomUUID();
        given(policy.getId()).willReturn(id);
        given(policy.appliesTo(request)).willReturn(true);
        given(policyLevelConverter.toPolicyRequests(level)).willReturn(Collections.singleton(request));
        final List<VerificationSequencesPolicy> policies = Collections.singletonList(policy);
        given(multiplePoliciesHandler.extractPolicy(policies)).willReturn(Optional.empty());
        given(dao.load(request)).willReturn(policies);

        final Throwable error = catchThrowable(() -> service.load(request));

        assertThat(error)
                .isInstanceOf(RequestedVerificationSequencesPolicyNotFoundException.class)
                .hasMessage(toExpectedMessage(request));
    }

    private static String toExpectedMessage(final PolicyRequest request) {
        return String.format("channelId %s activity %s aliasType %s",
                request.getChannelId(),
                request.getActivityName(),
                request.getAliasType());
    }

}
