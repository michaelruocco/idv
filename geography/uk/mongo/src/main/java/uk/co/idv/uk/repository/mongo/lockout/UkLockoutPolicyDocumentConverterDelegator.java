package uk.co.idv.uk.repository.mongo.lockout;

import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptStrategyFactory;
import uk.co.idv.repository.mongo.lockout.policy.LockoutPolicyDocumentConverterDelegator;
import uk.co.idv.repository.mongo.lockout.policy.hard.HardLockoutPolicyDocumentConverter;
import uk.co.idv.repository.mongo.lockout.policy.nonlocking.NonLockingPolicyDocumentConverter;

import java.util.Arrays;

public class UkLockoutPolicyDocumentConverterDelegator extends LockoutPolicyDocumentConverterDelegator {

    public UkLockoutPolicyDocumentConverterDelegator(final RecordAttemptStrategyFactory recordAttemptStrategyFactory) {
        super(Arrays.asList(
                new HardLockoutPolicyDocumentConverter(recordAttemptStrategyFactory),
                new NonLockingPolicyDocumentConverter(recordAttemptStrategyFactory)
        ));
    }

}
