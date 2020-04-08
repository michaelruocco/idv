package uk.co.idv.repository.inmemory.verification.onetimepasscode;

import uk.co.idv.domain.entities.onetimepasscode.OneTimePasscodeVerification;
import uk.co.idv.domain.usecases.onetimepasscode.OneTimePasscodeVerificationDao;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class InMemoryOneTimePasscodeVerificationDao implements OneTimePasscodeVerificationDao {

    private final Map<UUID, OneTimePasscodeVerification> verifications = new HashMap<>();

    @Override
    public void save(final OneTimePasscodeVerification verification) {
        verifications.put(verification.getId(), verification);
    }

    @Override
    public Optional<OneTimePasscodeVerification> load(final UUID id) {
        return Optional.ofNullable(verifications.get(id));
    }

}
