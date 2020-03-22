package uk.co.idv.app.rest.verificationcontext;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.co.idv.api.verificationcontext.CreateContextRequestDocument;
import uk.co.idv.api.verificationcontext.UpdateContextResultsRequestDocument;
import uk.co.idv.api.verificationcontext.VerificationContextDocument;
import uk.co.idv.domain.entities.verificationcontext.VerificationContext;
import uk.co.idv.domain.usecases.verificationcontext.VerificationContextService;

import java.net.URI;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@RequiredArgsConstructor
@RequestMapping("/verification-contexts")
public class VerificationContextController {

    private final VerificationContextService contextService;

    @PostMapping
    public ResponseEntity<VerificationContextDocument> createContext(@RequestBody final CreateContextRequestDocument request) {
        final VerificationContext context = contextService.create(request.getAttributes());
        final VerificationContextDocument document = toDocument(context);
        return ResponseEntity
                .created(buildGetContextUri(context.getId()))
                .body(document);
    }

    @GetMapping("/{id}")
    public VerificationContextDocument getContext(@PathVariable("id") final UUID id) {
        final VerificationContext context = contextService.load(id);
        return toDocument(context);
    }

    @PatchMapping("/{id}")
    public VerificationContextDocument updateContextResults(@RequestBody final UpdateContextResultsRequestDocument request) {
        final VerificationContext context = contextService.recordResult(request.getAttributes());
        return toDocument(context);
    }

    private static VerificationContextDocument toDocument(final VerificationContext context) {
        return new VerificationContextDocument(context);
    }

    private static URI buildGetContextUri(final UUID id) {
        return linkTo(methodOn(VerificationContextController.class).getContext(id)).toUri();
    }

}
