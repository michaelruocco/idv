package uk.co.mruoc.idv.mongo.verificationcontext.dao.method;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.domain.exception.MethodNotSupportedException;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.FakeVerificationMethodEligible;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.VerificationMethod;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class VerificationMethodConverterDelegatorTest {

    private final VerificationMethodDocument document = new FakeVerificationMethodDocument();
    private final VerificationMethod method = new FakeVerificationMethodEligible();

    private final VerificationMethodConverter converter = mock(VerificationMethodConverter.class);

    private final VerificationMethodConverterDelegator delegator = new VerificationMethodConverterDelegator(Collections.singleton(converter));

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
