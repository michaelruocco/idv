package uk.co.idv.api.lockout.policy;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import uk.co.mruoc.jsonapi.ApiDataWithId;
import uk.co.mruoc.jsonapi.ApiDocumentWithId;

public class LockoutPolicyDocument extends ApiDocumentWithId<LockoutPolicyAttributes> {

    public LockoutPolicyDocument(final LockoutPolicyAttributes attributes) {
        super(new Data(attributes));
    }

    @JsonPropertyOrder({ "id", "type", "attributes" })
    private static class Data extends ApiDataWithId<LockoutPolicyAttributes> {

        private Data(final LockoutPolicyAttributes attributes) {
            super(attributes.getId(),"lockoutPolicies", attributes);
        }

    }

}
