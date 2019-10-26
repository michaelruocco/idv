package uk.co.mruoc.idv.mongo.verificationcontext.dao.method;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.VerificationMethod;

import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class VerificationMethodsConverterTest {

    private final VerificationMethodDocument document1 = new VerificationMethodDocument();
    private final VerificationMethodDocument document2 = new OneTimePasscodeSmsDocument();

    private final VerificationMethod method1 = mock(VerificationMethod.class);
    private final VerificationMethod method2 = mock(VerificationMethod.class);

    private final VerificationMethodConverterDelegator methodConverter = mock(VerificationMethodConverterDelegator.class);

    private final VerificationMethodsConverter methodsConverter = new VerificationMethodsConverter(methodConverter);

    @Test
    void shouldConvertMultipleDocumentsToMobileNumbers() {
        given(methodConverter.toMethod(document1)).willReturn(method1);
        given(methodConverter.toMethod(document2)).willReturn(method2);

        final Collection<VerificationMethod> methods = methodsConverter.toMethods(Arrays.asList(document1, document2));

        assertThat(methods).containsExactly(method1, method2);
    }

    @Test
    void shouldConvertMultipleCardNumbersToDocuments() {
        given(methodConverter.toDocument(method1)).willReturn(document1);
        given(methodConverter.toDocument(method2)).willReturn(document2);

        final Collection<VerificationMethodDocument> documents = methodsConverter.toDocuments(Arrays.asList(method1, method2));

        assertThat(documents).containsExactly(document1, document2);
    }

}
