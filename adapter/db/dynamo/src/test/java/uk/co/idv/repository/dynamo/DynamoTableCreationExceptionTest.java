package uk.co.idv.repository.dynamo;

import org.junit.jupiter.api.Test;
import uk.co.idv.repository.dynamo.IdvTables.DynamoTableCreationException;

import static org.assertj.core.api.Assertions.assertThat;

class DynamoTableCreationExceptionTest {

    @Test
    void shouldReturnCause() {
        final Throwable cause = new Exception();

        final Throwable error = new DynamoTableCreationException(cause);

        assertThat(error.getCause()).isEqualTo(cause);
    }

}
