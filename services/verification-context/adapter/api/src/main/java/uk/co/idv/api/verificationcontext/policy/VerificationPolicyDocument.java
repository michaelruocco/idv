package uk.co.idv.api.verificationcontext.policy;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import uk.co.idv.domain.entities.verificationcontext.policy.VerificationPolicy;
import uk.co.mruoc.jsonapi.ApiDataWithId;
import uk.co.mruoc.jsonapi.ApiDocumentWithId;

public class VerificationPolicyDocument extends ApiDocumentWithId<VerificationPolicy> {

    public VerificationPolicyDocument(final VerificationPolicy attributes) {
        super(new Data(attributes));
    }

    @JsonPropertyOrder({ "id", "type", "attributes" })
    private static class Data extends ApiDataWithId<VerificationPolicy> {

        private Data(final VerificationPolicy attributes) {
            super(attributes.getId(),"verification-policies", attributes);
        }

    }

}
