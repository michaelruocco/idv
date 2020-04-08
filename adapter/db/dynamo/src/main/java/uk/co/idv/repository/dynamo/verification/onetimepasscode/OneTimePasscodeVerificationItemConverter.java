package uk.co.idv.repository.dynamo.verification.onetimepasscode;

import com.amazonaws.services.dynamodbv2.document.Item;
import lombok.Builder;
import uk.co.idv.domain.entities.onetimepasscode.OneTimePasscodeVerification;
import uk.co.idv.repository.dynamo.table.TimeToLiveCalculator;
import uk.co.idv.utils.json.converter.JsonConverter;

@Builder
public class OneTimePasscodeVerificationItemConverter {

    private final JsonConverter jsonConverter;
    private final TimeToLiveCalculator timeToLiveCalculator;

    public Item toItem(final OneTimePasscodeVerification verification) {
        final String json = jsonConverter.toJson(verification);
        System.out.println("saving otp verification with json " + json);
        return new Item()
                .withPrimaryKey("id", verification.getId().toString())
                .with("ttl", timeToLiveCalculator.calculate())
                .withJSON("body", json);
    }

    public OneTimePasscodeVerification toVerification(final Item item) {
        System.out.println("converting json to otp verification " + item.getJSON("body"));
        return jsonConverter.toObject(item.getJSON("body"), OneTimePasscodeVerification.class);
    }

}
