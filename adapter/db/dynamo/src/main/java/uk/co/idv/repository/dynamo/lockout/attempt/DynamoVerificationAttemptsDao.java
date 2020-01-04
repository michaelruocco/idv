package uk.co.idv.repository.dynamo.lockout.attempt;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttempts;
import uk.co.idv.domain.usecases.lockout.VerificationAttemptsDao;
import uk.co.idv.repository.dynamo.json.JsonConverter;

import java.util.Optional;
import java.util.UUID;

@Builder
@Slf4j
public class DynamoVerificationAttemptsDao implements VerificationAttemptsDao {

    private final JsonConverter converter;
    private final Table table;

    @Override
    public void save(final VerificationAttempts attempts) {
        log.info("saving verification attempts {}", attempts);
        final Item item = toItem(attempts);
        log.info("putting item {}", item);
        table.putItem(item);
    }

    @Override
    public Optional<VerificationAttempts> load(final UUID idvId) {
        log.info("loading verification attempts by idvId {}", idvId);
        final Item item = table.getItem("id", idvId.toString());
        if (item == null) {
            log.debug("verification attempts not found returning empty optional");
            return Optional.empty();
        }
        log.info("loaded item {}", item);
        final VerificationAttempts attempts = toVerificationAttempts(item);
        log.info("returning attempts {}", attempts);
        return Optional.of(attempts);
    }

    private Item toItem(final VerificationAttempts attempts) {
        final String json = converter.toJson(attempts);
        log.info("writing item with body {}", json);
        return new Item()
                .withPrimaryKey("id", attempts.getIdvId().toString())
                .withJSON("body", json);
    }

    private VerificationAttempts toVerificationAttempts(final Item item) {
        final String json = item.getJSON("body");
        log.info("loaded json {}", json);
        return converter.toObject(json, VerificationAttempts.class);
    }

}
