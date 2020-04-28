package uk.co.idv.domain.usecases.verificationcontext.sequence.policy;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.IterableUtils;
import uk.co.idv.domain.entities.verificationcontext.MultipleMethodSequence;
import uk.co.idv.domain.entities.verificationcontext.SingleMethodSequence;
import uk.co.idv.domain.entities.verificationcontext.VerificationSequence;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.usecases.verificationcontext.sequence.policy.method.MethodPolicy;
import uk.co.idv.domain.usecases.verificationcontext.sequence.LoadSequencesRequest;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class SequencePolicy {

    private final String name;
    private final Collection<MethodPolicy> methodPolicies;

    public SequencePolicy(final MethodPolicy methodPolicy) {
        this(methodPolicy.getName(), Collections.singleton(methodPolicy));
    }

    public SequencePolicy(final String name, final MethodPolicy... methodPolicies) {
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
