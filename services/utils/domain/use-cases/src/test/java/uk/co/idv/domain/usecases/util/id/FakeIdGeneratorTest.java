package uk.co.idv.domain.usecases.util.id;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class FakeIdGeneratorTest {

    private final UUID id = UUID.randomUUID();

    private final IdGenerator generator = new FakeIdGenerator(id);

    @Test
    void shouldGenerateFixedId() {
        assertThat(generator.generate()).isEqualTo(id);
    }

}
