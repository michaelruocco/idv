package uk.co.idv.domain.usecases.verification.onetimepasscode.sender;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verification.onetimepasscode.OneTimePasscodeDelivery;
import uk.co.idv.domain.entities.verification.onetimepasscode.OneTimePasscodeDeliveryMother;

import static org.assertj.core.api.Assertions.assertThat;

class InMemoryRecordingOneTimePasscodeDeliverySenderTest {

    private final InMemoryRecordingOneTimePasscodeDeliverySender sender = new InMemoryRecordingOneTimePasscodeDeliverySender();

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

}
