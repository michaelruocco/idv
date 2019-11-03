package uk.co.mruoc.idv.lockout.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.co.mruoc.idv.lockout.domain.model.AbstractLockoutPolicyParameters;
import uk.co.mruoc.idv.lockout.domain.service.LockoutPolicyService;
import uk.co.mruoc.idv.lockout.jsonapi.LockoutPoliciesDocument;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
public class LockoutPolicyController {

    private final LockoutPolicyService service;

    @GetMapping("/lockoutPolicies")
    public LockoutPoliciesDocument getLockoutPolicies() {
        final Collection<AbstractLockoutPolicyParameters> policies = service.loadPolicies();
        return toDocument(policies);
    }

    private static LockoutPoliciesDocument toDocument(final Collection<AbstractLockoutPolicyParameters> policies) {
        return new LockoutPoliciesDocument(policies);
    }

}
