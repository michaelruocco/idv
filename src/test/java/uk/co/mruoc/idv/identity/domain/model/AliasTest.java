package uk.co.mruoc.idv.identity.domain.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AliasTest {

    private static final String VALUE = "value";

    @Test
    void shouldReturnType() {
        final Alias alias = new FakeAlias(VALUE);

        final String type = alias.getType();

        assertThat(type).isEqualTo("fake-alias");
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

        final boolean isType = alias.isType("fake-alias");

        assertThat(isType).isTrue();
    }

    @Test
    void shouldReturnIsTypeFalseIfTypeDoesNotMatch() {
        final Alias alias = new FakeAlias(VALUE);

        final boolean isType = alias.isType("other-alias-type");

        assertThat(isType).isFalse();
    }

    private static class FakeAlias implements Alias {

        private final String value;

        private FakeAlias(final String value) {
            this.value = value;
        }

        @Override
        public String getType() {
            return "fake-alias";
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
