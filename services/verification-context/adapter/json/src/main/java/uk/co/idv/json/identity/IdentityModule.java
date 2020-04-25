package uk.co.idv.json.identity;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.domain.entities.identity.Identity;
import uk.co.idv.json.account.AccountModule;
import uk.co.idv.json.identity.alias.AliasModule;
import uk.co.idv.json.mobiledevice.MobileDeviceModule;
import uk.co.idv.json.phonenumber.PhoneNumberModule;

import java.util.Arrays;

public class IdentityModule extends SimpleModule {

    public IdentityModule() {
        super("identity-module", Version.unknownVersion());

        setMixInAnnotation(Identity.class, IdentityMixin.class);

        addDeserializer(Identity.class, new IdentityDeserializer());
    }

    @Override
    public Iterable<? extends Module> getDependencies() {
        return Arrays.asList(
                new AliasModule(),
                new PhoneNumberModule(),
                new AccountModule(),
                new MobileDeviceModule()
        );
    }

}
