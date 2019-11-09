package uk.co.idv.repository.mongo.verificationcontext.method.onetimepasscode;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.MobileNumber;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.MobileNumberMother;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class MobileNumberDocumentConverterTest {

    private final MobileNumberDocumentConverter converter = new MobileNumberDocumentConverter();

    @Test
    void shouldPopulateIdOnMobileNumber() {
        final MobileNumberDocument document = MobileNumberDocumentMother.primary();

        final MobileNumber number = converter.toMobileNumber(document);

        assertThat(number.getId()).isEqualTo(UUID.fromString(document.getId()));
    }

    @Test
    void shouldPopulateNumberValueOnMobileNumber() {
        final MobileNumberDocument document = MobileNumberDocumentMother.primary();

        final MobileNumber number = converter.toMobileNumber(document);

        assertThat(number.getNumber()).isEqualTo(document.getNumber());
    }

    @Test
    void shouldPopulateIdOnDocument() {
        final MobileNumber number = MobileNumberMother.primary();

        final MobileNumberDocument document = converter.toDocument(number);

        assertThat(document.getId()).isEqualTo(number.getId().toString());
    }

    @Test
    void shouldPopulateNumberValueOnDocument() {
        final MobileNumber number = MobileNumberMother.primary();

        final MobileNumberDocument document = converter.toDocument(number);

        assertThat(document.getNumber()).isEqualTo(number.getNumber());
    }

}
