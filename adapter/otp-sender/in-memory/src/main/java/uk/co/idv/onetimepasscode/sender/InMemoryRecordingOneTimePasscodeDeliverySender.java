package uk.co.idv.onetimepasscode.sender;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.domain.entities.onetimepasscode.OneTimePasscodeDelivery;
import uk.co.idv.domain.usecases.onetimepasscode.OneTimePasscodeDeliverySender;
import uk.co.idv.domain.usecases.util.IdGenerator;

import java.util.ArrayList;
import java.util.Collection;

@RequiredArgsConstructor
@Slf4j
public class InMemoryRecordingOneTimePasscodeDeliverySender implements OneTimePasscodeDeliverySender {

    private final Collection<OneTimePasscodeDelivery> deliveries = new ArrayList<>();

    private final IdGenerator idGenerator;

    @Override
    public String send(OneTimePasscodeDelivery delivery) {
        log.info("recording sent delivery {} in memory", delivery);
        deliveries.add(delivery);
        return idGenerator.generate().toString();
    }

    public Collection<OneTimePasscodeDelivery> getDeliveries() {
        return deliveries;
    }

}
