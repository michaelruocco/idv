package uk.co.mruoc.idv.verificationcontext.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import uk.co.mruoc.idv.verificationcontext.jsonapi.CreateContextRequestDocument;
import uk.co.mruoc.idv.verificationcontext.jsonapi.VerificationContextDocument;
import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationContext;
import uk.co.mruoc.idv.verificationcontext.domain.service.GetContextRequest;
import uk.co.mruoc.idv.verificationcontext.domain.service.VerificationContextService;

import java.util.UUID;

@RestController
public class VerificationContextController {

    private final VerificationContextService contextService;

    public VerificationContextController(final VerificationContextService contextService) {
        this.contextService = contextService;
    }

    @PostMapping("/verificationContexts")
    public VerificationContextDocument createContext(@RequestBody final CreateContextRequestDocument request) {
        final VerificationContext context = contextService.create(request.getAttributes());
        return toDocument(context);
    }

    @GetMapping("/verificationContexts/{id}")
    public VerificationContextDocument getContext(@PathVariable("id") final UUID id) {
        final GetContextRequest request = toGetContextRequest(id);
        final VerificationContext context = contextService.get(request);
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

}
