package uk.co.idv.app.config.domain.verificationcontext;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import uk.co.idv.domain.usecases.verificationcontext.policy.InitialVerificationPolicyCreator;

@RequiredArgsConstructor
public class CreateVerificationPoliciesListener {

    private final InitialVerificationPolicyCreator policyCreator;

    @EventListener(ApplicationReadyEvent.class)
    @Order(3)
    public void populatePoliciesAfterStartup() {
        policyCreator.create();
    }

}
