package uk.co.idv.onetimepasscode.sender.sns;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.domain.entities.verification.onetimepasscode.OneTimePasscodeDelivery;
import uk.co.idv.domain.usecases.verification.onetimepasscode.sender.OneTimePasscodeDeliverySender;

@Builder
@Slf4j
public class SnsOneTimePasscodeDeliverySender implements OneTimePasscodeDeliverySender {

    private final OneTimePasscodeDeliveryConverter converter;
    private final AmazonSNS client;

    @Override
    public String send(final OneTimePasscodeDelivery delivery) {
        final PublishRequest request = converter.toPublishRequest(delivery);
        log.info("sending publish request {}", request);
        final PublishResult result = client.publish(request);
        log.info("message {} sent", result.getMessageId());
        return result.getMessageId();
    }

}
