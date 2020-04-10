package uk.co.idv.app.rest.verification.onetimepasscode;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.co.idv.api.onetimepasscode.OneTimePasscodeVerificationDocument;
import uk.co.idv.api.onetimepasscode.ResendOneTimePasscodeRequestDocument;
import uk.co.idv.api.onetimepasscode.SendOneTimePasscodeRequestDocument;
import uk.co.idv.api.onetimepasscode.VerifyOneTimePasscodeRequestDocument;
import uk.co.idv.domain.entities.onetimepasscode.OneTimePasscodeVerification;
import uk.co.idv.domain.usecases.onetimepasscode.OneTimePasscodeService;

import java.net.URI;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
@RequestMapping("/one-time-passcode-verifications")
public class OneTimePasscodeController {

    private final OneTimePasscodeService service;

    @PostMapping
    public ResponseEntity<OneTimePasscodeVerificationDocument> sendOtp(@RequestBody final SendOneTimePasscodeRequestDocument request) {
        final OneTimePasscodeVerification verification = service.send(request.getAttributes());
        final OneTimePasscodeVerificationDocument document = toDocument(verification);
        return ResponseEntity
                .created(buildGetContextUri(verification.getId()))
                .body(document);
    }

    @PostMapping("/{id}")
    public OneTimePasscodeVerificationDocument resendOtp(@RequestBody final ResendOneTimePasscodeRequestDocument request) {
        final OneTimePasscodeVerification verification = service.send(request.getAttributes());
        return toDocument(verification);
    }

    @GetMapping("/{id}")
    public OneTimePasscodeVerificationDocument getVerification(@PathVariable("id") final UUID id) {
        final OneTimePasscodeVerification verification = service.load(id);
        return toDocument(verification);
    }

    @PatchMapping("/{id}")
    public OneTimePasscodeVerificationDocument verifyOtp(@RequestBody final VerifyOneTimePasscodeRequestDocument request) {
        final OneTimePasscodeVerification verification = service.verify(request.getAttributes());
        return toDocument(verification);
    }

    private static OneTimePasscodeVerificationDocument toDocument(final OneTimePasscodeVerification verification) {
        return new OneTimePasscodeVerificationDocument(verification);
    }

    private static URI buildGetContextUri(final UUID id) {
        return linkTo(methodOn(OneTimePasscodeController.class).getVerification(id)).toUri();
    }

}
