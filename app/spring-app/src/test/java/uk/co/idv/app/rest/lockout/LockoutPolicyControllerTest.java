package uk.co.idv.app.rest.lockout;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import uk.co.idv.api.lockout.policy.LockoutPolicyAttributesMother;
import uk.co.idv.api.lockout.policy.LockoutPolicyDocument;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicyMother;
import uk.co.idv.api.lockout.policy.FakeLockoutPolicyAttributes;
import uk.co.idv.api.lockout.policy.LockoutPoliciesDocument;
import uk.co.idv.api.lockout.policy.LockoutPolicyAttributes;
import uk.co.idv.domain.usecases.lockout.FakeLockoutPolicyService;
import uk.co.idv.api.lockout.policy.LockoutPolicyAttributesConverterDelegator;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class LockoutPolicyControllerTest {

    private final FakeLockoutPolicyService service = new FakeLockoutPolicyService();
    private final LockoutPolicyAttributesConverterDelegator attributesConverter = mock(LockoutPolicyAttributesConverterDelegator.class);

    private final LockoutPolicyController controller = new LockoutPolicyController(service, attributesConverter);

    @Test
    void shouldReturnLockoutPoliciesDocumentWithLockoutPolicies() {
        final LockoutPolicy policy = LockoutPolicyMother.hardLockoutPolicy();
        final Collection<LockoutPolicy> policies = Collections.singleton(policy);
        service.setPoliciesToLoad(policies);
        final LockoutPolicyAttributes attributes = new FakeLockoutPolicyAttributes();
        given(attributesConverter.toAttributes(policies)).willReturn(Collections.singleton(attributes));

        final LockoutPoliciesDocument document = controller.getLockoutPolicies();

        assertThat(document.getAttributes()).containsExactly(attributes);
    }

    @Test
    void shouldLoadLockoutPolicyById() {
        final LockoutPolicy policy = LockoutPolicyMother.hardLockoutPolicy();
        service.setPolicyToLoad(policy);
        final LockoutPolicyAttributes attributes = new FakeLockoutPolicyAttributes();
        given(attributesConverter.toAttributes(policy)).willReturn(attributes);
        final UUID id = UUID.randomUUID();

        controller.getLockoutPolicy(id);

        assertThat(service.getLastLoadedId()).isEqualTo(id);
    }

    @Test
    void shouldReturnSpecificLockoutPolicyById() {
        final LockoutPolicy policy = LockoutPolicyMother.hardLockoutPolicy();
        service.setPolicyToLoad(policy);
        final LockoutPolicyAttributes attributes = new FakeLockoutPolicyAttributes();
        given(attributesConverter.toAttributes(policy)).willReturn(attributes);
        final UUID id = UUID.randomUUID();

        final LockoutPolicyDocument document = controller.getLockoutPolicy(id);

        assertThat(document.getAttributes()).isEqualTo(attributes);
    }

    @Test
    void shouldCreateLockoutPolicy() {
        final LockoutPolicyAttributes attributes = LockoutPolicyAttributesMother.hardLock();
        final LockoutPolicyDocument document = new LockoutPolicyDocument(attributes);
        final LockoutPolicy policy = LockoutPolicyMother.hardLockoutPolicy();
        given(attributesConverter.toPolicy(attributes)).willReturn(policy);

        controller.createLockoutPolicy(document);

        assertThat(service.getLastCreatedPolicy()).isEqualTo(policy);
    }

    @Test
    void shouldReturnCreatedStatus() {
        final LockoutPolicyAttributes attributes = LockoutPolicyAttributesMother.hardLock();
        final LockoutPolicyDocument document = new LockoutPolicyDocument(attributes);
        given(attributesConverter.toPolicy(attributes)).willReturn(LockoutPolicyMother.hardLockoutPolicy());

        final ResponseEntity<LockoutPolicyDocument> response = controller.createLockoutPolicy(document);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    void shouldReturnCreatedDocument() {
        final LockoutPolicyAttributes attributes = LockoutPolicyAttributesMother.hardLock();
        final LockoutPolicyDocument document = new LockoutPolicyDocument(attributes);
        given(attributesConverter.toPolicy(attributes)).willReturn(LockoutPolicyMother.hardLockoutPolicy());

        final ResponseEntity<LockoutPolicyDocument> response = controller.createLockoutPolicy(document);

        assertThat(response.getBody()).isEqualTo(document);
    }

    @Test
    void shouldReturnLocationHeader() {
        final LockoutPolicyAttributes attributes = LockoutPolicyAttributesMother.hardLock();
        final LockoutPolicyDocument document = new LockoutPolicyDocument(attributes);
        final LockoutPolicy policy = LockoutPolicyMother.hardLockoutPolicy();
        given(attributesConverter.toPolicy(attributes)).willReturn(policy);

        final ResponseEntity<LockoutPolicyDocument> response = controller.createLockoutPolicy(document);

        final HttpHeaders headers = response.getHeaders();
        final String expectedUri = String.format("/lockoutPolicies/%s", policy.getId());
        assertThat(headers.get("Location")).containsExactly(expectedUri);
    }

    @Test
    void shouldUpdateLockoutPolicy() {
        final LockoutPolicyAttributes attributes = LockoutPolicyAttributesMother.hardLock();
        final LockoutPolicyDocument document = new LockoutPolicyDocument(attributes);
        final LockoutPolicy policy = LockoutPolicyMother.hardLockoutPolicy();
        given(attributesConverter.toPolicy(attributes)).willReturn(policy);

        controller.updateLockoutPolicy(document);

        assertThat(service.getLastUpdatedPolicy()).isEqualTo(policy);
    }

    @Test
    void shouldReturnUpdatedDocument() {
        final LockoutPolicyAttributes attributes = LockoutPolicyAttributesMother.hardLock();
        final LockoutPolicyDocument document = new LockoutPolicyDocument(attributes);

        final LockoutPolicyDocument updatedDocument = controller.updateLockoutPolicy(document);

        assertThat(updatedDocument).isEqualTo(document);
    }

}
