package uk.co.idv.app.config;

import com.mongodb.ConnectionString;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public class MongoConnectionString {

    private static final String NAME = "MONGO_CONNECTION_STRING";

    private MongoConnectionString() {
        // utility class
    }

    public static Optional<ConnectionString> load() {
        final String value = System.getenv(NAME);
        log.info("loaded environment variable {}={}", NAME, value);
        return Optional.ofNullable(value).map(ConnectionString::new);
    }

}
