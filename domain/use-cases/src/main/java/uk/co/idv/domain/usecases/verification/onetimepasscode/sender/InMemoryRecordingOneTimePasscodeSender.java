package uk.co.idv.domain.usecases.verification.onetimepasscode.sender;

import lombok.extern.slf4j.Slf4j;
import uk.co.idv.domain.entities.verification.onetimepasscode.OneTimePasscodeDelivery;

import java.util.ArrayList;
import java.util.Collection;

@Slf4j
public class InMemoryRecordingOneTimePasscodeSender implements OneTimePasscodeSender {

    private final Collection<OneTimePasscodeDelivery> deliveries = new ArrayList<>();

    @Override
    public void send(OneTimePasscodeDelivery delivery) {
        log.info("recording sent delivery {} in memory", delivery);
        deliveries.add(delivery);
    }

    public Collection<OneTimePasscodeDelivery> getDeliveries() {
        return deliveries;
    }

}
