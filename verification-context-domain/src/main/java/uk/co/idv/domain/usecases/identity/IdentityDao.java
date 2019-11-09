package uk.co.idv.domain.usecases.identity;

import uk.co.idv.domain.entities.identity.Alias;
import uk.co.idv.domain.entities.identity.Identity;

import java.util.Optional;

public interface IdentityDao {

    void save(final Identity identity);

    Optional<Identity> load(final Alias alias);

}
