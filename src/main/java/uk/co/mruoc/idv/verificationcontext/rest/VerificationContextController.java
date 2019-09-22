package uk.co.mruoc.idv.verificationcontext.rest;

import org.javamoney.moneta.Money;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.co.mruoc.idv.domain.model.activity.Activity;
import uk.co.mruoc.idv.domain.model.activity.OnlinePurchase;
import uk.co.mruoc.idv.domain.model.channel.Rsa;
import uk.co.mruoc.idv.identity.domain.model.CreditCardNumber;
import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationContext;
import uk.co.mruoc.idv.verificationcontext.domain.service.CreateContextRequest;
import uk.co.mruoc.idv.verificationcontext.domain.service.GetContextRequest;
import uk.co.mruoc.idv.verificationcontext.domain.service.VerificationContextService;

import java.time.Instant;
import java.util.UUID;

@RestController("/verificationContexts")
public class VerificationContextController {

    private final VerificationContextService contextService;

    public VerificationContextController(final VerificationContextService contextService) {
        this.contextService = contextService;
    }

    @PostMapping
    public VerificationContext createContext() {
        final Activity activity = OnlinePurchase.builder()
                .timestamp(Instant.now())
                .cost(Money.of(10.99, "GBP"))
                .merchantName("Amazon")
                .reference("ABC-123")
                .build();
        final CreateContextRequest request = CreateContextRequest.builder()
                .channel(new Rsa())
                .activity(activity)
                .providedAlias(new CreditCardNumber("49290012345678"))
                .build();
        return contextService.create(request);
    }

    @GetMapping("/{id}")
    public VerificationContext getContext(final UUID id) {
        final GetContextRequest request = GetContextRequest.builder()
                .id(id)
                .build();

        return contextService.get(request);
    }

}
