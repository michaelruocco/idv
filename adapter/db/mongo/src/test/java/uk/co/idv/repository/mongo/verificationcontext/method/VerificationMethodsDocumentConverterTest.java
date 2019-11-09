package uk.co.idv.repository.mongo.verificationcontext.method;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;

import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class VerificationMethodsDocumentConverterTest {

    private final VerificationMethodDocument document1 = mock(VerificationMethodDocument.class);
    private final VerificationMethodDocument document2 = mock(VerificationMethodDocument.class);

    private final VerificationMethod method1 = mock(VerificationMethod.class);
    private final VerificationMethod method2 = mock(VerificationMethod.class);

    private final VerificationMethodDocumentConverterDelegator methodConverter = mock(VerificationMethodDocumentConverterDelegator.class);

    private final VerificationMethodsDocumentConverter methodsConverter = new VerificationMethodsDocumentConverter(methodConverter);

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
