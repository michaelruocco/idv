package uk.co.idv.domain.usecases.identity.data;

import lombok.Builder;
import lombok.Getter;
import uk.co.idv.domain.entities.card.account.Account;
import uk.co.idv.domain.entities.identity.alias.Aliases;
import uk.co.idv.domain.entities.mobiledevice.MobileDevice;
import uk.co.idv.domain.entities.phonenumber.PhoneNumbers;

import java.util.Collection;

@Builder
@Getter
public class IdentityDataResponse {

    private final Aliases aliases;
    private final PhoneNumbers phoneNumbers;
    private final Collection<Account> accounts;
    private final Collection<MobileDevice> mobileDevices;

}
