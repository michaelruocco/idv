package uk.co.idv.repository.mongo.verificationcontext;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.activity.ActivityMother;
import uk.co.idv.domain.entities.channel.ChannelMother;
import uk.co.idv.repository.mongo.activity.ActivityConverterDelegator;
import uk.co.idv.repository.mongo.activity.ActivityDocument;
import uk.co.idv.repository.mongo.channel.ChannelDocument;
import uk.co.idv.repository.mongo.channel.ChannelDocumentConverterDelegator;
import uk.co.idv.repository.mongo.identity.IdentityDocument;
import uk.co.idv.repository.mongo.identity.IdentityDocumentConverter;
import uk.co.idv.repository.mongo.identity.alias.AliasDocument;
import uk.co.idv.repository.mongo.identity.alias.AliasDocumentConverter;
import uk.co.idv.domain.entities.activity.Activity;
import uk.co.idv.domain.entities.channel.Channel;
import uk.co.idv.domain.entities.identity.alias.Alias;
import uk.co.idv.domain.entities.identity.alias.AliasesMother;
import uk.co.idv.domain.entities.identity.Identity;
import uk.co.idv.domain.entities.verificationcontext.FakeVerificationContext;
import uk.co.idv.domain.entities.verificationcontext.VerificationContext;
import uk.co.idv.domain.entities.verificationcontext.VerificationSequences;

