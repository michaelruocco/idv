package uk.co.idv.repository.dynamo.lockout.attempt;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttempts;
import uk.co.idv.domain.usecases.lockout.attempt.VerificationAttemptDao;
import uk.co.idv.utils.json.converter.JsonConverter;

import java.util.Optional;
import java.util.UUID;

@Builder
@Slf4j
public class DynamoVerificationAttemptDao implements VerificationAttemptDao {

    private final JsonConverter converter;
    private final Table table;

    @Override
    public void save(final VerificationAttempts attempts) {
        log.info("saving verification attempts {}", attempts);
        table.putItem(toItem(attempts));
    }

    @Override
    public Optional<VerificationAttempts> load(final UUID idvId) {
        log.info("loading verification attempts by idvId {}", idvId);
        return Optional.ofNullable(table.getItem("id", idvId.toString()))
                .map(this::toVerificationAttempts);
    }

    private Item toItem(final VerificationAttempts attempts) {
        return new Item()
                .withPrimaryKey("id", attempts.getIdvId().toString())
                .withJSON("body", converter.toJson(attempts));
    }

    private VerificationAttempts toVerificationAttempts(final Item item) {
        return converter.toObject(item.getJSON("body"), VerificationAttempts.class);
    }

}
