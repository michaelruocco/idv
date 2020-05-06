package uk.co.idv.domain.usecases.verificationcontext.sequence.policy;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.IterableUtils;
import uk.co.idv.domain.entities.verificationcontext.MultipleMethodSequence;
import uk.co.idv.domain.entities.verificationcontext.SingleMethodSequence;
import uk.co.idv.domain.entities.verificationcontext.VerificationSequence;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.usecases.verificationcontext.sequence.policy.method.VerificationMethodPolicy;
import uk.co.idv.domain.usecases.verificationcontext.sequence.LoadSequencesRequest;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class VerificationSequencePolicy {

    private final String name;
    private final Collection<VerificationMethodPolicy> methodPolicies;

    public VerificationSequencePolicy(final VerificationMethodPolicy methodPolicy) {
        this(methodPolicy.getName(), Collections.singleton(methodPolicy));
    }

    public VerificationSequencePolicy(final String name, final VerificationMethodPolicy... methodPolicies) {
        this(name, Arrays.asList(methodPolicies));
    }

    public VerificationSequence buildSequence(final LoadSequencesRequest request) {
        final Collection<VerificationMethod> methods = methodPolicies.stream()
                .map(parameters -> parameters.buildMethod(request))
                .collect(Collectors.toList());
        if (methods.size() == 1) {
            return new SingleMethodSequence(IterableUtils.get(methods, 0));
        }
        return new MultipleMethodSequence(name, methods);
    }
}
