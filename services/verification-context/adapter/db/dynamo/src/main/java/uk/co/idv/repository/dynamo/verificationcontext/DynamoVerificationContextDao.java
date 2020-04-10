package uk.co.idv.repository.dynamo.verificationcontext;

import com.amazonaws.services.dynamodbv2.document.Table;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.domain.entities.verificationcontext.VerificationContext;
import uk.co.idv.domain.usecases.verificationcontext.VerificationContextDao;

import java.util.Optional;
import java.util.UUID;

@Builder
@Slf4j
public class DynamoVerificationContextDao implements VerificationContextDao {

    private final VerificationContextItemConverter itemConverter;
    private final Table table;

    @Override
    public void save(final VerificationContext context) {
        log.info("saving verification context {}", context);
        table.putItem(itemConverter.toItem(context));
    }

    @Override
    public Optional<VerificationContext> load(final UUID id) {
        log.info("loading verification context by id {}", id);
        return Optional.ofNullable(table.getItem("id", id.toString()))
                .map(itemConverter::toContext);
    }

}
