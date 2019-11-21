package uk.co.idv.repository.mongo.lockout.policy;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.lockout.LockoutRequestMother;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.LockoutRequest;
import uk.co.idv.domain.usecases.lockout.MultipleLockoutPoliciesHandler;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class MongoLockoutPolicyDaoTest {

    private final LockoutPolicyDocument document = new LockoutPolicyDocument();
    private final LockoutPolicy policy = mock(LockoutPolicy.class);

    private final LockoutPolicyRepository repository = mock(LockoutPolicyRepository.class);
    private final LockoutPolicyDocumentConverterDelegator documentConverter = mock(LockoutPolicyDocumentConverterDelegator.class);
    private final MultipleLockoutPoliciesHandler multiplePoliciesHandler = mock(MultipleLockoutPoliciesHandler.class);

    private final MongoLockoutPolicyDao dao = MongoLockoutPolicyDao.builder()
            .repository(repository)
            .documentConverter(documentConverter)
            .multiplePoliciesHandler(multiplePoliciesHandler)
            .build();

    @Test
    void shouldSavePolicy() {
        given(documentConverter.toDocument(policy)).willReturn(document);

        dao.save(policy);

        verify(repository).save(document);
    }

    @Test
    void shouldReturnEmptyOptionalIfPolicyWithIdDoesNotExist() {
        final UUID id = UUID.randomUUID();
        given(repository.findById(id.toString())).willReturn(Optional.empty());

        final Optional<LockoutPolicy> loadedPolicy = dao.load(id);

        assertThat(loadedPolicy).isEmpty();
    }

    @Test
    void shouldLoadPolicyById() {
        final UUID id = UUID.randomUUID();
        given(repository.findById(id.toString())).willReturn(Optional.of(document));
        given(documentConverter.toPolicy(document)).willReturn(policy);

        final Optional<LockoutPolicy> loadedPolicy = dao.load(id);

        assertThat(loadedPolicy).contains(policy);
    }

    @Test
    void shouldLoadMultipleDocuments() {
        final List<LockoutPolicyDocument> documents = Arrays.asList(document, document);
        given(repository.findAll()).willReturn(documents);
        final List<LockoutPolicy> policies = Arrays.asList(policy, policy);
        given(documentConverter.toPolicies(documents)).willReturn(policies);

        final Collection<LockoutPolicy> loadedPolicies = dao.load();

        assertThat(loadedPolicies).containsExactlyElementsOf(policies);
    }

    @Test
    void shouldReturnEmptyOptionalWhenLoadingPolicyByLockoutRequestIfNoPoliciesFound() {
        final LockoutRequest request = LockoutRequestMother.fakeBuilder().build();
        given(repository.findByChannelId(request.getChannelId())).willReturn(Collections.emptyList());
        given(documentConverter.toPolicies(Collections.emptyList())).willReturn(Collections.emptyList());
        given(multiplePoliciesHandler.extractPolicy(Collections.emptyList())).willReturn(Optional.empty());

        final Optional<LockoutPolicy> loadedPolicy = dao.load(request);

        assertThat(loadedPolicy).isEmpty();
    }

    @Test
    void shouldReturnPolicyFromMultiplePolicyHandlerIfIfMultipleApplicablePoliciesFound() {
        final LockoutRequest request = LockoutRequestMother.fakeBuilder().build();
        final Collection<LockoutPolicyDocument> documents = Arrays.asList(document, document);
        given(repository.findByChannelId(request.getChannelId())).willReturn(documents);
        final List<LockoutPolicy> policies = Arrays.asList(policy, policy);
        given(documentConverter.toPolicies(documents)).willReturn(policies);
        given(policy.appliesTo(request)).willReturn(true);
        given(multiplePoliciesHandler.extractPolicy(policies)).willReturn(Optional.of(policy));

        final Optional<LockoutPolicy> loadedPolicy = dao.load(request);

        assertThat(loadedPolicy).contains(policy);
    }

    @Test
    void shouldReturnEmptyOptionalWhenLoadingPolicyByLockoutRequestIfOnePolicyFoundAndDoesNotApplyToRequest() {
        final LockoutRequest request = LockoutRequestMother.fakeBuilder().build();
        final Collection<LockoutPolicyDocument> documents = Collections.singleton(document);
        given(repository.findByChannelId(request.getChannelId())).willReturn(documents);
        given(documentConverter.toPolicies(documents)).willReturn(Collections.singleton(policy));
        given(policy.appliesTo(request)).willReturn(false);
        given(multiplePoliciesHandler.extractPolicy(Collections.emptyList())).willReturn(Optional.empty());

        final Optional<LockoutPolicy> loadedPolicy = dao.load(request);

        assertThat(loadedPolicy).isEmpty();
    }

    @Test
    void shouldReturnPolicyWhenLoadingPolicyByLockoutRequestIfOnePolicyFoundAndAppliesToRequest() {
        final LockoutRequest request = LockoutRequestMother.fakeBuilder().build();
        final Collection<LockoutPolicyDocument> documents = Collections.singleton(document);
        given(repository.findByChannelId(request.getChannelId())).willReturn(documents);
        final List<LockoutPolicy> policies = Collections.singletonList(policy);
        given(documentConverter.toPolicies(documents)).willReturn(policies);
        given(policy.appliesTo(request)).willReturn(true);
        given(multiplePoliciesHandler.extractPolicy(policies)).willReturn(Optional.of(policy));

        final Optional<LockoutPolicy> loadedPolicy = dao.load(request);

        assertThat(loadedPolicy).contains(policy);
    }

}
