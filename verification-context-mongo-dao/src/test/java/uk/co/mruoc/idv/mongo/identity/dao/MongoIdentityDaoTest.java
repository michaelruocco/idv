package uk.co.mruoc.idv.mongo.identity.dao;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.identity.domain.model.Alias;
import uk.co.mruoc.idv.identity.domain.model.Aliases;
import uk.co.mruoc.idv.identity.domain.model.AliasesMother;
import uk.co.mruoc.idv.identity.domain.model.Identity;
import uk.co.mruoc.idv.mongo.identity.dao.MongoIdentityDao.MultipleIdentitiesFoundException;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class MongoIdentityDaoTest {

    private final IdentityRepository repository = mock(IdentityRepository.class);
    private final IdentityDocumentConverter converter = mock(IdentityDocumentConverter.class);

    private final MongoIdentityDao dao = MongoIdentityDao.builder()
            .repository(repository)
            .converter(converter)
            .build();

    @Test
    void shouldSaveConvertedDocument() {
        final Identity identity = new Identity(AliasesMother.aliases());
        final IdentityDocument document = new IdentityDocument();
        given(converter.toDocument(identity)).willReturn(document);

        dao.save(identity);

        verify(repository).insert(document);
    }

    @Test
    void shouldLoadIdentityByAliasTypeAndValue() {
        final Alias alias = AliasesMother.creditCardNumber();
        final Identity expectedIdentity = new Identity(Aliases.with(alias));
        final IdentityDocument document = new IdentityDocument();
        final Collection<IdentityDocument> singleDocument = Collections.singleton(document);
        given(repository.findByAliasesTypeAndAliasesValue(alias.getType(), alias.getValue())).willReturn(singleDocument);
        given(converter.toIdentity(document)).willReturn(expectedIdentity);

        Optional<Identity> identity = dao.load(alias);

        assertThat(identity).contains(expectedIdentity);
    }

    @Test
    void shouldReturnEmptyOptionalIfIdentityNotFound() {
        final Alias alias = AliasesMother.creditCardNumber();
        final Collection<IdentityDocument> none = Collections.emptyList();
        given(repository.findByAliasesTypeAndAliasesValue(alias.getType(), alias.getValue())).willReturn(none);

        Optional<Identity> identity = dao.load(alias);

        assertThat(identity).isEmpty();
    }

    @Test
    void shouldThrowExceptionIfMoreThanOneIdentityFound() {
        final Alias alias = AliasesMother.creditCardNumber();
        final Collection<IdentityDocument> documents = Arrays.asList(
                mock(IdentityDocument.class),
                mock(IdentityDocument.class)
        );
        given(repository.findByAliasesTypeAndAliasesValue(alias.getType(), alias.getValue())).willReturn(documents);

        final Throwable error = catchThrowable(() -> dao.load(alias));

        assertThat(error).isInstanceOf(MultipleIdentitiesFoundException.class);
    }

    @Test
    void shouldPopulateAliasOnExceptionWhenMultipleIdentitiesFound() {
        final Alias alias = AliasesMother.creditCardNumber();
        final Collection<IdentityDocument> documents = Arrays.asList(
                mock(IdentityDocument.class),
                mock(IdentityDocument.class)
        );
        given(repository.findByAliasesTypeAndAliasesValue(alias.getType(), alias.getValue())).willReturn(documents);

        final Throwable error = catchThrowable(() -> dao.load(alias));

        final MultipleIdentitiesFoundException exception = (MultipleIdentitiesFoundException) error;
        assertThat(exception.getAlias()).isEqualTo(alias);
    }

}
