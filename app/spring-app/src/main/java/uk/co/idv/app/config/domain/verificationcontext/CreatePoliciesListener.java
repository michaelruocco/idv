package uk.co.idv.app.config.domain.verificationcontext;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import uk.co.idv.domain.usecases.policy.PolicyCreator;

@RequiredArgsConstructor
public class CreatePoliciesListener {

    private final PolicyCreator policyCreator;

    @EventListener(ApplicationReadyEvent.class)
    public void populatePoliciesAfterStartup() {
        policyCreator.create();
    }

}
