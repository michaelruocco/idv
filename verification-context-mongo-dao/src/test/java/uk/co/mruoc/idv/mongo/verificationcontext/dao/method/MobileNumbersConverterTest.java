package uk.co.mruoc.idv.mongo.verificationcontext.dao.method;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.MobileNumber;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.MobileNumberMother;

import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class MobileNumbersConverterTest {

    private final MobileNumberDocument document1 = MobileNumberDocumentMother.primary();
    private final MobileNumberDocument document2 = MobileNumberDocumentMother.secondary();

    private final MobileNumber mobileNumber1 = MobileNumberMother.primary();
    private final MobileNumber mobileNumber2 = MobileNumberMother.secondary();

    private final MobileNumberConverter mobileNumberConverter = mock(MobileNumberConverter.class);

    private final MobileNumbersConverter mobileNumbersConverter = new MobileNumbersConverter(mobileNumberConverter);

    @Test
    void shouldConvertMultipleDocumentsToMobileNumbers() {
        given(mobileNumberConverter.toMobileNumber(document1)).willReturn(mobileNumber1);
        given(mobileNumberConverter.toMobileNumber(document2)).willReturn(mobileNumber2);

        final Collection<MobileNumber> mobileNumbers = mobileNumbersConverter.toMobileNumbers(Arrays.asList(document1, document2));

        assertThat(mobileNumbers).containsExactly(mobileNumber1, mobileNumber2);
    }

    @Test
    void shouldConvertMultipleCardNumbersToDocuments() {
        given(mobileNumberConverter.toDocument(mobileNumber1)).willReturn(document1);
        given(mobileNumberConverter.toDocument(mobileNumber2)).willReturn(document2);

        final Collection<MobileNumberDocument> documents = mobileNumbersConverter.toDocuments(Arrays.asList(mobileNumber1, mobileNumber2));

        assertThat(documents).containsExactly(document1, document2);
    }

}
