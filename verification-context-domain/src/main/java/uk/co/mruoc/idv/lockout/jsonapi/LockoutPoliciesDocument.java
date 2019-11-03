package uk.co.mruoc.idv.lockout.jsonapi;

import uk.co.mruoc.idv.lockout.domain.model.AbstractLockoutPolicyParameters;
import uk.co.mruoc.jsonapi.ApiDataWithId;
import uk.co.mruoc.jsonapi.batch.ApiBatchDocumentWithId;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class LockoutPoliciesDocument extends ApiBatchDocumentWithId<AbstractLockoutPolicyParameters> {

    public LockoutPoliciesDocument(final AbstractLockoutPolicyParameters... parameters) {
        this(Arrays.asList(parameters));
    }

    public LockoutPoliciesDocument(final Collection<AbstractLockoutPolicyParameters> parameters) {
        super(toDataItems(parameters));
    }

    private static Collection<ApiDataWithId<AbstractLockoutPolicyParameters>> toDataItems(final Collection<AbstractLockoutPolicyParameters> collection) {
        return collection.stream()
                .map(Data::new)
                .collect(Collectors.toList());
    }

    private static class Data extends ApiDataWithId<AbstractLockoutPolicyParameters> {

        private Data(final AbstractLockoutPolicyParameters attributes) {
            super(attributes.getId(), "lockoutPolicies", attributes);
        }

    }

}
