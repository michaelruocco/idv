package uk.co.idv.app.rest.lockout;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.api.lockout.policy.LockoutPoliciesDocument;
import uk.co.idv.api.lockout.policy.LockoutPolicyAttributes;
import uk.co.idv.domain.usecases.lockout.LockoutPolicyService;
import uk.co.idv.api.lockout.policy.LockoutPolicyAttributesConverterDelegator;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
public class LockoutPolicyController {

    private final LockoutPolicyService service;
    private final LockoutPolicyAttributesConverterDelegator parametersConverter;

    @GetMapping("/lockoutPolicies")
    public LockoutPoliciesDocument getLockoutPolicies() {
        final Collection<LockoutPolicy> policies = service.loadPolicies();
        return toDocument(parametersConverter.toAttributes(policies));
    }

    private static LockoutPoliciesDocument toDocument(final Collection<LockoutPolicyAttributes> policies) {
        return new LockoutPoliciesDocument(policies);
    }

}
