package uk.co.idv.repository.mongo.verificationcontext;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.verificationcontext.domain.model.FakeVerificationContext;
import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationContext;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class MongoVerificationContextDaoTest {

    private final VerificationContext context = new FakeVerificationContext();
    private final VerificationContextDocument document = mock(VerificationContextDocument.class);

    private final VerificationContextRepository repository = mock(VerificationContextRepository.class);
    private final VerificationContextDocumentConverter converter = mock(VerificationContextDocumentConverter.class);

    private final MongoVerificationContextDao dao = MongoVerificationContextDao.builder()
            .repository(repository)
            .converter(converter)
            .build();

    @Test
    void shouldSaveConvertedContext() {
        given(converter.toDocument(context)).willReturn(document);

        dao.save(context);

        verify(repository).save(document);
    }

    @Test
    void shouldLoadContext() {
        given(repository.findById(context.getId().toString())).willReturn(Optional.of(document));
        given(converter.toContext(document)).willReturn(context);

        final Optional<VerificationContext> loadedContext = dao.load(context.getId());

        assertThat(loadedContext).contains(context);
    }

    @Test
    void shouldLoadEmptyOptionalIfNotFound() {
        given(repository.findById(context.getId().toString())).willReturn(Optional.empty());

        final Optional<VerificationContext> loadedContext = dao.load(context.getId());

        assertThat(loadedContext).isEmpty();
    }

}
