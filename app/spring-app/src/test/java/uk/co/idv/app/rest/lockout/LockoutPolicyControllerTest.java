package uk.co.idv.app.rest.lockout;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicyMother;
import uk.co.idv.api.lockout.policy.FakeLockoutPolicyAttributes;
import uk.co.idv.api.lockout.policy.LockoutPoliciesDocument;
import uk.co.idv.api.lockout.policy.LockoutPolicyAttributes;
import uk.co.idv.domain.usecases.lockout.FakeLockoutPolicyService;
import uk.co.idv.api.lockout.policy.LockoutPolicyAttributesConverterDelegator;

import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class LockoutPolicyControllerTest {

    private final FakeLockoutPolicyService service = new FakeLockoutPolicyService();
    private final LockoutPolicyAttributesConverterDelegator parametersConverter = mock(LockoutPolicyAttributesConverterDelegator.class);

    private final LockoutPolicyController controller = new LockoutPolicyController(service, parametersConverter);

    @Test
    void shouldReturnLockoutPoliciesDocumentWithLockoutPolicies() {
        final LockoutPolicy policy = LockoutPolicyMother.hardLockoutPolicy();
        final Collection<LockoutPolicy> policies = Collections.singleton(policy);
        service.setPoliciesToLoad(policies);
        final LockoutPolicyAttributes parameters = new FakeLockoutPolicyAttributes();
        given(parametersConverter.toAttributes(policies)).willReturn(Collections.singleton(parameters));

        final LockoutPoliciesDocument document = controller.getLockoutPolicies();

        assertThat(document.getAttributes()).containsExactly(parameters);
    }

}
