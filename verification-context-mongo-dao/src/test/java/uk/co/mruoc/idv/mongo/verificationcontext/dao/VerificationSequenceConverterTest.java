package uk.co.mruoc.idv.mongo.verificationcontext.dao;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.mongo.verificationcontext.dao.method.VerificationMethodDocument;
import uk.co.mruoc.idv.mongo.verificationcontext.dao.method.VerificationMethodsConverter;
import uk.co.mruoc.idv.verificationcontext.domain.model.MultipleMethodSequence;
import uk.co.mruoc.idv.verificationcontext.domain.model.SingleMethodSequence;
import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationSequence;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.FakeVerificationMethodEligible;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.VerificationMethod;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class VerificationSequenceConverterTest {

    private final VerificationMethodsConverter methodsConverter = mock(VerificationMethodsConverter.class);

    private final VerificationSequenceConverter sequenceConverter = VerificationSequenceConverter.builder()
            .methodsConverter(methodsConverter)
            .build();

    @Test
    void shouldConvertToSingleMethodSequenceIfOnlyOneMethod() {
        final VerificationSequenceDocument document = new VerificationSequenceDocument();
        final VerificationMethod method = mock(VerificationMethod.class);
        given(methodsConverter.toMethods(document.getMethods())).willReturn(Collections.singleton(method));

        final VerificationSequence sequence = sequenceConverter.toSequence(document);

        assertThat(sequence).isInstanceOf(SingleMethodSequence.class);
        assertThat(sequence.getMethods()).containsExactly(method);
    }

    @Test
    void shouldConvertToMultipleMethodSequenceIfMoreThanOneMethod() {
        final VerificationSequenceDocument document = new VerificationSequenceDocument();
        final VerificationMethod method1 = mock(VerificationMethod.class);
        final VerificationMethod method2 = mock(VerificationMethod.class);
        given(methodsConverter.toMethods(document.getMethods())).willReturn(Arrays.asList(method1, method2));

        final VerificationSequence sequence = sequenceConverter.toSequence(document);

        assertThat(sequence).isInstanceOf(MultipleMethodSequence.class);
        assertThat(sequence.getMethods()).containsExactly(method1, method2);
    }

    @Test
    void shouldConvertSequenceNameToDocument() {
        final VerificationSequence sequence = new SingleMethodSequence(new FakeVerificationMethodEligible("fake-method"));

        final VerificationSequenceDocument sequenceDocument = sequenceConverter.toDocument(sequence);

        assertThat(sequenceDocument.getName()).isEqualTo(sequence.getName());
    }

    @Test
    void shouldConvertMethodsToDocument() {
        final VerificationSequence sequence = new SingleMethodSequence(new FakeVerificationMethodEligible("fake-method"));
        final Collection<VerificationMethodDocument> methodDocuments = Collections.singleton(new VerificationMethodDocument());
        given(methodsConverter.toDocuments(sequence.getMethods())).willReturn(methodDocuments);

        final VerificationSequenceDocument sequenceDocument = sequenceConverter.toDocument(sequence);

        assertThat(sequenceDocument.getMethods()).isEqualTo(methodDocuments);
    }

}
