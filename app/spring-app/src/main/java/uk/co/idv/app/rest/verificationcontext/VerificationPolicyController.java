package uk.co.idv.app.rest.verificationcontext;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.co.idv.api.verificationcontext.policy.VerificationPoliciesDocument;
import uk.co.idv.api.verificationcontext.policy.VerificationPolicyDocument;
import uk.co.idv.domain.entities.verificationcontext.policy.VerificationPolicy;
import uk.co.idv.domain.usecases.verificationcontext.policy.VerificationPolicyService;

import java.net.URI;
import java.util.Collection;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
@RequestMapping("/verification-policies")
public class VerificationPolicyController {

    private final VerificationPolicyService service;

    @GetMapping
    public VerificationPoliciesDocument getPolicies() {
        final Collection<VerificationPolicy> policies = service.loadAll();
        return new VerificationPoliciesDocument(policies);
    }

    @GetMapping("/{id}")
    public VerificationPolicyDocument getPolicy(@PathVariable("id") final UUID id) {
        final VerificationPolicy policy = service.load(id);
        return new VerificationPolicyDocument(policy);
    }

    @PostMapping
    public ResponseEntity<VerificationPolicyDocument> createPolicy(@RequestBody final VerificationPolicyDocument document) {
        final VerificationPolicy policy = document.getAttributes();
        service.create(policy);
        return ResponseEntity
                .created(buildGetContextUri(policy.getId()))
                .body(document);
    }

    @PutMapping("/{id}")
    public VerificationPolicyDocument updatePolicy(@RequestBody final VerificationPolicyDocument document) {
        final VerificationPolicy policy = document.getAttributes();
        service.update(policy);
        return document;
    }

    private static URI buildGetContextUri(final UUID id) {
        return linkTo(methodOn(VerificationPolicyController.class).getPolicy(id)).toUri();
    }

}
