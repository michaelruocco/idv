package uk.co.idv.onetimepasscode.sender;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.onetimepasscode.OneTimePasscodeDelivery;
import uk.co.idv.domain.entities.onetimepasscode.OneTimePasscodeDeliveryMother;
import uk.co.idv.domain.usecases.util.id.FakeIdGenerator;
import uk.co.idv.domain.usecases.util.id.IdGenerator;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class InMemoryRecordingOneTimePasscodeDeliverySenderTest {

    private final UUID expectedId = UUID.randomUUID();
    private final IdGenerator idGenerator = new FakeIdGenerator(expectedId);

    private final InMemoryRecordingOneTimePasscodeDeliverySender sender = new InMemoryRecordingOneTimePasscodeDeliverySender(idGenerator);

    @Test
    public void shouldRecordAllSentDeliveries() {
        final OneTimePasscodeDelivery delivery1 = OneTimePasscodeDeliveryMother.smsDelivery("11111111");
        final OneTimePasscodeDelivery delivery2 = OneTimePasscodeDeliveryMother.smsDelivery("22222222");

        sender.send(delivery1);
        sender.send(delivery2);

        assertThat(sender.getDeliveries()).containsExactly(
                delivery1,
                delivery2
        );
    }

    @Test
    public void shouldReturnGeneratedId() {
        final OneTimePasscodeDelivery delivery = OneTimePasscodeDeliveryMother.smsDelivery("11111111");

        final String id = sender.send(delivery);

        assertThat(id).isEqualTo(expectedId.toString());
    }

}
