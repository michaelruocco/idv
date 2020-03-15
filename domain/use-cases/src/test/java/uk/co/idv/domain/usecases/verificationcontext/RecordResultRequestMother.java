package uk.co.idv.domain.usecases.verificationcontext;

import uk.co.idv.domain.entities.verificationcontext.result.FakeVerificationResultSuccessful;

import java.util.UUID;

public class RecordResultRequestMother {

    private RecordResultRequestMother() {
        // utility class
    }

    public static RecordResultRequest build() {
        return RecordResultRequest.builder()
                .contextId(UUID.fromString("d39510ac-9ef2-4fd0-b25f-02671c898f61"))
                .result(new FakeVerificationResultSuccessful("method-name"))
                .build();
    }

}
