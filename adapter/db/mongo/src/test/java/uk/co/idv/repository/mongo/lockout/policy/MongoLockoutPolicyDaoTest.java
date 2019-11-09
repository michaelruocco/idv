package uk.co.idv.repository.mongo.lockout.policy;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.identity.AliasesMother;
import uk.co.mruoc.idv.lockout.domain.model.DefaultLockoutPolicyParameters;
import uk.co.mruoc.idv.lockout.domain.model.LockoutPolicy;
import uk.co.mruoc.idv.lockout.domain.service.DefaultLockoutRequest;
import uk.co.mruoc.idv.lockout.domain.service.LockoutPolicyParametersConverter;
import uk.co.mruoc.idv.lockout.domain.service.LockoutRequest;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class MongoLockoutPolicyDaoTest {

    private final LockoutPolicyDocument document = new LockoutPolicyDocument();
    private final LockoutPolicy policy = mock(LockoutPolicy.class);
    private final DefaultLockoutPolicyParameters parameters = mock(DefaultLockoutPolicyParameters.class);

    private final LockoutPolicyRepository repository = mock(LockoutPolicyRepository.class);
    private final LockoutPolicyDocumentConverterDelegator documentConverter = mock(LockoutPolicyDocumentConverterDelegator.class);
    private final LockoutPolicyParametersConverter parametersConverter = mock(LockoutPolicyParametersConverter.class);

    private final MongoLockoutPolicyDao dao = MongoLockoutPolicyDao.builder()
            .repository(repository)
            .documentConverter(documentConverter)
            .parametersConverter(parametersConverter)
            .build();

    @Test
    void shouldSavePolicy() {
        given(policy.getParameters()).willReturn(parameters);
        given(documentConverter.toDocument(parameters)).willReturn(document);

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
        given(documentConverter.toParameters(document)).willReturn(parameters);
        given(parametersConverter.toPolicy(parameters)).willReturn(policy);

        final Optional<LockoutPolicy> loadedPolicy = dao.load(id);

        assertThat(loadedPolicy).contains(policy);
    }

    @Test
    void shouldLoadMultipleDocuments() {
        given(repository.findAll()).willReturn(Collections.singletonList(document));
        given(documentConverter.toParameters(document)).willReturn(parameters);
        given(parametersConverter.toPolicy(parameters)).willReturn(policy);

        final Collection<LockoutPolicy> loadedPolicies = dao.load();

        assertThat(loadedPolicies).containsExactly(policy);
    }

    @Test
    void shouldReturnEmptyOptionalWhenLoadingPolicyByLockoutRequestIfNoPoliciesFound() {
        final LockoutRequest request = DefaultLockoutRequest.builder()
                .channelId("channel-id")
                .activityName("activity-name")
                .alias(AliasesMother.creditCardNumber())
                .build();
        given(repository.findById("channel-id*activity-name*credit-card-number")).willReturn(Optional.empty());

        final Optional<LockoutPolicy> loadedPolicy = dao.load(request);

        assertThat(loadedPolicy).isEmpty();
    }

    @Test
    void shouldReturnPolicyWhenLoadingPolicyByLockoutRequestIfPolicyFound() {
        final LockoutRequest request = DefaultLockoutRequest.builder()
                .channelId("channel-id")
                .activityName("activity-name")
                .alias(AliasesMother.creditCardNumber())
                .build();
        given(repository.findById("channel-id*activity-name*credit-card-number")).willReturn(Optional.of(document));
        given(documentConverter.toParameters(document)).willReturn(parameters);
        given(parametersConverter.toPolicy(parameters)).willReturn(policy);

        final Optional<LockoutPolicy> loadedPolicy = dao.load(request);

        assertThat(loadedPolicy).contains(policy);
    }

}
