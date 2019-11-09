package uk.co.mruoc.idv.lockout.domain.service;

import uk.co.idv.domain.entities.identity.Alias;


public interface LockoutRequest extends ChannelIdProvider, ActivityNameProvider, AliasTypeProvider {

    Alias getAlias();

}
