package uk.co.idv.domain.usecases.verificationcontext.sequence.policy;

import uk.co.idv.domain.entities.card.account.Account;
import uk.co.idv.domain.entities.mobiledevice.MobileDevice;
import uk.co.idv.domain.entities.phonenumber.PhoneNumbers;
import uk.co.idv.domain.entities.policy.PolicyRequest;

import java.util.Collection;

public interface VerificationSequencesPolicyRequest extends PolicyRequest {

    PhoneNumbers getPhoneNumbers();

    Collection<MobileDevice> getMobileDevices();

    Collection<Account> getAccounts();

}
