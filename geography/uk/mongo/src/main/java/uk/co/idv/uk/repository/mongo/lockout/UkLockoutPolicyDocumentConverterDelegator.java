package uk.co.idv.uk.repository.mongo.lockout;

import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptStrategyFactory;
import uk.co.idv.repository.mongo.lockout.policy.LockoutPolicyDocumentConverterDelegator;
import uk.co.idv.repository.mongo.lockout.policy.hard.HardLockoutPolicyDocumentConverter;
import uk.co.idv.repository.mongo.lockout.policy.nonlocking.NonLockingPolicyDocumentConverter;
import uk.co.idv.repository.mongo.lockout.policy.soft.RecurringSoftLockoutPolicyDocumentConverter;
import uk.co.idv.repository.mongo.lockout.policy.soft.SoftLockIntervalDocumentsConverter;
import uk.co.idv.repository.mongo.lockout.policy.soft.SoftLockoutPolicyDocumentConverter;

import java.util.Arrays;

public class UkLockoutPolicyDocumentConverterDelegator extends LockoutPolicyDocumentConverterDelegator {

    public UkLockoutPolicyDocumentConverterDelegator(final RecordAttemptStrategyFactory recordAttemptStrategyFactory,
                                                     final SoftLockIntervalDocumentsConverter softLockIntervalDocumentsConverter) {
        super(Arrays.asList(
                new HardLockoutPolicyDocumentConverter(recordAttemptStrategyFactory),
                new NonLockingPolicyDocumentConverter(recordAttemptStrategyFactory),
                new SoftLockoutPolicyDocumentConverter(recordAttemptStrategyFactory, softLockIntervalDocumentsConverter),
                new RecurringSoftLockoutPolicyDocumentConverter(recordAttemptStrategyFactory, softLockIntervalDocumentsConverter)
        ));
    }

}