import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class VerificationContextConverterTest {

    private final ChannelDocumentConverterDelegator channelConverter = mock(ChannelDocumentConverterDelegator.class);
    private final AliasDocumentConverter aliasConverter = mock(AliasDocumentConverter.class);
    private final IdentityDocumentConverter identityConverter = mock(IdentityDocumentConverter.class);
    private final ActivityConverterDelegator activityConverter = mock(ActivityConverterDelegator.class);
    private final VerificationSequencesConverter sequencesConverter = mock(VerificationSequencesConverter.class);

    private final VerificationContextDocumentConverter contextConverter = VerificationContextDocumentConverter.builder()
            .channelConverter(channelConverter)
            .aliasConverter(aliasConverter)
            .identityConverter(identityConverter)
            .activityConverter(activityConverter)
            .sequencesConverter(sequencesConverter)
            .build();

    @Test
    void shouldConvertIdToContext() {
        final VerificationContextDocument document = VerificationContextDocumentMother.fake();

        final VerificationContext context = contextConverter.toContext(document);

        assertThat(context.getId()).isEqualTo(UUID.fromString(document.getId()));
    }

    @Test
    void shouldConvertIdToDocument() {
        final VerificationContext context = new FakeVerificationContext();

        final VerificationContextDocument contextDocument = contextConverter.toDocument(context);

        assertThat(contextDocument.getId()).isEqualTo(context.getId().toString());
    }

    @Test
    void shouldChannelToContext() {
        final VerificationContextDocument document = VerificationContextDocumentMother.fake();
        final Channel channel = ChannelMother.fake();
        given(channelConverter.toChannel(document.getChannel())).willReturn(channel);

        final VerificationContext context = contextConverter.toContext(document);

        assertThat(context.getChannel()).isEqualTo(channel);
    }

    @Test
    void shouldConvertChannelToDocument() {
        final VerificationContext context = new FakeVerificationContext();
        final ChannelDocument channelDocument = new ChannelDocument();
        given(channelConverter.toDocument(context.getChannel())).willReturn(channelDocument);

        final VerificationContextDocument contextDocument = contextConverter.toDocument(context);

        assertThat(contextDocument.getChannel()).isEqualTo(channelDocument);
    }

    @Test
    void shouldProvidedAliasToContext() {
        final VerificationContextDocument document = VerificationContextDocumentMother.fake();
        final Alias alias = AliasesMother.creditCardNumber();
        given(aliasConverter.toAlias(document.getProvidedAlias())).willReturn(alias);

        final VerificationContext context = contextConverter.toContext(document);

        assertThat(context.getProvidedAlias()).isEqualTo(alias);
    }

    @Test
    void shouldConvertProvidedAliasToDocument() {
        final VerificationContext context = new FakeVerificationContext();
        final AliasDocument aliasDocument = new AliasDocument();
        given(aliasConverter.toDocument(context.getProvidedAlias())).willReturn(aliasDocument);

        final VerificationContextDocument contextDocument = contextConverter.toDocument(context);

        assertThat(contextDocument.getProvidedAlias()).isEqualTo(aliasDocument);
    }

    @Test
    void shouldIdentityToContext() {
        final VerificationContextDocument document = VerificationContextDocumentMother.fake();
        final Identity identity = new Identity(AliasesMother.aliases());
        given(identityConverter.toIdentity(document.getIdentity())).willReturn(identity);

        final VerificationContext context = contextConverter.toContext(document);

        assertThat(context.getIdentity()).isEqualTo(identity);
    }

    @Test
    void shouldConvertIdentityToDocument() {
        final VerificationContext context = new FakeVerificationContext();
        final IdentityDocument identityDocument = new IdentityDocument();
        given(identityConverter.toDocument(context.getIdentity())).willReturn(identityDocument);

        final VerificationContextDocument contextDocument = contextConverter.toDocument(context);

        assertThat(contextDocument.getIdentity()).isEqualTo(identityDocument);
    }

    @Test
    void shouldActivityToContext() {
        final VerificationContextDocument document = VerificationContextDocumentMother.fake();
        final Activity activity = ActivityMother.fake();
        given(activityConverter.toActivity(document.getActivity())).willReturn(activity);

        final VerificationContext context = contextConverter.toContext(document);

        assertThat(context.getActivity()).isEqualTo(activity);
    }

    @Test
    void shouldConvertActivityToDocument() {
        final VerificationContext context = new FakeVerificationContext();
        final ActivityDocument activityDocument = new ActivityDocument();
        given(activityConverter.toDocument(context.getActivity())).willReturn(activityDocument);

        final VerificationContextDocument contextDocument = contextConverter.toDocument(context);

        assertThat(contextDocument.getActivity()).isEqualTo(activityDocument);
    }

    @Test
    void shouldCreateToContext() {
        final VerificationContextDocument document = VerificationContextDocumentMother.fake();

        final VerificationContext context = contextConverter.toContext(document);

        assertThat(context.getCreated()).isEqualTo(Instant.parse(document.getCreated()));
    }

    @Test
    void shouldConvertCreatedToDocument() {
        final VerificationContext context = new FakeVerificationContext();

        final VerificationContextDocument contextDocument = contextConverter.toDocument(context);

        assertThat(contextDocument.getCreated()).isEqualTo(context.getCreated().toString());
    }

    @Test
    void shouldExpiryToContext() {
        final VerificationContextDocument document = VerificationContextDocumentMother.fake();

        final VerificationContext context = contextConverter.toContext(document);

        assertThat(context.getExpiry()).isEqualTo(Instant.parse(document.getExpiry()));
    }

    @Test
    void shouldConvertExpiryToDocument() {
        final VerificationContext context = new FakeVerificationContext();

        final VerificationContextDocument contextDocument = contextConverter.toDocument(context);

        assertThat(contextDocument.getExpiry()).isEqualTo(context.getExpiry().toString());
    }

    @Test
    void shouldConvertSequenceToContext() {
        final VerificationContextDocument document = VerificationContextDocumentMother.fake();
        final VerificationSequences sequences = new VerificationSequences();
        given(sequencesConverter.toSequences(document.getSequences())).willReturn(sequences);

        final VerificationContext context = contextConverter.toContext(document);

        assertThat(context.getSequences()).isEqualTo(sequences);
    }

    @Test
    void shouldConvertSequenceToDocument() {
        final VerificationContext context = new FakeVerificationContext();
        final VerificationSequenceDocument sequenceDocument = new VerificationSequenceDocument();
        final Collection<VerificationSequenceDocument> sequenceDocuments = Collections.singleton(sequenceDocument);
        given(sequencesConverter.toDocuments(context.getSequences())).willReturn(sequenceDocuments);

        final VerificationContextDocument contextDocument = contextConverter.toDocument(context);

        assertThat(contextDocument.getSequences()).isEqualTo(sequenceDocuments);
    }

}
