package uk.co.idv.api.lockout;

import uk.co.idv.json.lockout.LockoutPolicyDto;
import uk.co.mruoc.jsonapi.ApiDataWithId;
import uk.co.mruoc.jsonapi.batch.ApiBatchDocumentWithId;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class LockoutPoliciesDocument extends ApiBatchDocumentWithId<LockoutPolicyDto> {

    public LockoutPoliciesDocument(final LockoutPolicyDto... parameters) {
        this(Arrays.asList(parameters));
    }

    public LockoutPoliciesDocument(final Collection<LockoutPolicyDto> parameters) {
        super(toDataItems(parameters));
    }

    private static Collection<ApiDataWithId<LockoutPolicyDto>> toDataItems(final Collection<LockoutPolicyDto> collection) {
        return collection.stream()
                .map(Data::new)
                .collect(Collectors.toList());
    }

    private static class Data extends ApiDataWithId<LockoutPolicyDto> {

        private Data(final LockoutPolicyDto attributes) {
            super(attributes.getId(), "lockoutPolicies", attributes);
        }

    }

}
