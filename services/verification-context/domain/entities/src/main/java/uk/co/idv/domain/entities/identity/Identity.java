package uk.co.idv.domain.entities.identity;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import uk.co.idv.domain.entities.card.account.Account;
import uk.co.idv.domain.entities.identity.alias.Alias;
import uk.co.idv.domain.entities.identity.alias.Aliases;
import uk.co.idv.domain.entities.mobiledevice.MobileDevice;
import uk.co.idv.domain.entities.phonenumber.PhoneNumbers;

import java.util.Collection;
import java.util.UUID;

@EqualsAndHashCode
@Builder
@ToString
public class Identity {

    private final Aliases aliases;
    private final PhoneNumbers phoneNumbers;
    private final Collection<Account> accounts;
    private final Collection<MobileDevice> mobileDevices;

    public UUID getIdvIdValue() {
        return aliases.getIdvIdValue();
    }

    public Aliases getAliases() {
        return aliases;
    }

    public boolean hasAlias(final Alias alias) {
        return aliases.contains(alias);
    }

    public PhoneNumbers getPhoneNumbers() {
        return phoneNumbers;
    }

    public PhoneNumbers getMobilePhoneNumbers() {
        return phoneNumbers.getMobileNumbers();
    }

    public Collection<Account> getAccounts() {
        return accounts;
    }

    public Collection<MobileDevice> getMobileDevices() {
        return mobileDevices;
    }

}
