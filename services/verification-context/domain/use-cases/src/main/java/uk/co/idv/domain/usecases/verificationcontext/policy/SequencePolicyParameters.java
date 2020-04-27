package uk.co.idv.domain.usecases.verificationcontext.policy;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.IterableUtils;
import uk.co.idv.domain.entities.verificationcontext.MultipleMethodSequence;
import uk.co.idv.domain.entities.verificationcontext.SingleMethodSequence;
import uk.co.idv.domain.entities.verificationcontext.VerificationSequence;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.usecases.verificationcontext.policy.method.MethodPolicyParameters;
import uk.co.idv.domain.usecases.verificationcontext.sequence.LoadSequencesRequest;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class SequencePolicyParameters {

    private final String name;
    private final Collection<MethodPolicyParameters> parameters;

    public SequencePolicyParameters(final MethodPolicyParameters parameters) {
        this(parameters.getMethodName(), Collections.singleton(parameters));
    }

    public SequencePolicyParameters(final String name, final MethodPolicyParameters... parameters) {
        this(name, Arrays.asList(parameters));
    }

    public VerificationSequence buildSequence(final LoadSequencesRequest request) {
        final Collection<VerificationMethod> methods = parameters.stream()
                .map(parameters -> parameters.buildMethod(request))
                .collect(Collectors.toList());
        if (methods.size() == 1) {
            return new SingleMethodSequence(IterableUtils.get(methods, 0));
        }
        return new MultipleMethodSequence(name, methods);
    }
}
