package uk.co.idv.utils.aws.system;

import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public class SystemPropertyLoader {

    private SystemPropertyLoader() {
        // utility class
    }

    public static Optional<String> load(final String name) {
        final String value = System.getProperty(name);
        log.info("loaded system property {}={}", name, value);
        return Optional.ofNullable(value);
    }
}
