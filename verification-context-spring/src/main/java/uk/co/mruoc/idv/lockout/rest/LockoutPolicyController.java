package uk.co.mruoc.idv.lockout.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.co.mruoc.idv.api.lockout.LockoutPoliciesDocument;
import uk.co.mruoc.idv.lockout.domain.model.LockoutPolicyParameters;
import uk.co.mruoc.idv.lockout.domain.service.LockoutPolicyService;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
public class LockoutPolicyController {

    private final LockoutPolicyService service;

    @GetMapping("/lockoutPolicies")
    public LockoutPoliciesDocument getLockoutPolicies() {
        final Collection<LockoutPolicyParameters> policies = service.loadPolicies();
        return toDocument(policies);
    }

    private static LockoutPoliciesDocument toDocument(final Collection<LockoutPolicyParameters> policies) {
        return new LockoutPoliciesDocument(policies);
    }

}
