package uk.co.idv.domain.entities.lockout.attempt;

import uk.co.idv.domain.entities.identity.alias.Alias;
import uk.co.idv.domain.entities.identity.alias.AliasesMother;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

public class VerificationAttemptsMother {

    private static final UUID CONTEXT_ID = UUID.fromString("fb059cfd-5613-49fe-8f34-2264b5da8343");
    private static final UUID VERIFICATION_ID = UUID.fromString("1fb7cd98-694d-4ba4-968a-9b86bbf52c01");
    private static final Instant TIMESTAMP = Instant.parse("2019-09-27T09:35:15.612Z");
    private static final UUID IDV_ID = UUID.fromString("49888d4b-36ac-4d1c-b529-3829869e5858");

    public static VerificationAttempts oneAttempt() {
        return withNumberOfAttempts(1);
    }

    public static VerificationAttempts oneAttempt(UUID idvId) {
        return withNumberOfAttempts(1, idvId);
    }

    public static VerificationAttempts withNumberOfAttempts(int numberOfAttempts) {
        return withNumberOfAttempts(numberOfAttempts, IDV_ID);
    }

    public static VerificationAttempts withNumberOfAttempts(int numberOfAttempts, final UUID idvId) {
        return withAttempts(buildAttemptsOfSize(numberOfAttempts, idvId));
    }

    public static VerificationAttempts withAttempts(final VerificationAttempt... attempts) {
        return withAttempts(Arrays.asList(attempts));
    }

    public static VerificationAttempts withAttempts(final Collection<VerificationAttempt> attempts) {
        final UUID id = UUID.fromString("20a32973-2018-46e8-a93c-28892e71da64");
        return new VerificationAttempts(id, IDV_ID, attempts);
    }

    public static VerificationAttempt successful() {
        return successful(IDV_ID);
    }

    public static VerificationAttempt successful(final UUID idvId) {
        return new VerificationAttemptSuccessful(CONTEXT_ID,
                "fake-channel",
                "fake-activity",
                AliasesMother.creditCardNumber(),
                idvId,
                "fake-method",
                VERIFICATION_ID,
                TIMESTAMP);
    }

    public static VerificationAttempt failed() {
        return failed(IDV_ID);
    }

    public static VerificationAttempt failed(final UUID idvId) {
        return failed(idvId, AliasesMother.creditCardNumber());
    }

    public static VerificationAttempt failed(final UUID idvId, final Alias alias) {
        return new VerificationAttemptFailed(CONTEXT_ID,
                "fake-channel",
                "fake-activity",
                alias,
                idvId,
                "fake-method",
                VERIFICATION_ID,
                TIMESTAMP);
    }

    private static Collection<VerificationAttempt> buildAttemptsOfSize(final int numberOfAttempts, final UUID idvId) {
        final Collection<VerificationAttempt> attempts = new ArrayList<>();
        for (int i = 0; i < numberOfAttempts; i++) {
            attempts.add(failed(idvId));
        }
        return Collections.unmodifiableCollection(attempts);
    }

}
