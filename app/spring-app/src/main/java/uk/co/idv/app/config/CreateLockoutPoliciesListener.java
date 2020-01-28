package uk.co.idv.app.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import uk.co.idv.domain.usecases.lockout.InitialLockoutPolicyCreator;

@RequiredArgsConstructor
public class CreateLockoutPoliciesListener {

    private final InitialLockoutPolicyCreator policyCreator;

    @EventListener(ApplicationReadyEvent.class)
    @Order(2)
    public void populatePoliciesAfterStartup() {
        policyCreator.create();
    }

}
