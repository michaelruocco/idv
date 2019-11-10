package uk.co.idv.repository.mongo.lockout.policy;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.lockout.DefaultLockoutRequest;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.LockoutRequest;

import java.util.Arrays;
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

    private final LockoutPolicyRepository repository = mock(LockoutPolicyRepository.class);
    private final LockoutPolicyDocumentConverterDelegator documentConverter = mock(LockoutPolicyDocumentConverterDelegator.class);
    private final LockoutPolicyDocumentKeyConverter keyConverter = mock(LockoutPolicyDocumentKeyConverter.class);

    private final MongoLockoutPolicyDao dao = MongoLockoutPolicyDao.builder()
            .repository(repository)
            .documentConverter(documentConverter)
            .keyConverter(keyConverter)
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
        given(repository.findAll()).willReturn(Collections.singletonList(document));
        given(documentConverter.toPolicy(document)).willReturn(policy);

        final Collection<LockoutPolicy> loadedPolicies = dao.load();

        assertThat(loadedPolicies).containsExactly(policy);
    }

    @Test
    void shouldReturnEmptyOptionalWhenLoadingPolicyByLockoutRequestIfNoPoliciesFound() {
        final LockoutRequest request = DefaultLockoutRequest.builder().build();
        final String key = "fake-key";
        given(keyConverter.toKeys(request)).willReturn(Collections.singleton(key));
        given(repository.findById(key)).willReturn(Optional.empty());

        final Optional<LockoutPolicy> loadedPolicy = dao.load(request);

        assertThat(loadedPolicy).isEmpty();
    }

    @Test
    void shouldReturnPolicyWhenLoadingPolicyByLockoutRequestIfPolicyFound() {
        final LockoutRequest request = DefaultLockoutRequest.builder().build();
        final String key = "fake-key";
        given(keyConverter.toKeys(request)).willReturn(Arrays.asList("other-key", key));
        given(repository.findById(key)).willReturn(Optional.of(document));
        given(documentConverter.toPolicy(document)).willReturn(policy);

        final Optional<LockoutPolicy> loadedPolicy = dao.load(request);

        assertThat(loadedPolicy).contains(policy);
    }

}
