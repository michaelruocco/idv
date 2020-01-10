package uk.co.idv.repository.dynamo.verificationcontext;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.domain.entities.verificationcontext.VerificationContext;
import uk.co.idv.domain.usecases.verificationcontext.VerificationContextDao;
import uk.co.idv.repository.dynamo.json.JsonConverter;

import java.util.Optional;
import java.util.UUID;

@Builder
@Slf4j
public class DynamoVerificationContextDao implements VerificationContextDao {

    private final JsonConverter converter;
    private final Table table;

    @Override
    public void save(final VerificationContext context) {
        log.info("saving verification context {}", context);
        table.putItem(toItem(context));
    }

    @Override
    public Optional<VerificationContext> load(final UUID id) {
        log.info("loading verification context by id {}", id);
        return Optional.ofNullable(table.getItem("id", id.toString()))
                .map(this::toVerificationContext);
    }

    private Item toItem(final VerificationContext context) {
        return new Item()
                .withPrimaryKey("id", context.getId().toString())
                .withJSON("body", converter.toJson(context));
    }

    private VerificationContext toVerificationContext(final Item item) {
        return converter.toObject(item.getJSON("body"), VerificationContext.class);
    }

}
