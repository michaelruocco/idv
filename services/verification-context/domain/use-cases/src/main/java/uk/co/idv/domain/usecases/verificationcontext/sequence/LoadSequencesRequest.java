package uk.co.idv.domain.usecases.verificationcontext.sequence;

import lombok.Builder;
import lombok.Getter;
import uk.co.idv.domain.entities.activity.Activity;
import uk.co.idv.domain.entities.card.account.Account;
import uk.co.idv.domain.entities.channel.Channel;
import uk.co.idv.domain.entities.identity.alias.Alias;
import uk.co.idv.domain.entities.identity.Identity;
import uk.co.idv.domain.entities.mobiledevice.MobileDevice;
import uk.co.idv.domain.entities.phonenumber.PhoneNumbers;

import java.util.Collection;

@Builder
@Getter
public class LoadSequencesRequest {

    private final Channel channel;
    private final Activity activity;
    private final Alias providedAlias;
    private final Identity identity;

    public Collection<MobileDevice> getMobileDevices() {
        return identity.getMobileDevices();
    }

    public Collection<Account> getAccounts() {
        return identity.getAccounts();
    }

    public PhoneNumbers getPhoneNumbers() {
        return identity.getPhoneNumbers();
    }

}
