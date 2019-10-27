package uk.co.mruoc.idv.lockout.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.co.mruoc.idv.lockout.domain.model.LockoutPolicy;
import uk.co.mruoc.idv.lockout.domain.model.LockoutPolicyParameters;
import uk.co.mruoc.idv.lockout.domain.service.LockoutPolicyService;
import uk.co.mruoc.idv.lockout.jsonapi.LockoutPoliciesDocument;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class LockoutPolicyController {

    private final LockoutPolicyService service;

    @GetMapping("/lockoutPolicies")
    public LockoutPoliciesDocument getLockoutPolicies() {
        final Collection<LockoutPolicy> policies = service.loadPolicies();
        return toDocument(policies);
    }

    private static LockoutPoliciesDocument toDocument(final Collection<LockoutPolicy> policies) {
        final Collection<LockoutPolicyParameters> parametersCollection = policies.stream()
                .map(LockoutPolicy::getParameters)
                .collect(Collectors.toList());
        return new LockoutPoliciesDocument(parametersCollection);
    }

}
