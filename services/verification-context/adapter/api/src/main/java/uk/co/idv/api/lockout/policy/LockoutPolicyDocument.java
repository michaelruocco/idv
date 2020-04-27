package uk.co.idv.api.lockout.policy;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.mruoc.jsonapi.ApiDataWithId;
import uk.co.mruoc.jsonapi.ApiDocumentWithId;

public class LockoutPolicyDocument extends ApiDocumentWithId<LockoutPolicy> {

    public LockoutPolicyDocument(final LockoutPolicy attributes) {
        super(new Data(attributes));
    }

    @JsonPropertyOrder({ "id", "type", "attributes" })
    private static class Data extends ApiDataWithId<LockoutPolicy> {

        private Data(final LockoutPolicy attributes) {
            super(attributes.getId(),"lockout-policies", attributes);
        }

    }

}
