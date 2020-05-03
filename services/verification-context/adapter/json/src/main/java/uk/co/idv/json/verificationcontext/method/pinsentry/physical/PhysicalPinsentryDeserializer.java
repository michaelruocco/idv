package uk.co.idv.json.verificationcontext.method.pinsentry.physical;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.domain.entities.card.number.CardNumber;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical.PhysicalPinsentry;
import uk.co.idv.utils.json.converter.jackson.JsonNodeConverter;
import uk.co.idv.utils.json.converter.jackson.JsonParserConverter;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import static uk.co.idv.json.verificationcontext.method.VerificationMethodJsonNodeConverter.toEligibility;
import static uk.co.idv.json.verificationcontext.method.VerificationMethodJsonNodeConverter.toResults;
import static uk.co.idv.json.verificationcontext.method.pinsentry.PinsentryFunctionJsonNodeConverter.extractParams;

public class PhysicalPinsentryDeserializer extends StdDeserializer<PhysicalPinsentry> {

    public PhysicalPinsentryDeserializer() {
        super(PhysicalPinsentry.class);
    }

    @Override
    public PhysicalPinsentry deserialize(final JsonParser parser, final DeserializationContext context) {
        final JsonNode node = JsonParserConverter.toNode(parser);
        return PhysicalPinsentry.eligibleBuilder()
                .params(extractParams(node, parser))
                .cardNumbers(extractCardNumbers(node, parser))
                .eligibility(toEligibility(node, parser))
                .results(toResults(node, parser))
                .build();
    }

    private static Collection<CardNumber> extractCardNumbers(final JsonNode node, final JsonParser parser) {
        return Optional.ofNullable(node.get("cardNumbers"))
                .map(results -> Arrays.asList(JsonNodeConverter.toObject(results, parser, CardNumber[].class)))
                .orElseGet(Collections::emptyList);
    }

}
