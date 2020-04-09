package uk.co.idv.dynamo.table;

import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultProvisionedThroughputTest {

    private final ProvisionedThroughput throughput = new DefaultProvisionedThroughput();

    @Test
    void shouldSetReadCapacityUnitsToOne() {
        assertThat(throughput.getReadCapacityUnits()).isEqualTo(1);
    }

    @Test
    void shouldSetWriteCapacityUnitsToOne() {
        assertThat(throughput.getWriteCapacityUnits()).isEqualTo(1);
    }

}
