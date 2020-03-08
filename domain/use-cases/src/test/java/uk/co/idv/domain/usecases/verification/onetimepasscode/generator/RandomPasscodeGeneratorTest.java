package uk.co.idv.domain.usecases.verification.onetimepasscode.generator;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RandomPasscodeGeneratorTest {

    private PasscodeGenerator generator = new RandomPasscodeGenerator();

    @Test
    void shouldReturnRandomNumericStringOfSpecifiedLength() {
        final int length = 8;

        final String passcode = generator.generate(length);

        assertThat(passcode).hasSize(length);
        assertThat(passcode).containsOnlyDigits();
    }

}
