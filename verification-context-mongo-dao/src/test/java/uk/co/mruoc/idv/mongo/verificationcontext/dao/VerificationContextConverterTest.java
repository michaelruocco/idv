package uk.co.mruoc.idv.mongo.verificationcontext.dao;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.domain.model.activity.Activity;
import uk.co.mruoc.idv.domain.model.activity.FakeActivity;
import uk.co.mruoc.idv.domain.model.channel.Channel;
import uk.co.mruoc.idv.domain.model.channel.FakeChannel;
import uk.co.mruoc.idv.identity.domain.model.Alias;
import uk.co.mruoc.idv.identity.domain.model.FakeCreditCardNumber;
import uk.co.mruoc.idv.identity.domain.model.FakeIdentity;
import uk.co.mruoc.idv.identity.domain.model.Identity;
import uk.co.mruoc.idv.mongo.dao.activity.ActivityConverterDelegator;
import uk.co.mruoc.idv.mongo.dao.activity.ActivityDocument;
import uk.co.mruoc.idv.mongo.dao.channel.ChannelConverter;
import uk.co.mruoc.idv.mongo.dao.channel.ChannelDocument;
import uk.co.mruoc.idv.mongo.identity.dao.AliasConverter;
import uk.co.mruoc.idv.mongo.identity.dao.AliasDocument;
import uk.co.mruoc.idv.mongo.identity.dao.IdentityConverter;
import uk.co.mruoc.idv.mongo.identity.dao.IdentityDocument;
import uk.co.mruoc.idv.verificationcontext.domain.model.FakeVerificationContext;
import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationContext;
import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationSequences;

import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class VerificationContextConverterTest {

    private final ChannelConverter channelConverter = mock(ChannelConverter.class);
    private final AliasConverter aliasConverter = mock(AliasConverter.class);
    private final IdentityConverter identityConverter = mock(IdentityConverter.class);
    private final ActivityConverterDelegator activityConverter = mock(ActivityConverterDelegator.class);
    private final VerificationSequenceConverter sequenceConverter = mock(VerificationSequenceConverter.class);

    private final VerificationContextConverter contextConverter = VerificationContextConverter.builder()
            .channelConverter(channelConverter)
            .aliasConverter(aliasConverter)
            .identityConverter(identityConverter)
            .activityConverter(activityConverter)
            .sequenceConverter(sequenceConverter)
            .build();

    @Test
    void shouldConvertIdToContext() {
        final VerificationContextDocument document = new FakeVerificationContextDocument();

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
        final VerificationContextDocument document = new FakeVerificationContextDocument();
        final Channel channel = new FakeChannel();
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
        final VerificationContextDocument document = new FakeVerificationContextDocument();
        final Alias alias = new FakeCreditCardNumber();
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
        final VerificationContextDocument document = new FakeVerificationContextDocument();
        final Identity identity = new FakeIdentity();
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
        final VerificationContextDocument document = new FakeVerificationContextDocument();
        final Activity activity = new FakeActivity();
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
        final VerificationContextDocument document = new FakeVerificationContextDocument();

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
        final VerificationContextDocument document = new FakeVerificationContextDocument();

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
        final VerificationContextDocument document = new FakeVerificationContextDocument();
        final VerificationSequences sequences = new VerificationSequences();
        given(sequenceConverter.toSequences(document.getSequences())).willReturn(sequences);

        final VerificationContext context = contextConverter.toContext(document);

        assertThat(context.getSequences()).isEqualTo(sequences);
    }

    @Test
    void shouldConvertSequenceToDocument() {
        final VerificationContext context = new FakeVerificationContext();
        final VerificationSequenceDocument sequenceDocument = new VerificationSequenceDocument();
        final Collection<VerificationSequenceDocument> sequenceDocuments = Collections.singleton(sequenceDocument);
        given(sequenceConverter.toDocuments(context.getSequences())).willReturn(sequenceDocuments);

        final VerificationContextDocument contextDocument = contextConverter.toDocument(context);

        assertThat(contextDocument.getSequences()).isEqualTo(sequenceDocuments);
    }

}
