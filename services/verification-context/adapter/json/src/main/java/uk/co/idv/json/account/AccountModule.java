package uk.co.idv.json.account;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.domain.entities.card.account.Account;
import uk.co.idv.json.cardnumber.CardNumberModule;
import java.util.Collections;

public class AccountModule extends SimpleModule {

    public AccountModule() {
        super("account-module", Version.unknownVersion());

        setMixInAnnotation(Account.class, AccountMixin.class);

        addDeserializer(Account.class, new AccountDeserializer());
    }

    @Override
    public Iterable<? extends Module> getDependencies() {
        return Collections.singleton(new CardNumberModule());
    }

}
