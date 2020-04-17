package uk.co.idv.json.account;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.domain.entities.card.account.Account;
import uk.co.idv.domain.entities.card.account.ClosedAccount;
import uk.co.idv.domain.entities.card.account.OpenAccount;
import uk.co.idv.domain.entities.card.number.CardNumber;
import uk.co.idv.utils.json.converter.jackson.JsonNodeConverter;
import uk.co.idv.utils.json.converter.jackson.JsonParserConverter;

import java.util.Arrays;
import java.util.Collection;

public class AccountDeserializer extends StdDeserializer<Account> {

    public AccountDeserializer() {
        super(Account.class);
    }

    @Override
    public Account deserialize(final JsonParser parser, final DeserializationContext context) {
        final JsonNode node = JsonParserConverter.toNode(parser);
        final String status = node.get("status").asText();
        final Collection<CardNumber> cardNumbers = extractCardNumbers(node, parser);
        switch (status) {
            case OpenAccount.STATUS: return new OpenAccount(cardNumbers);
            case ClosedAccount.STATUS: return new ClosedAccount(cardNumbers);
            default: throw new InvalidAccountStatusException(status);
        }
    }

    private Collection<CardNumber> extractCardNumbers(final JsonNode node, final JsonParser parser) {
        final CardNumber[] cardNumbers = JsonNodeConverter.toObject(node.get("cardNumbers"), parser, CardNumber[].class);
        return Arrays.asList(cardNumbers);
    }

    public static class InvalidAccountStatusException extends RuntimeException {

        public InvalidAccountStatusException(final String status) {
            super(status);
        }

    }

}
