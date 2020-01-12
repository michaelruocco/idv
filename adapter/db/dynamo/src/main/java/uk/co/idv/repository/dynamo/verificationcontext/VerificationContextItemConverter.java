package uk.co.idv.repository.dynamo.verificationcontext;

import com.amazonaws.services.dynamodbv2.document.Item;
import lombok.Builder;
import uk.co.idv.domain.entities.verificationcontext.VerificationContext;
import uk.co.idv.repository.dynamo.table.TimeToLiveCalculator;
import uk.co.idv.repository.dynamo.json.JsonConverter;

@Builder
public class VerificationContextItemConverter {

    private final JsonConverter jsonConverter;
    private final TimeToLiveCalculator timeToLiveCalculator;

    public Item toItem(final VerificationContext context) {
        return new Item()
                .withPrimaryKey("id", context.getId().toString())
                .with("ttl", timeToLiveCalculator.calculate())
                .withJSON("body", jsonConverter.toJson(context));
    }

    public VerificationContext toContext(final Item item) {
        return jsonConverter.toObject(item.getJSON("body"), VerificationContext.class);
    }

}
