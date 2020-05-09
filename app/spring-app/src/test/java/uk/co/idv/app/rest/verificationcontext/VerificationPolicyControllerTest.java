package uk.co.idv.app.rest.verificationcontext;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import uk.co.idv.api.verificationcontext.policy.VerificationPoliciesDocument;
import uk.co.idv.api.verificationcontext.policy.VerificationPolicyDocument;
import uk.co.idv.domain.entities.verificationcontext.policy.VerificationPolicy;
import uk.co.idv.domain.entities.verificationcontext.policy.VerificationPolicyMother;
import uk.co.idv.domain.usecases.verificationcontext.policy.FakeVerificationPolicyService;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class VerificationPolicyControllerTest {

    private final FakeVerificationPolicyService service = new FakeVerificationPolicyService();

    private final VerificationPolicyController controller = new VerificationPolicyController(service);

    @Test
    void shouldReturnPoliciesDocumentWithVerificationPolicies() {
        final VerificationPolicy policy = VerificationPolicyMother.build();
        final Collection<VerificationPolicy> policies = Collections.singleton(policy);
        service.setPoliciesToLoad(policies);

        final VerificationPoliciesDocument document = controller.getPolicies();

        assertThat(document.getAttributes()).containsExactly(policy);
    }

    @Test
    void shouldLoadPolicyById() {
        final VerificationPolicy policy = VerificationPolicyMother.build();
        service.setPolicyToLoad(policy);
        final UUID id = UUID.randomUUID();

        controller.getPolicy(id);

        assertThat(service.getLastLoadedId()).isEqualTo(id);
    }

    @Test
    void shouldReturnSpecificPolicyById() {
        final VerificationPolicy policy = VerificationPolicyMother.build();
        service.setPolicyToLoad(policy);
        final UUID id = UUID.randomUUID();

        final VerificationPolicyDocument document = controller.getPolicy(id);

        assertThat(document.getAttributes()).isEqualTo(policy);
    }

    @Test
    void shouldCreatePolicy() {
        final VerificationPolicy policy = VerificationPolicyMother.build();
        final VerificationPolicyDocument document = new VerificationPolicyDocument(policy);

        controller.createPolicy(document);

        assertThat(service.getLastCreatedPolicy()).isEqualTo(policy);
    }

    @Test
    void shouldReturnCreatedStatus() {
        final VerificationPolicy policy = VerificationPolicyMother.build();
        final VerificationPolicyDocument document = new VerificationPolicyDocument(policy);

        final ResponseEntity<VerificationPolicyDocument> response = controller.createPolicy(document);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    void shouldReturnCreatedDocument() {
        final VerificationPolicy policy = VerificationPolicyMother.build();
        final VerificationPolicyDocument document = new VerificationPolicyDocument(policy);

        final ResponseEntity<VerificationPolicyDocument> response = controller.createPolicy(document);

        assertThat(response.getBody()).isEqualTo(document);
    }

    @Test
    void shouldReturnLocationHeader() {
        final VerificationPolicy policy = VerificationPolicyMother.build();
        final VerificationPolicyDocument document = new VerificationPolicyDocument(policy);

        final ResponseEntity<VerificationPolicyDocument> response = controller.createPolicy(document);

        final HttpHeaders headers = response.getHeaders();
        final String expectedUri = String.format("/verification-policies/%s", policy.getId());
        assertThat(headers.get("Location")).containsExactly(expectedUri);
    }

    @Test
    void shouldUpdatePolicy() {
        final VerificationPolicy policy = VerificationPolicyMother.build();
        final VerificationPolicyDocument document = new VerificationPolicyDocument(policy);

        controller.updatePolicy(document);

        assertThat(service.getLastUpdatedPolicy()).isEqualTo(policy);
    }

    @Test
    void shouldReturnUpdatedDocument() {
        final VerificationPolicy policy = VerificationPolicyMother.build();
        final VerificationPolicyDocument document = new VerificationPolicyDocument(policy);

        final VerificationPolicyDocument updatedDocument = controller.updatePolicy(document);

        assertThat(updatedDocument).isEqualTo(document);
    }

}
