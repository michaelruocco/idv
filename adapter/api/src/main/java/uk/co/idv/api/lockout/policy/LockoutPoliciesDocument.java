package uk.co.idv.api.lockout.policy;

import uk.co.mruoc.jsonapi.ApiDataWithId;
import uk.co.mruoc.jsonapi.batch.ApiBatchDocumentWithId;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class LockoutPoliciesDocument extends ApiBatchDocumentWithId<LockoutPolicyAttributes> {

    public LockoutPoliciesDocument(final LockoutPolicyAttributes... parameters) {
        this(Arrays.asList(parameters));
    }

    public LockoutPoliciesDocument(final Collection<LockoutPolicyAttributes> parameters) {
        super(toDataItems(parameters));
    }

    private static Collection<ApiDataWithId<LockoutPolicyAttributes>> toDataItems(final Collection<LockoutPolicyAttributes> collection) {
        return collection.stream()
                .map(Data::new)
                .collect(Collectors.toList());
    }

    private static class Data extends ApiDataWithId<LockoutPolicyAttributes> {

        private Data(final LockoutPolicyAttributes attributes) {
            super(attributes.getId(), "lockout-policies", attributes);
        }

    }

}
