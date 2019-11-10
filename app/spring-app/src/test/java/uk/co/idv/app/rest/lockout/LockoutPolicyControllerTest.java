package uk.co.idv.app.rest.lockout;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.lockout.policy.FakeLockoutPolicyParameters;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicyMother;
import uk.co.idv.domain.usecases.lockout.LockoutPolicyParametersConverter;
import uk.co.mruoc.idv.api.lockout.LockoutPoliciesDocument;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicyParameters;
import uk.co.idv.domain.usecases.lockout.FakeLockoutPolicyService;

import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class LockoutPolicyControllerTest {

    private final FakeLockoutPolicyService service = new FakeLockoutPolicyService();
    private final LockoutPolicyParametersConverter parametersConverter = mock(LockoutPolicyParametersConverter.class);

    private final LockoutPolicyController controller = new LockoutPolicyController(service, parametersConverter);

    @Test
    void shouldReturnLockoutPoliciesDocumentWithLockoutPolicies() {
        final LockoutPolicy policy = LockoutPolicyMother.maxAttemptsPolicy();
        final Collection<LockoutPolicy> policies = Collections.singleton(policy);
        service.setPoliciesToLoad(policies);
        final LockoutPolicyParameters parameters = new FakeLockoutPolicyParameters();
        given(parametersConverter.toParameters(policies)).willReturn(Collections.singleton(parameters));

        final LockoutPoliciesDocument document = controller.getLockoutPolicies();

        assertThat(document.getAttributes()).containsExactly(parameters);
    }

}
