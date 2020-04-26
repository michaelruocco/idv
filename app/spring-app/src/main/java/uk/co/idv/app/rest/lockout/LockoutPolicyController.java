package uk.co.idv.app.rest.lockout;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.co.idv.api.lockout.policy.DefaultLockoutPolicyAttributesConverter;
import uk.co.idv.api.lockout.policy.LockoutPolicyDocument;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.api.lockout.policy.LockoutPoliciesDocument;
import uk.co.idv.api.lockout.policy.LockoutPolicyAttributes;
import uk.co.idv.domain.usecases.lockout.policy.LockoutPolicyService;

import java.net.URI;
import java.util.Collection;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lockout-policies")
public class LockoutPolicyController {

    private final LockoutPolicyService service;
    private final DefaultLockoutPolicyAttributesConverter attributesConverter;

    @PostMapping
    public ResponseEntity<LockoutPolicyDocument> createLockoutPolicy(@RequestBody final LockoutPolicyDocument document) {
        final LockoutPolicy policy = attributesConverter.toPolicy(document.getAttributes());
        service.create(policy);
        return ResponseEntity
                .created(buildGetContextUri(policy.getId()))
                .body(document);
    }

    @GetMapping
    public LockoutPoliciesDocument getLockoutPolicies() {
        final Collection<LockoutPolicy> policies = service.loadAll();
        return toDocument(attributesConverter.toAttributes(policies));
    }

    @GetMapping("/{id}")
    public LockoutPolicyDocument getLockoutPolicy(@PathVariable("id") final UUID id) {
        final LockoutPolicy policy = service.load(id);
        return toDocument(attributesConverter.toAttributes(policy));
    }

    @PutMapping("/{id}")
    public LockoutPolicyDocument updateLockoutPolicy(@RequestBody final LockoutPolicyDocument document) {
        final LockoutPolicy policy = attributesConverter.toPolicy(document.getAttributes());
        service.update(policy);
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
