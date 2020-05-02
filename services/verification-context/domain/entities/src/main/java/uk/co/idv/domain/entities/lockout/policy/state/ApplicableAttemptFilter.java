package uk.co.idv.domain.entities.lockout.policy.state;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.domain.entities.identity.alias.Alias;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttempts;
import uk.co.idv.domain.entities.policy.PolicyLevel;

@RequiredArgsConstructor
@Slf4j
public class ApplicableAttemptFilter {

    private final PolicyLevel level;

    public VerificationAttempts filter(final CalculateLockoutStateRequest request) {
        final VerificationAttempts attempts = request.getAttempts();
        if (level.isAliasLevel()) {
            return filterByAlias(attempts, request.getAlias());
        }
        return filterByLevel(attempts);
    }

    private VerificationAttempts filterByAlias(final VerificationAttempts attempts, final Alias alias) {
        log.info("filtering by alias {}", alias);
        final VerificationAttempts aliasAttempts = attempts.filterApplicable(alias);
        return filterByLevel(aliasAttempts);
    }

    private VerificationAttempts filterByLevel(final VerificationAttempts attempts) {
        log.info("filtering by level {}", level);
        return attempts.filterApplicable(level);
    }

}
