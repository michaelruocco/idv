package uk.co.idv.api.verificationcontext;

import uk.co.idv.domain.entities.verificationcontext.result.FakeVerificationResultSuccessful;
import uk.co.idv.domain.usecases.verificationcontext.result.RecordResultRequest;

import java.util.UUID;

public class FakeUpdateContextResultsRequestDocument extends UpdateContextResultsRequestDocument {

    public FakeUpdateContextResultsRequestDocument() {
        super(buildRequest());
    }

    private static RecordResultRequest buildRequest() {
        return RecordResultRequest.builder()
                .contextId(UUID.fromString("513d667f-718b-4bb4-b969-35244b6e7e1e"))
                .result(new FakeVerificationResultSuccessful("push-notification"))
                .build();
    }

}
