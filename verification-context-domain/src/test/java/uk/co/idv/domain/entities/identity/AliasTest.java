package uk.co.idv.domain.entities.identity;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AliasTest {

    private static final String VALUE = "value";

    @Test
    void shouldReturnType() {
        final Alias alias = new FakeAlias(VALUE);

        final String type = alias.getType();

        assertThat(type).isEqualTo("fake-providedAlias");
    }

    @Test
    void isNotCardNumber() {
        final Alias alias = new FakeAlias(VALUE);

        final boolean cardNumber = alias.isCardNumber();

        assertThat(cardNumber).isFalse();
    }

    @Test
    void shouldReturnValue() {
        final Alias alias = new FakeAlias(VALUE);

        assertThat(alias.getValue()).isEqualTo(VALUE);
    }

    @Test
    void shouldReturnIsTypeTrueIfTypeMatches() {
        final Alias alias = new FakeAlias(VALUE);

        final boolean isType = alias.isType("fake-providedAlias");

        assertThat(isType).isTrue();
    }

    @Test
    void shouldReturnIsTypeFalseIfTypeDoesNotMatch() {
        final Alias alias = new FakeAlias(VALUE);

        final boolean isType = alias.isType("other-providedAlias-type");

        assertThat(isType).isFalse();
    }

    private static class FakeAlias implements Alias {

        private final String value;

        private FakeAlias(final String value) {
            this.value = value;
        }

        @Override
        public String getType() {
            return "fake-providedAlias";
        }

        @Override
        public String getValue() {
            return value;
        }

        @Override
        public boolean isCardNumber() {
            return false;
        }

    }

}
