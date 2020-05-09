package uk.co.idv.api.verificationcontext.policy;

import uk.co.idv.domain.entities.verificationcontext.policy.VerificationPolicy;
import uk.co.mruoc.jsonapi.ApiDataWithId;
import uk.co.mruoc.jsonapi.batch.ApiBatchDocumentWithId;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class VerificationPoliciesDocument extends ApiBatchDocumentWithId<VerificationPolicy> {

    public VerificationPoliciesDocument(final VerificationPolicy... parameters) {
        this(Arrays.asList(parameters));
    }

    public VerificationPoliciesDocument(final Collection<VerificationPolicy> parameters) {
        super(toDataItems(parameters));
    }

    private static Collection<ApiDataWithId<VerificationPolicy>> toDataItems(final Collection<VerificationPolicy> collection) {
        return collection.stream()
                .map(Data::new)
                .collect(Collectors.toList());
    }

    private static class Data extends ApiDataWithId<VerificationPolicy> {

        private Data(final VerificationPolicy attributes) {
            super(attributes.getId(), "verification-policies", attributes);
        }

    }

}
