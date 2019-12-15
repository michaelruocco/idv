package uk.co.idv.repository.dynamo.identity;

import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import uk.co.idv.domain.entities.identity.Identity;
import uk.co.idv.domain.entities.identity.alias.Alias;
import uk.co.idv.domain.entities.identity.alias.Aliases;
import uk.co.idv.domain.entities.identity.alias.AliasesMother;
import uk.co.idv.repository.dynamo.identity.alias.AliasConverter;
import uk.co.idv.repository.dynamo.identity.alias.AliasMappingDocument;
import uk.co.idv.repository.dynamo.identity.alias.AliasMappingDocumentConverter;
import uk.co.idv.repository.dynamo.identity.alias.AliasMappingRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class DynamoIdentityDaoTest {

    private final AliasMappingDocumentConverter documentConverter = mock(AliasMappingDocumentConverter.class);
    private final AliasConverter aliasConverter = mock(AliasConverter.class);
    private final AliasMappingRepository repository = mock(AliasMappingRepository.class);

    private final DynamoIdentityDao dao = DynamoIdentityDao.builder()
            .documentConverter(documentConverter)
            .aliasConverter(aliasConverter)
            .repository(repository)
            .build();

    @Test
    void shouldSaveNewAliasMappingDocuments() {
        final Identity identity = new Identity(AliasesMother.aliases());
        final AliasMappingDocument document = AliasMappingDocumentMother.build();
        given(documentConverter.toDocuments(identity)).willReturn(Collections.singleton(document));
        given(repository.findByIdvId(identity.getIdvIdValue().toString())).willReturn(Collections.emptyList());

        dao.save(identity);

        final ArgumentCaptor<Iterable<AliasMappingDocument>> captor = ArgumentCaptor.forClass(Iterable.class);
        verify(repository).saveAll(captor.capture());
        assertThat(captor.getValue()).containsExactly(document);
    }

    @Test
    void shouldSaveNewAliasMappingDocumentsIfExistingButUnchanged() {
        final Identity identity = new Identity(AliasesMother.aliases());
        final AliasMappingDocument document = AliasMappingDocumentMother.build();
        given(documentConverter.toDocuments(identity)).willReturn(Collections.singleton(document));
        given(repository.findByIdvId(identity.getIdvIdValue().toString())).willReturn(Collections.singleton(document));

        dao.save(identity);

        final ArgumentCaptor<Iterable<AliasMappingDocument>> captor = ArgumentCaptor.forClass(Iterable.class);
        verify(repository).saveAll(captor.capture());
        assertThat(captor.getValue()).containsExactly(document);
    }

    @Test
    void shouldDeleteAliasMappingDocumentsIfAliasHasBeenRemoved() {
        final Identity identity = new Identity(AliasesMother.aliases());
        final AliasMappingDocument newDocument = AliasMappingDocumentMother.build(0);
        given(documentConverter.toDocuments(identity)).willReturn(Collections.singleton(newDocument));
        final AliasMappingDocument existingDocument = AliasMappingDocumentMother.build(1);
        given(repository.findByIdvId(identity.getIdvIdValue().toString())).willReturn(Collections.singleton(existingDocument));

        dao.save(identity);

        final ArgumentCaptor<Iterable<AliasMappingDocument>> captor = ArgumentCaptor.forClass(Iterable.class);
        verify(repository).deleteAll(captor.capture());
        assertThat(captor.getValue()).containsExactly(existingDocument);
    }

    @Test
    void shouldNotDeleteAnyExistingDocumentsIfIdvIdDoesNotExist() {
        final Alias alias = AliasesMother.idvId();
        final Identity identity = new Identity(Aliases.with(alias));
        given(repository.findByIdvId(alias.getValue())).willThrow(ResourceNotFoundException.class);

        dao.save(identity);

        final ArgumentCaptor<Iterable<AliasMappingDocument>> captor = ArgumentCaptor.forClass(Iterable.class);
        verify(repository).deleteAll(captor.capture());
        assertThat(captor.getValue()).isEmpty();
    }

    @Test
    void shouldReturnEmptyOptionalIfIdvIdAliasDoesNotExist() {
        final Alias alias = AliasesMother.idvId();

        final Optional<Identity> identity = dao.load(alias);

        assertThat(identity).isEmpty();
    }

    @Test
    void shouldReturnIdentityByIdvIdAlias() {
        final Alias alias = AliasesMother.idvId();
        final Collection<AliasMappingDocument> documents = Collections.singleton(AliasMappingDocumentMother.build());
        given(repository.findByIdvId(alias.getValue())).willReturn(documents);
        final Identity expectedIdentity = new Identity(Aliases.with(alias));
        given(documentConverter.toIdentity(documents)).willReturn(expectedIdentity);

        final Optional<Identity> identity = dao.load(alias);

        assertThat(identity).contains(expectedIdentity);
    }

    @Test
    void shouldReturnEmptyOptionalIfOtherAliasDoesNotExist() {
        final Alias alias = AliasesMother.creditCardNumber();

        final Optional<Identity> identity = dao.load(alias);

        assertThat(identity).isEmpty();
    }

    @Test
    void shouldReturnIdentityByOtherAlias() {
        final Alias alias = AliasesMother.creditCardNumber();
        final String aliasString = "alias-string";
        given(aliasConverter.toString(alias)).willReturn(aliasString);
        final AliasMappingDocument aliasDocument = AliasMappingDocumentMother.build();
        given(repository.findById(aliasString)).willReturn(Optional.of(aliasDocument));
        final Collection<AliasMappingDocument> documents = Collections.singleton(AliasMappingDocumentMother.build(1));
        given(repository.findByIdvId(aliasDocument.getIdvId())).willReturn(documents);
        final Identity expectedIdentity = new Identity(Aliases.with(alias));
        given(documentConverter.toIdentity(documents)).willReturn(expectedIdentity);

        final Optional<Identity> identity = dao.load(alias);

        assertThat(identity).contains(expectedIdentity);
    }

}
