package uk.co.idv.domain.entities.verificationcontext.method;

import uk.co.idv.domain.entities.card.account.Account;
import uk.co.idv.domain.entities.mobiledevice.MobileDevice;
import uk.co.idv.domain.entities.phonenumber.PhoneNumbers;

import java.util.Collection;

public interface VerificationMethodPolicyRequest {

    PhoneNumbers getPhoneNumbers();

    Collection<MobileDevice> getMobileDevices();

    Collection<Account> getAccounts();

}
