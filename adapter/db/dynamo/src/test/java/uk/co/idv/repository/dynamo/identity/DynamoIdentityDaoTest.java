package uk.co.idv.repository.dynamo.identity;

class DynamoIdentityDaoTest {

    /*private final AliasMappingItemConverter documentConverter = mock(AliasMappingItemConverter.class);
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
        final AliasMappingDocument document = AliasMappingItemMother.build();
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
        final AliasMappingDocument document = AliasMappingItemMother.build();
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
        final AliasMappingDocument newDocument = AliasMappingItemMother.build(0);
        given(documentConverter.toDocuments(identity)).willReturn(Collections.singleton(newDocument));
        final AliasMappingDocument existingDocument = AliasMappingItemMother.build(1);
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
        final Collection<AliasMappingDocument> documents = Collections.singleton(AliasMappingItemMother.build());
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
        final AliasMappingDocument aliasDocument = AliasMappingItemMother.build();
        given(repository.findById(aliasString)).willReturn(Optional.of(aliasDocument));
        final Collection<AliasMappingDocument> documents = Collections.singleton(AliasMappingItemMother.build(1));
        given(repository.findByIdvId(aliasDocument.getIdvId())).willReturn(documents);
        final Identity expectedIdentity = new Identity(Aliases.with(alias));
        given(documentConverter.toIdentity(documents)).willReturn(expectedIdentity);

        final Optional<Identity> identity = dao.load(alias);

        assertThat(identity).contains(expectedIdentity);
    }*/

}
