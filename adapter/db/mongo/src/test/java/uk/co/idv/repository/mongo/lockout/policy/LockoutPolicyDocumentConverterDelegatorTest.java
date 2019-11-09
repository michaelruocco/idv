package uk.co.idv.repository.mongo.lockout.policy;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.lockout.domain.model.DefaultLockoutPolicyParameters;
import uk.co.mruoc.idv.lockout.domain.model.LockoutPolicyParameters;
import uk.co.mruoc.idv.lockout.domain.model.LockoutStateCalculatorFactory.LockoutTypeNotSupportedException;
import uk.co.mruoc.idv.lockout.domain.service.LockoutPolicyParametersMother;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class LockoutPolicyDocumentConverterDelegatorTest {

    private final LockoutPolicyDocumentConverter converter = mock(LockoutPolicyDocumentConverter.class);
    private final LockoutPolicyDocumentConverterDelegator delegator = new LockoutPolicyDocumentConverterDelegator(converter);

    @Test
    void shouldThrowExceptionIfNoConverterSupportingLockoutPolicyDocument() {
        final LockoutPolicyDocument document = new LockoutPolicyDocument();

        final Throwable error = catchThrowable(() -> delegator.toParameters(document));

        assertThat(error).isInstanceOf(LockoutTypeNotSupportedException.class);
        assertThat(error.getMessage()).isEqualTo(document.getLockoutType());
    }

    @Test
    void shouldThrowExceptionIfNoConverterSupportingLockoutPolicyParameters() {
        final DefaultLockoutPolicyParameters parameters = mock(DefaultLockoutPolicyParameters.class);
        given(parameters.getLockoutType()).willReturn("not-supported");

        final Throwable error = catchThrowable(() -> delegator.toDocument(parameters));

        assertThat(error).isInstanceOf(LockoutTypeNotSupportedException.class);
        assertThat(error.getMessage()).isEqualTo(parameters.getLockoutType());
    }

    @Test
    void shouldConvertLockoutPolicyDocument() {
        final LockoutPolicyParameters expectedParameters = LockoutPolicyParametersMother.maxAttempts();
        final LockoutPolicyDocument document = new LockoutPolicyDocument();
        given(converter.supportsType(document.getLockoutType())).willReturn(true);
        given(converter.toParameters(document)).willReturn(expectedParameters);

        final LockoutPolicyParameters parameters = delegator.toParameters(document);

        assertThat(parameters).isEqualTo(expectedParameters);
    }

    @Test
    void shouldConvertLockoutPolicyParameters() {
        final DefaultLockoutPolicyParameters parameters = LockoutPolicyParametersMother.maxAttempts();
        final LockoutPolicyDocument expectedDocument = new LockoutPolicyDocument();
        given(converter.supportsType(parameters.getLockoutType())).willReturn(true);
        given(converter.toDocument(parameters)).willReturn(expectedDocument);

        final LockoutPolicyDocument document = delegator.toDocument(parameters);

        assertThat(document).isEqualTo(expectedDocument);
    }

}
