package uk.co.idv.domain.usecases.verificationcontext.policy;

import lombok.extern.slf4j.Slf4j;
import uk.co.idv.domain.entities.policy.PolicyLevelConverter;
import uk.co.idv.domain.entities.verificationcontext.policy.VerificationPolicy;
import uk.co.idv.domain.usecases.policy.DefaultPolicyService;

@Slf4j
public class DefaultVerificationPolicyService extends DefaultPolicyService<VerificationPolicy> implements VerificationPolicyService {

    public DefaultVerificationPolicyService(final VerificationPolicyDao dao) {
        super(dao, new MultipleVerificationPoliciesHandler(), new PolicyLevelConverter());
    }

    public DefaultVerificationPolicyService(final VerificationPolicyDao dao,
                                            final MultipleVerificationPoliciesHandler multiplePoliciesHandler,
                                            final PolicyLevelConverter policyLevelConverter) {
        super(dao, multiplePoliciesHandler, policyLevelConverter);
    }

}
