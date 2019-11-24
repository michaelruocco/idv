package uk.co.idv.app.rest.lockout;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import uk.co.idv.api.lockout.policy.LockoutPolicyDocument;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.api.lockout.policy.LockoutPoliciesDocument;
import uk.co.idv.api.lockout.policy.LockoutPolicyAttributes;
import uk.co.idv.domain.usecases.lockout.LockoutPolicyService;
import uk.co.idv.api.lockout.policy.LockoutPolicyAttributesConverterDelegator;

import java.net.URI;
import java.util.Collection;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
public class LockoutPolicyController {

    private final LockoutPolicyService service;
    private final LockoutPolicyAttributesConverterDelegator attributesConverter;

    @GetMapping("/lockoutPolicies")
    public LockoutPoliciesDocument getLockoutPolicies() {
        final Collection<LockoutPolicy> policies = service.loadPolicies();
        return toDocument(attributesConverter.toAttributes(policies));
    }

    @GetMapping("/lockoutPolicies/{id}")
    public LockoutPolicyDocument getLockoutPolicy(@PathVariable("id") final UUID id) {
        final LockoutPolicy policy = service.loadPolicy(id);
        return toDocument(attributesConverter.toAttributes(policy));
    }

    @PostMapping("/lockoutPolicies")
    public ResponseEntity<LockoutPolicyDocument> createLockoutPolicy(@RequestBody final LockoutPolicyDocument document) {
        final LockoutPolicy policy = attributesConverter.toPolicy(document.getAttributes());
        service.createPolicy(policy);
        return ResponseEntity
                .created(buildGetContextUri(policy.getId()))
                .body(document);
    }

    @PutMapping("/lockoutPolicies")
    public LockoutPolicyDocument updateLockoutPolicy(@RequestBody final LockoutPolicyDocument document) {
        final LockoutPolicy policy = attributesConverter.toPolicy(document.getAttributes());
        service.updatePolicy(policy);
        return document;
    }

    private static LockoutPoliciesDocument toDocument(final Collection<LockoutPolicyAttributes> policies) {
        return new LockoutPoliciesDocument(policies);
    }

    private static LockoutPolicyDocument toDocument(final LockoutPolicyAttributes attributes) {
        return new LockoutPolicyDocument(attributes);
    }

    private static URI buildGetContextUri(final UUID id) {
        return linkTo(methodOn(LockoutPolicyController.class).getLockoutPolicy(id)).toUri();
    }

}
