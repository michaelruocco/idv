package uk.co.idv.domain.usecases.identity.data;

import uk.co.idv.domain.entities.identity.alias.Alias;
import uk.co.idv.domain.usecases.identity.UpsertIdentityRequest;

// TODO rename to mobile device loader and actually return mobile devices
// once have checked what fields a device needs to have
public class MobileApplicationEligibleLoader {

    public boolean load(final UpsertIdentityRequest request) {
        final Alias providedAlias = request.getProvidedAlias();
        return valueEndsWithNine(providedAlias);
    }

    private boolean valueEndsWithNine(final Alias alias) {
        return alias.getValue().endsWith("9");
    }

}
