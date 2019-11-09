package uk.co.idv.repository.mongo.activity;

import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.domain.model.activity.Activity;
import uk.co.mruoc.idv.domain.model.activity.FakeActivity;
import uk.co.mruoc.idv.domain.model.activity.FakeOnlinePurchase;
import uk.co.mruoc.idv.domain.model.activity.OnlinePurchase;

import javax.money.MonetaryAmount;
import java.math.BigDecimal;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class OnlinePurchaseConverterTest {

    private final MonetaryAmountDocumentConverter amountConverter = mock(MonetaryAmountDocumentConverter.class);

    private final OnlinePurchaseDocumentConverter converter = new OnlinePurchaseDocumentConverter(amountConverter);

    @Test
    void shouldOnlySupportOnlinePurchaseActivityName() {
        assertThat(converter.supportsActivity(OnlinePurchase.NAME)).isTrue();
        assertThat(converter.supportsActivity("other-activity")).isFalse();
    }

    @Test
    void shouldThrowExceptionIfNotOnlinePurchaseDocument() {
        final ActivityDocument document = ActivityDocumentMother.fake();

        final Throwable error = catchThrowable(() -> converter.toActivity(document));

        assertThat(error).isInstanceOf(ClassCastException.class);
    }

    @Test
    void shouldThrowExceptionIfNotOnlinePurchaseActivity() {
        final Activity activity = new FakeActivity();

        final Throwable error = catchThrowable(() -> converter.toDocument(activity));

        assertThat(error).isInstanceOf(ClassCastException.class);
    }

    @Test
    void shouldConvertToOnlinePurchaseActivity() {
        final OnlinePurchaseDocument document = ActivityDocumentMother.onlinePurchase();

        final Activity activity = converter.toActivity(document);

        assertThat(activity).isInstanceOf(OnlinePurchase.class);
    }

    @Test
    void shouldConvertTimestampToActivity() {
        final OnlinePurchaseDocument document = ActivityDocumentMother.onlinePurchase();

        final OnlinePurchase onlinePurchase = (OnlinePurchase) converter.toActivity(document);

        assertThat(onlinePurchase.getTimestamp()).isEqualTo(Instant.parse(document.getTimestamp()));
    }

    @Test
    void shouldConvertMerchantNameToActivity() {
        final OnlinePurchaseDocument document = ActivityDocumentMother.onlinePurchase();

        final OnlinePurchase onlinePurchase = (OnlinePurchase) converter.toActivity(document);

        assertThat(onlinePurchase.getMerchantName()).isEqualTo(document.getMerchantName());
    }

    @Test
    void shouldConvertReferenceToActivity() {
        final OnlinePurchaseDocument document = ActivityDocumentMother.onlinePurchase();

        final OnlinePurchase onlinePurchase = (OnlinePurchase) converter.toActivity(document);

        assertThat(onlinePurchase.getReference()).isEqualTo(document.getReference());
    }

    @Test
    void shouldConvertCostToActivity() {
        final MonetaryAmount expectedCost = Money.of(BigDecimal.ONE, "GBP");
        final OnlinePurchaseDocument document = ActivityDocumentMother.onlinePurchase();
        given(amountConverter.toMonetaryAmount(document.getCost())).willReturn(expectedCost);

        final OnlinePurchase onlinePurchase = (OnlinePurchase) converter.toActivity(document);

        assertThat(onlinePurchase.getCost()).isEqualTo(expectedCost);
    }

    @Test
    void shouldConvertToOnlinePurchaseDocument() {
        final OnlinePurchase onlinePurchase = new FakeOnlinePurchase();

        final ActivityDocument document = converter.toDocument(onlinePurchase);

        assertThat(document).isInstanceOf(OnlinePurchaseDocument.class);
    }

    @Test
    void shouldConvertNameToDocument() {
        final OnlinePurchase onlinePurchase = new FakeOnlinePurchase();

        final OnlinePurchaseDocument document = (OnlinePurchaseDocument) converter.toDocument(onlinePurchase);

        assertThat(document.getName()).isEqualTo(onlinePurchase.getName());
    }

    @Test
    void shouldConvertTimestampToDocument() {
        final OnlinePurchase onlinePurchase = new FakeOnlinePurchase();

        final OnlinePurchaseDocument document = (OnlinePurchaseDocument) converter.toDocument(onlinePurchase);

        assertThat(document.getTimestamp()).isEqualTo(onlinePurchase.getTimestamp().toString());
    }

    @Test
    void shouldConvertMerchantNameToDocument() {
        final OnlinePurchase onlinePurchase = new FakeOnlinePurchase();

        final OnlinePurchaseDocument document = (OnlinePurchaseDocument) converter.toDocument(onlinePurchase);

        assertThat(document.getMerchantName()).isEqualTo(onlinePurchase.getMerchantName());
    }

    @Test
    void shouldConvertReferenceToDocument() {
        final OnlinePurchase onlinePurchase = new FakeOnlinePurchase();

        final OnlinePurchaseDocument document = (OnlinePurchaseDocument) converter.toDocument(onlinePurchase);

        assertThat(document.getReference()).isEqualTo(onlinePurchase.getReference());
    }

    @Test
    void shouldConvertCostToDocument() {
        final OnlinePurchase onlinePurchase = new FakeOnlinePurchase();
        final MonetaryAmountDocument expectedCostDocument = MonetaryAmountDocumentMother.build();
        given(amountConverter.toDocument(onlinePurchase.getCost())).willReturn(expectedCostDocument);

        final OnlinePurchaseDocument document = (OnlinePurchaseDocument) converter.toDocument(onlinePurchase);

        assertThat(document.getCost()).isEqualTo(expectedCostDocument);
    }

}
