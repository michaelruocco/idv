package uk.co.mruoc.idv.lockout.domain.service;

import lombok.Getter;

import java.util.Collection;
import java.util.Collections;

public class LockoutPolicyLoader {

    private final Collection<LockoutPolicy> policies;

    public LockoutPolicyLoader() {
        this(Collections.singleton(new LockoutPolicyRsa()));
    }

    public LockoutPolicyLoader(final Collection<LockoutPolicy> policies) {
        this.policies = policies;
    }

    public LockoutPolicy load(final LockoutPolicyRequest request) {
        return policies.stream()
                .filter(policy -> policy.appliesTo(request))
                .findFirst()
                .orElseThrow(() -> new LockoutPolicyNotFoundException(request));
    }

    @Getter
    public static class LockoutPolicyNotFoundException extends RuntimeException {

        private final LockoutPolicyRequest request;

        public LockoutPolicyNotFoundException(final LockoutPolicyRequest request) {
            super(String.format("channelId %s, activity %s aliasType %s",
                    request.getChannelId(),
                    request.getActivityName(),
                    request.getAliasType()));
            this.request = request;
        }

    }

}
