package uk.co.idv.app.rest.lockout;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import uk.co.idv.api.lockout.policy.LockoutPolicyDocument;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicyMother;
import uk.co.idv.api.lockout.policy.LockoutPoliciesDocument;
import uk.co.idv.domain.usecases.lockout.policy.FakeLockoutPolicyService;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class LockoutPolicyControllerTest {

    private final FakeLockoutPolicyService service = new FakeLockoutPolicyService();

    private final LockoutPolicyController controller = new LockoutPolicyController(service);

    @Test
    void shouldReturnLockoutPoliciesDocumentWithLockoutPolicies() {
        final LockoutPolicy policy = LockoutPolicyMother.hardLockoutPolicy();
        final Collection<LockoutPolicy> policies = Collections.singleton(policy);
        service.setPoliciesToLoad(policies);

        final LockoutPoliciesDocument document = controller.getLockoutPolicies();

        assertThat(document.getAttributes()).containsExactly(policy);
    }

    @Test
    void shouldLoadLockoutPolicyById() {
        final LockoutPolicy policy = LockoutPolicyMother.hardLockoutPolicy();
        service.setPolicyToLoad(policy);
        final UUID id = UUID.randomUUID();

        controller.getLockoutPolicy(id);

        assertThat(service.getLastLoadedId()).isEqualTo(id);
    }

    @Test
    void shouldReturnSpecificLockoutPolicyById() {
        final LockoutPolicy policy = LockoutPolicyMother.hardLockoutPolicy();
        service.setPolicyToLoad(policy);
        final UUID id = UUID.randomUUID();

        final LockoutPolicyDocument document = controller.getLockoutPolicy(id);

        assertThat(document.getAttributes()).isEqualTo(policy);
    }

    @Test
    void shouldCreateLockoutPolicy() {
        final LockoutPolicy policy = LockoutPolicyMother.hardLockoutPolicy();
        final LockoutPolicyDocument document = new LockoutPolicyDocument(policy);

        controller.createLockoutPolicy(document);

        assertThat(service.getLastCreatedPolicy()).isEqualTo(policy);
    }

    @Test
    void shouldReturnCreatedStatus() {
        final LockoutPolicy policy = LockoutPolicyMother.hardLockoutPolicy();
        final LockoutPolicyDocument document = new LockoutPolicyDocument(policy);

        final ResponseEntity<LockoutPolicyDocument> response = controller.createLockoutPolicy(document);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    void shouldReturnCreatedDocument() {
        final LockoutPolicy policy = LockoutPolicyMother.hardLockoutPolicy();
        final LockoutPolicyDocument document = new LockoutPolicyDocument(policy);

        final ResponseEntity<LockoutPolicyDocument> response = controller.createLockoutPolicy(document);

        assertThat(response.getBody()).isEqualTo(document);
    }

    @Test
    void shouldReturnLocationHeader() {
        final LockoutPolicy policy = LockoutPolicyMother.hardLockoutPolicy();
        final LockoutPolicyDocument document = new LockoutPolicyDocument(policy);

        final ResponseEntity<LockoutPolicyDocument> response = controller.createLockoutPolicy(document);

        final HttpHeaders headers = response.getHeaders();
        final String expectedUri = String.format("/lockout-policies/%s", policy.getId());
        assertThat(headers.get("Location")).containsExactly(expectedUri);
    }

    @Test
    void shouldUpdateLockoutPolicy() {
        final LockoutPolicy policy = LockoutPolicyMother.hardLockoutPolicy();
        final LockoutPolicyDocument document = new LockoutPolicyDocument(policy);

        controller.updateLockoutPolicy(document);

        assertThat(service.getLastUpdatedPolicy()).isEqualTo(policy);
    }

    @Test
    void shouldReturnUpdatedDocument() {
        final LockoutPolicy policy = LockoutPolicyMother.hardLockoutPolicy();
        final LockoutPolicyDocument document = new LockoutPolicyDocument(policy);

        final LockoutPolicyDocument updatedDocument = controller.updateLockoutPolicy(document);

        assertThat(updatedDocument).isEqualTo(document);
    }

}
