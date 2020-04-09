package uk.co.idv.dynamo.table;

import org.junit.jupiter.api.Test;
import uk.co.idv.dynamo.table.DynamoTableCreator.DynamoTableCreationException;

import static org.assertj.core.api.Assertions.assertThat;

class DynamoTableCreationExceptionTest {

    @Test
    void shouldReturnCause() {
        final Throwable cause = new Exception();

        final Throwable error = new DynamoTableCreationException(cause);

        assertThat(error.getCause()).isEqualTo(cause);
    }

}
