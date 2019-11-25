package uk.co.idv.app.config;

import com.mongodb.ConnectionString;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.idv.app.environment.WithEnvironmentVariables.withEnvironmentVariable;

class MongoConnectionStringTest {

    private static final String NAME = "MONGO_CONNECTION_STRING";

    @Test
    void shouldReturnEmptyOptionalIfEnvironmentVariableNotSet() {
        withEnvironmentVariable(NAME, null)
                .execute(() -> {
                            final Optional<ConnectionString> connectionString = MongoConnectionString.load();

                            assertThat(connectionString).isEmpty();
                        }
                );
    }

    @Test
    void shouldReturnMongoConnectionStringIfEnvironmentVariableSet() {
        final String value = "mongodb://host:27017/db";
        withEnvironmentVariable(NAME, value)
                .execute(() -> {
                            final Optional<ConnectionString> connectionString = MongoConnectionString.load();

                            assertThat(connectionString.isPresent()).isTrue();
                            assertThat(connectionString.get().getConnectionString()).isEqualTo(value);
                        }
                );
    }

}
