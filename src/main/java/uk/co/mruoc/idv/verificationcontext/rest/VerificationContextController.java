package uk.co.mruoc.idv.verificationcontext.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import uk.co.mruoc.idv.verificationcontext.jsonapi.CreateContextRequestDocument;
import uk.co.mruoc.idv.verificationcontext.jsonapi.UpdateContextResultsRequestDocument;
import uk.co.mruoc.idv.verificationcontext.jsonapi.VerificationContextDocument;
import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationContext;
import uk.co.mruoc.idv.verificationcontext.domain.service.GetContextRequest;
import uk.co.mruoc.idv.verificationcontext.domain.service.VerificationContextService;

import java.net.URI;
import java.util.UUID;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class VerificationContextController {

    private final VerificationContextService contextService;

    public VerificationContextController(final VerificationContextService contextService) {
        this.contextService = contextService;
    }

    @PostMapping("/verificationContexts")
    public ResponseEntity<VerificationContextDocument> createContext(@RequestBody final CreateContextRequestDocument request) {
        final VerificationContext context = contextService.create(request.getAttributes());
        final VerificationContextDocument document = toDocument(context);
        return ResponseEntity
                .created(buildGetContextUri(context.getId()))
                .body(document);
    }

    @GetMapping("/verificationContexts/{id}")
    public VerificationContextDocument getContext(@PathVariable("id") final UUID id) {
        final GetContextRequest request = toGetContextRequest(id);
        final VerificationContext context = contextService.get(request);
        return toDocument(context);
    }

    @PatchMapping("/verificationContexts/{id}")
    public VerificationContextDocument updateContextResults(@RequestBody final UpdateContextResultsRequestDocument request) {
        final VerificationContext context = contextService.updateResults(request.getAttributes());
        return toDocument(context);
    }

    private static GetContextRequest toGetContextRequest(final UUID id) {
        return GetContextRequest.builder()
                .id(id)
                .build();
    }

    private static VerificationContextDocument toDocument(final VerificationContext context) {
        return new VerificationContextDocument(context);
    }

    private static URI buildGetContextUri(final UUID id) {
        return linkTo(methodOn(VerificationContextController.class).getContext(id)).toUri();
    }

}
