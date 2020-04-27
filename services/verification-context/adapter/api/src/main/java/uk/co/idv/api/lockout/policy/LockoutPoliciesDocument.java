package uk.co.idv.api.lockout.policy;

import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.mruoc.jsonapi.ApiDataWithId;
import uk.co.mruoc.jsonapi.batch.ApiBatchDocumentWithId;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class LockoutPoliciesDocument extends ApiBatchDocumentWithId<LockoutPolicy> {

    public LockoutPoliciesDocument(final LockoutPolicy... parameters) {
        this(Arrays.asList(parameters));
    }

    public LockoutPoliciesDocument(final Collection<LockoutPolicy> parameters) {
        super(toDataItems(parameters));
    }

    private static Collection<ApiDataWithId<LockoutPolicy>> toDataItems(final Collection<LockoutPolicy> collection) {
        return collection.stream()
                .map(Data::new)
                .collect(Collectors.toList());
    }

    private static class Data extends ApiDataWithId<LockoutPolicy> {

        private Data(final LockoutPolicy attributes) {
            super(attributes.getId(), "lockout-policies", attributes);
        }

    }

}
