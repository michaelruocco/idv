package uk.co.idv.json.account;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.domain.entities.card.account.Account;

public class AccountModule extends SimpleModule {

    public AccountModule() {
        super("account-module", Version.unknownVersion());

        setMixInAnnotation(Account.class, AccountMixin.class);
    }

}
