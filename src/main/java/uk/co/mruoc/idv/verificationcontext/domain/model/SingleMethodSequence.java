package uk.co.mruoc.idv.verificationcontext.domain.model;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.CardCredentials;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.MobilePinsentry;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.OneTimePasscodeSms;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.PhysicalPinsentry;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.PushNotification;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.VerificationMethod;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.VerificationResult;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Slf4j
@ToString
public class SingleMethodSequence implements VerificationSequence {

    private final VerificationMethod method;
    private final Collection<VerificationResult> results;

    public SingleMethodSequence(final VerificationMethod method) {
        this(method, Collections.emptyList());
    }

    public SingleMethodSequence(final VerificationMethod method, final VerificationResult result) {
        this(method, Collections.singleton(result));
    }

    public SingleMethodSequence(final VerificationMethod method, final Collection<VerificationResult> results) {
        this.method = method;
        this.results = results;
    }

    @Override
    public Optional<PhysicalPinsentry> getPhysicalPinsentry() {
        return castMethodTo(PhysicalPinsentry.class);
    }

    @Override
    public Optional<MobilePinsentry> getMobilePinsentry() {
        return castMethodTo(MobilePinsentry.class);
    }

    @Override
    public Optional<PushNotification> getPushNotification() {
        return castMethodTo(PushNotification.class);
    }

    @Override
    public Optional<OneTimePasscodeSms> getOneTimePasscodeSms() {
        return castMethodTo(OneTimePasscodeSms.class);
    }

    @Override
    public Optional<CardCredentials> getCardCredentials() {
        return castMethodTo(CardCredentials.class);
    }

    @Override
    public Collection<VerificationMethod> getMethods() {
        return Collections.singleton(method);
    }

    @Override
    public Duration getDuration() {
        return method.getDuration();
    }

    @Override
    public boolean containsMethod(final String name) {
        return method.hasName(name);
    }

    @Override
    public boolean isEligible() {
        return method.isEligible();
    }

    @Override
    public VerificationSequence addResultIfContainsMethod(final VerificationResult result) {
        if (containsMethod(result.getMethodName())) {
            return addResult(result);
        }
        logResultNotAdded(result);
        return this;
    }

    @Override
    public boolean hasResults() {
        return !results.isEmpty();
    }

    @Override
    public Collection<VerificationResult> getResults() {
        return Collections.unmodifiableCollection(results);
    }

    @Override
    public boolean isComplete() {
        return !results.isEmpty();
    }

    @Override
    public boolean isSuccessful() {
        return results.stream().anyMatch(VerificationResult::isSuccessful);
    }

    private <T> Optional<T> castMethodTo(final Class<T> type) {
        if (type.isAssignableFrom(method.getClass())) {
            return Optional.of(type.cast(method));
        }
        return Optional.empty();
    }

    private VerificationSequence addResult(final VerificationResult result) {
        final Collection<VerificationResult> updatedResults = new ArrayList<>(results);
        updatedResults.add(result);
        return new SingleMethodSequence(method, updatedResults);
    }

    private void logResultNotAdded(final VerificationResult result) {
        log.info("result not added {} to sequence {} as sequence does not contain method with name {}",
                result,
                this,
                result.getMethodName());
    }

}
