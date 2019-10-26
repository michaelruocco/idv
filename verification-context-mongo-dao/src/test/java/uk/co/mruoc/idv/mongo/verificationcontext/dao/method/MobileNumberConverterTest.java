package uk.co.mruoc.idv.mongo.verificationcontext.dao.method;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.MobileNumber;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.MobileNumberMother;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class MobileNumberConverterTest {

    private final MobileNumberConverter converter = new MobileNumberConverter();

    @Test
    void shouldPopulateIdOnMobileNumber() {
        final MobileNumberDocument document = MobileNumberDocumentMother.build();

        final MobileNumber number = converter.toMobileNumber(document);

        assertThat(number.getId()).isEqualTo(UUID.fromString(document.getId()));
    }

    @Test
    void shouldPopulateNumberValueOnMobileNumber() {
        final MobileNumberDocument document = MobileNumberDocumentMother.build();

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
