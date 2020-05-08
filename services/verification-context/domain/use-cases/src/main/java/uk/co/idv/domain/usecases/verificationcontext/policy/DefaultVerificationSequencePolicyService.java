package uk.co.idv.domain.usecases.verificationcontext.policy;

import lombok.extern.slf4j.Slf4j;
import uk.co.idv.domain.entities.policy.PolicyLevelConverter;
import uk.co.idv.domain.entities.verificationcontext.sequence.policy.VerificationSequencesPolicy;
import uk.co.idv.domain.usecases.policy.DefaultPolicyService;

@Slf4j
public class DefaultVerificationSequencePolicyService extends DefaultPolicyService<VerificationSequencesPolicy> implements VerificationSequencePolicyService {

    public DefaultVerificationSequencePolicyService(final VerificationSequencePolicyDao dao) {
        super(dao, new MultipleVerificationSequencePoliciesHandler(), new PolicyLevelConverter());
    }

    public DefaultVerificationSequencePolicyService(final VerificationSequencePolicyDao dao,
                                                    final MultipleVerificationSequencePoliciesHandler multiplePoliciesHandler,
                                                    final PolicyLevelConverter policyLevelConverter) {
        super(dao, multiplePoliciesHandler, policyLevelConverter);
    }

}
