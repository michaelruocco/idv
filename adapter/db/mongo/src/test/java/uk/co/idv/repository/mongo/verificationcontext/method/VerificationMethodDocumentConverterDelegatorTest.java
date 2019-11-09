package uk.co.idv.repository.mongo.verificationcontext.method;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.usecases.exception.MethodNotSupportedException;
import uk.co.idv.domain.entities.verificationcontext.method.FakeVerificationMethodEligible;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class VerificationMethodDocumentConverterDelegatorTest {

    private final VerificationMethodDocument document = new FakeVerificationMethodDocument();
    private final VerificationMethod method = new FakeVerificationMethodEligible();

    private final VerificationMethodDocumentConverter converter = mock(VerificationMethodDocumentConverter.class);

    private final VerificationMethodDocumentConverterDelegator delegator = new VerificationMethodDocumentConverterDelegator(Collections.singleton(converter));

    @Test
    void shouldThrowExceptionIfNoConvertersSupportingDocumentMethod() {
        given(converter.supportsMethod(document.getName())).willReturn(false);

        final Throwable error = catchThrowable(() -> delegator.toMethod(document));

        assertThat(error)
                .isInstanceOf(MethodNotSupportedException.class)
                .hasMessage(document.getName());
    }

    @Test
    void shouldThrowExceptionIfNoConvertersSupportingMethod() {
        given(converter.supportsMethod(method.getName())).willReturn(false);

        final Throwable error = catchThrowable(() -> delegator.toDocument(method));

        assertThat(error)
                .isInstanceOf(MethodNotSupportedException.class)
                .hasMessage(method.getName());
    }

    @Test
    void shouldConvertDocumentToMethodIfMethodIsSupported() {
        given(converter.supportsMethod(document.getName())).willReturn(true);
        given(converter.toMethod(document)).willReturn(method);

        final VerificationMethod convertedMethod = delegator.toMethod(document);

        assertThat(convertedMethod).isEqualTo(method);
    }

    @Test
    void shouldConvertMethodtoDocumentIfMethodIsSupported() {
        given(converter.supportsMethod(method.getName())).willReturn(true);
        given(converter.toDocument(method)).willReturn(document);

        final VerificationMethodDocument convertedDocument = delegator.toDocument(method);

        assertThat(convertedDocument).isEqualTo(document);
    }

}
