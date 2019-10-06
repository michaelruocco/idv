package uk.co.mruoc.idv.lockout.domain.service;

import uk.co.mruoc.idv.identity.domain.model.Alias;


public interface LockoutRequest extends ChannelIdProvider, ActivityNameProvider, AliasTypeProvider {

    Alias getAlias();

}
