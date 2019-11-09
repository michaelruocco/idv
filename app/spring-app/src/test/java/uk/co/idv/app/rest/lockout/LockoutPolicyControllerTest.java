package uk.co.idv.app.rest.lockout;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.api.lockout.LockoutPoliciesDocument;
import uk.co.idv.domain.entities.lockout.LockoutPolicyParameters;
import uk.co.idv.domain.usecases.lockout.FakeLockoutPolicyService;
import uk.co.idv.domain.usecases.lockout.LockoutPolicyParametersMother;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

class LockoutPolicyControllerTest {

    private final FakeLockoutPolicyService service = new FakeLockoutPolicyService();

    private final LockoutPolicyController controller = new LockoutPolicyController(service);

    @Test
    void shouldReturnLockoutPoliciesDocumentWithLockoutPolicies() {
        final LockoutPolicyParameters parameters = LockoutPolicyParametersMother.maxAttempts();
        service.setPoliciesToLoad(Collections.singleton(parameters));
        final LockoutPoliciesDocument document = controller.getLockoutPolicies();

        assertThat(document.getAttributes()).containsExactly(parameters);
    }

}
