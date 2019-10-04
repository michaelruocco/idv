package uk.co.mruoc.idv.identity.domain.model;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.identity.domain.model.AliasFactory.AliasTypeNotSupportedException;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class AliasFactoryTest {

    private final AliasFactory aliasFactory = new AliasFactory();

    @Test
    void shouldBuildIdvId() {
        final String aliasType = IdvId.TYPE;
        final String aliasValue = UUID.randomUUID().toString();

        final Alias alias = aliasFactory.build(aliasType, aliasValue);

        assertThat(alias).isInstanceOf(IdvId.class);
        assertThat(alias.getType()).isEqualTo(aliasType);
        assertThat(alias.getValue()).isEqualTo(aliasValue);
    }

    @Test
    void shouldBuildCreditCardNumber() {
        final String aliasType = CreditCardNumber.TYPE;
        final String aliasValue = "4929001111111111";

        final Alias alias = aliasFactory.build(aliasType, aliasValue);


        assertThat(alias).isInstanceOf(CreditCardNumber.class);
        assertThat(alias.getType()).isEqualTo(aliasType);
        assertThat(alias.getValue()).isEqualTo(aliasValue);
    }

    @Test
    void shouldBuildDebitCardNumber() {
        final String aliasType = DebitCardNumber.TYPE;
        final String aliasValue = "4929991111111111";

        final Alias alias = aliasFactory.build(aliasType, aliasValue);

        assertThat(alias).isInstanceOf(DebitCardNumber.class);
        assertThat(alias.getType()).isEqualTo(aliasType);
        assertThat(alias.getValue()).isEqualTo(aliasValue);
    }

    @Test
    void shouldThrowExceptionForUnsupportedAliasType() {
        final String aliasType = "not-supported";
        final String aliasValue = "ABC123";

        final Throwable error = catchThrowable(() -> aliasFactory.build(aliasType, aliasValue));

        assertThat(error)
                .isInstanceOf(AliasTypeNotSupportedException.class)
                .hasMessage(aliasType);
    }

}
