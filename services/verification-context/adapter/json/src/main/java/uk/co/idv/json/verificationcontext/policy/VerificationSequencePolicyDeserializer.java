package uk.co.idv.json.verificationcontext.policy;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethodPolicy;
import uk.co.idv.domain.entities.verificationcontext.policy.VerificationSequencePolicy;
import uk.co.idv.utils.json.converter.jackson.JsonNodeConverter;
import uk.co.idv.utils.json.converter.jackson.JsonParserConverter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

public class VerificationSequencePolicyDeserializer extends StdDeserializer<VerificationSequencePolicy> {

    public VerificationSequencePolicyDeserializer() {
        super(VerificationSequencePolicy.class);
    }

    @Override
    public VerificationSequencePolicy deserialize(final JsonParser parser, final DeserializationContext context) throws IOException {
        final JsonNode node = JsonParserConverter.toNode(parser);
        return VerificationSequencePolicy.builder()
                .name(node.get("name").asText())
                .methodPolicies(toMethodPolicies(node, parser))
                .build();
    }

    private static Collection<VerificationMethodPolicy> toMethodPolicies(final JsonNode node, final JsonParser parser) {
        return Optional.ofNullable(node.get("methods"))
                .map(results -> Arrays.asList(JsonNodeConverter.toObject(results, parser, VerificationMethodPolicy[].class)))
                .orElseGet(Collections::emptyList);
    }

}
