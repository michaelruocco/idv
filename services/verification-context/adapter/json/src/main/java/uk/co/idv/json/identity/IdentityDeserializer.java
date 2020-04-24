package uk.co.idv.json.identity;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.domain.entities.card.account.Account;
import uk.co.idv.domain.entities.identity.Identity;
import uk.co.idv.domain.entities.identity.alias.Aliases;
import uk.co.idv.domain.entities.mobiledevice.MobileDevice;
import uk.co.idv.domain.entities.phonenumber.PhoneNumbers;
import uk.co.idv.utils.json.converter.jackson.JsonParserConverter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import static uk.co.idv.utils.json.converter.jackson.JsonNodeConverter.toObject;

public class IdentityDeserializer extends StdDeserializer<Identity> {

    protected IdentityDeserializer() {
        super(Identity.class);
    }

    @Override
    public Identity deserialize(final JsonParser parser, final DeserializationContext context) throws IOException {
        final JsonNode node = JsonParserConverter.toNode(parser);
        return Identity.builder()
                .aliases(toAliases(node, parser))
                .phoneNumbers(toPhoneNumbers(node, parser))
                .accounts(toAccounts(node, parser))
                .mobileDevices(toMobileDevices(node, parser))
                .build();
    }

    private static Aliases toAliases(final JsonNode node, final JsonParser parser) {
        return Optional.ofNullable(node.get("aliases"))
                .map(aliases -> toObject(aliases, parser, Aliases.class))
                .orElseGet(Aliases::empty);
    }

    private static PhoneNumbers toPhoneNumbers(final JsonNode node, final JsonParser parser) {
        return Optional.ofNullable(node.get("phoneNumbers"))
                .map(phoneNumbers -> toObject(phoneNumbers, parser, PhoneNumbers.class))
                .orElseGet(PhoneNumbers::new);
    }

    private static Collection<Account> toAccounts(final JsonNode node, final JsonParser parser) {
        return Optional.ofNullable(node.get("accounts"))
                .map(accounts -> Arrays.asList(toObject(accounts, parser, Account[].class)))
                .orElseGet(Collections::emptyList);
    }

    private static Collection<MobileDevice> toMobileDevices(final JsonNode node, final JsonParser parser) {
        return Optional.ofNullable(node.get("mobileDevices"))
                .map(mobileDevices -> Arrays.asList(toObject(mobileDevices, parser, MobileDevice[].class)))
                .orElseGet(Collections::emptyList);
    }

}
