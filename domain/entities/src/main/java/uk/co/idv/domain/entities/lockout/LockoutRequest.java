package uk.co.idv.domain.entities.lockout;

import uk.co.idv.domain.entities.identity.alias.Alias;


public interface LockoutRequest extends ChannelIdProvider, ActivityNameProvider, AliasTypeProvider {

    Alias getAlias();

}
