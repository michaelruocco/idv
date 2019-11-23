package uk.co.idv.app.rest.lockout;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.api.lockout.LockoutPoliciesDocument;
import uk.co.idv.json.lockout.LockoutPolicyDto;
import uk.co.idv.domain.usecases.lockout.LockoutPolicyService;
import uk.co.idv.json.lockout.LockoutPolicyParametersDtoDelegator;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
public class LockoutPolicyController {

    private final LockoutPolicyService service;
    private final LockoutPolicyParametersDtoDelegator parametersConverter;

    @GetMapping("/lockoutPolicies")
    public LockoutPoliciesDocument getLockoutPolicies() {
        final Collection<LockoutPolicy> policies = service.loadPolicies();
        return toDocument(parametersConverter.toParameters(policies));
    }

    private static LockoutPoliciesDocument toDocument(final Collection<LockoutPolicyDto> policies) {
        return new LockoutPoliciesDocument(policies);
    }

}
