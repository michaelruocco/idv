package uk.co.mruoc.idv.lockout.jsonapi;

import uk.co.mruoc.idv.lockout.domain.model.LockoutPolicyParameters;
import uk.co.mruoc.jsonapi.JsonApiDataItemWithId;

import java.util.Collection;
import java.util.stream.Collectors;

public class LockoutPoliciesDocument extends BatchJsonApiDocumentWithIds<LockoutPolicyParameters> {

    private static final String TYPE = "lockoutPolicies";

    public LockoutPoliciesDocument(final Collection<LockoutPolicyParameters> parameters) {
        super(TYPE, toDataItems(parameters));
    }

    private static Collection<JsonApiDataItemWithId<LockoutPolicyParameters>> toDataItems(final Collection<LockoutPolicyParameters> parameters) {
        return parameters.stream()
                .map(LockoutPoliciesDocument::toDataItem)
                .collect(Collectors.toList());
    }

    private static JsonApiDataItemWithId<LockoutPolicyParameters> toDataItem(final LockoutPolicyParameters parameters) {
        return new JsonApiDataItemWithId<>(parameters.getId(), TYPE, parameters);
    }

}
