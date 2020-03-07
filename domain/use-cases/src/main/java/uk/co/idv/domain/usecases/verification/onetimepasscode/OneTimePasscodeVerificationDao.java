package uk.co.idv.domain.usecases.verification.onetimepasscode;

import uk.co.idv.domain.entities.verification.onetimepasscode.OneTimePasscodeVerification;

import java.util.Optional;
import java.util.UUID;

public interface OneTimePasscodeVerificationDao {

    void save(final OneTimePasscodeVerification verification);

    Optional<OneTimePasscodeVerification> load(final UUID id);

}
