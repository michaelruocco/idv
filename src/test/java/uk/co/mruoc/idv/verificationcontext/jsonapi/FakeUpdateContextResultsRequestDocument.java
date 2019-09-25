package uk.co.mruoc.idv.verificationcontext.jsonapi;

import uk.co.mruoc.idv.verificationcontext.domain.model.result.FakeVerificationResultSuccessful;
import uk.co.mruoc.idv.verificationcontext.domain.service.UpdateContextResultRequest;

import java.util.UUID;

public class FakeUpdateContextResultsRequestDocument extends UpdateContextResultsRequestDocument {

    public FakeUpdateContextResultsRequestDocument() {
        super(buildRequest());
    }

    private static UpdateContextResultRequest buildRequest() {
        return UpdateContextResultRequest.builder()
                .contextId(UUID.fromString("513d667f-718b-4bb4-b969-35244b6e7e1e"))
                .result(new FakeVerificationResultSuccessful("push-notification"))
                .build();
    }

}
