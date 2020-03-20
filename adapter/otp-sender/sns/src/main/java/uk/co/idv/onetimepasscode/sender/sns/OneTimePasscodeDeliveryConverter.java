package uk.co.idv.onetimepasscode.sender.sns;

import com.amazonaws.services.sns.model.PublishRequest;
import lombok.RequiredArgsConstructor;
import uk.co.idv.domain.entities.verification.onetimepasscode.OneTimePasscodeDelivery;
import uk.co.idv.onetimepasscode.sender.sns.attributes.MessageAttributeBuilder;

@RequiredArgsConstructor
public class OneTimePasscodeDeliveryConverter {

    private final MessageAttributeBuilder attributeBuilder;

    public PublishRequest toPublishRequest(final OneTimePasscodeDelivery delivery) {
        return new PublishRequest()
                .withMessage(delivery.getMessage())
                .withPhoneNumber(delivery.getMethod().getValue())
                .withMessageAttributes(attributeBuilder.build());
    }

}
