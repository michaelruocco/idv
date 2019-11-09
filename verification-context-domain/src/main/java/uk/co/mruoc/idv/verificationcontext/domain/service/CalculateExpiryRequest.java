package uk.co.mruoc.idv.verificationcontext.domain.service;

import lombok.Builder;
import lombok.Getter;
import uk.co.idv.domain.entities.activity.Activity;
import uk.co.idv.domain.entities.channel.Channel;
import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationSequences;

import java.time.Instant;

@Builder
@Getter
public class CalculateExpiryRequest {

    private final Instant created;
    private final Channel channel;
    private final Activity activity;
    private final VerificationSequences sequences;

}
