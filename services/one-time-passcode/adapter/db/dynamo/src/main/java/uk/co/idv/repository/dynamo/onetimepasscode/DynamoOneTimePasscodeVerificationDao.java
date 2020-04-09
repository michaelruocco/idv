package uk.co.idv.repository.dynamo.onetimepasscode;

import com.amazonaws.services.dynamodbv2.document.Table;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.domain.entities.onetimepasscode.OneTimePasscodeVerification;
import uk.co.idv.domain.usecases.onetimepasscode.OneTimePasscodeVerificationDao;

import java.util.Optional;
import java.util.UUID;

@Builder
@Slf4j
public class DynamoOneTimePasscodeVerificationDao implements OneTimePasscodeVerificationDao {

    private final OneTimePasscodeVerificationItemConverter itemConverter;
    private final Table table;

    @Override
    public void save(final OneTimePasscodeVerification context) {
        log.info("saving one time passcode verification {}", context);
        table.putItem(itemConverter.toItem(context));
    }

    @Override
    public Optional<OneTimePasscodeVerification> load(final UUID id) {
        log.info("loading one time passcode verification by id {}", id);
        return Optional.ofNullable(table.getItem("id", id.toString()))
                .map(itemConverter::toVerification);
    }

}
