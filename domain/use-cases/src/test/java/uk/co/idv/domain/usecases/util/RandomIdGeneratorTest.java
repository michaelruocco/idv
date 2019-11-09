package uk.co.idv.domain.usecases.util;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class RandomIdGeneratorTest {

    private final IdGenerator generator = new RandomIdGenerator();

    @Test
    void shouldReturnRandomId() {
        final UUID id1 = generator.generate();
        final UUID id2 = generator.generate();

        assertThat(id1).isNotEqualTo(id2);
    }

}
