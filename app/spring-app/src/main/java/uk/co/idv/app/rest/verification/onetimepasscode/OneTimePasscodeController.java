package uk.co.idv.app.rest.verification.onetimepasscode;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import uk.co.idv.api.verification.onetimepasscode.SendOneTimePasscodeRequestDocument;
import uk.co.idv.api.verification.onetimepasscode.OneTimePasscodeVerificationDocument;
import uk.co.idv.api.verification.onetimepasscode.ResendOneTimePasscodeRequestDocument;
import uk.co.idv.domain.entities.verification.onetimepasscode.OneTimePasscodeVerification;
import uk.co.idv.domain.usecases.verification.onetimepasscode.OneTimePasscodeService;

import java.net.URI;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
public class OneTimePasscodeController {

    private final OneTimePasscodeService service;

    @PostMapping("/oneTimePasscodeVerifications")
    public ResponseEntity<OneTimePasscodeVerificationDocument> sendOtp(@RequestBody final SendOneTimePasscodeRequestDocument request) {
        final OneTimePasscodeVerification verification = service.send(request.getAttributes());
        final OneTimePasscodeVerificationDocument document = toDocument(verification);
        return ResponseEntity
                .created(buildGetContextUri(verification.getId()))
                .body(document);
    }

    @PostMapping("/oneTimePasscodeVerifications/{id}")
    public ResponseEntity<OneTimePasscodeVerificationDocument> resendOtp(@RequestBody final ResendOneTimePasscodeRequestDocument request) {
        final OneTimePasscodeVerification verification = service.send(request.getAttributes());
        final OneTimePasscodeVerificationDocument document = toDocument(verification);
        return ResponseEntity.ok(document);
    }

    @GetMapping("/oneTimePasscodeVerifications/{id}")
    public OneTimePasscodeVerificationDocument getVerification(@PathVariable("id") final UUID id) {
        final OneTimePasscodeVerification verification = service.load(id);
        return toDocument(verification);
    }

    private static OneTimePasscodeVerificationDocument toDocument(final OneTimePasscodeVerification verification) {
        return new OneTimePasscodeVerificationDocument(verification);
    }

    private static URI buildGetContextUri(final UUID id) {
        return linkTo(methodOn(OneTimePasscodeController.class).getVerification(id)).toUri();
    }

}
