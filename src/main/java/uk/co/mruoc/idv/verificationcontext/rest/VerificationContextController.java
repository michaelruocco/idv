package uk.co.mruoc.idv.verificationcontext.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationContext;
import uk.co.mruoc.idv.verificationcontext.domain.service.CreateContextRequest;
import uk.co.mruoc.idv.verificationcontext.domain.service.GetContextRequest;
import uk.co.mruoc.idv.verificationcontext.domain.service.VerificationContextService;

import java.util.UUID;

@RestController("/verificationContexts")
public class VerificationContextController {

    private final VerificationContextService contextService;

    public VerificationContextController(final VerificationContextService contextService) {
        this.contextService = contextService;
    }

    @PostMapping
    public VerificationContext createContext(@RequestBody final CreateContextRequest request) {
        return contextService.create(request);
    }

    @GetMapping("/{id}")
    public VerificationContext getContext(final UUID id) {
        final GetContextRequest request = toGetContextRequest(id);
        return contextService.get(request);
    }

    private static GetContextRequest toGetContextRequest(final UUID id) {
        return GetContextRequest.builder()
                .id(id)
                .build();
    }

}
