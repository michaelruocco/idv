package uk.co.mruoc.idv.api.lockout;

import uk .co.mruoc.idv.lockout.domain.model.LockoutPolicyParameters;
import uk.co.mruoc.jsonapi.ApiDataWithId;
import uk.co.mruoc.jsonapi.batch.ApiBatchDocumentWithId;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class LockoutPoliciesDocument extends ApiBatchDocumentWithId<LockoutPolicyParameters> {

    public LockoutPoliciesDocument(final LockoutPolicyParameters... parameters) {
        this(Arrays.asList(parameters));
    }

    public LockoutPoliciesDocument(final Collection<LockoutPolicyParameters> parameters) {
        super(toDataItems(parameters));
    }

    private static Collection<ApiDataWithId<LockoutPolicyParameters>> toDataItems(final Collection<LockoutPolicyParameters> collection) {
        return collection.stream()
                .map(Data::new)
                .collect(Collectors.toList());
    }

    private static class Data extends ApiDataWithId<LockoutPolicyParameters> {

        private Data(final LockoutPolicyParameters attributes) {
            super(attributes.getId(), "lockoutPolicies", attributes);
        }

    }

}
