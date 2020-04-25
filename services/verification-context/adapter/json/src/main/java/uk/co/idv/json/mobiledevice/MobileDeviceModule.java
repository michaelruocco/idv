package uk.co.idv.json.mobiledevice;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import uk.co.idv.domain.entities.mobiledevice.MobileDevice;

import java.util.Collections;

public class MobileDeviceModule extends SimpleModule {

    public MobileDeviceModule() {
        super("mobile-device-module", Version.unknownVersion());

        setMixInAnnotation(MobileDevice.class, MobileDeviceMixin.class);

        addDeserializer(MobileDevice.class, new MobileDeviceDeserializer());
    }

    @Override
    public Iterable<? extends Module> getDependencies() {
        return Collections.singleton(new JavaTimeModule());
    }

}
