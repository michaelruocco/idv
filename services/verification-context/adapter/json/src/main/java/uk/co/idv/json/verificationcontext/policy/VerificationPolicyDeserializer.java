package uk.co.idv.json.verificationcontext.policy;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.domain.entities.policy.PolicyLevel;
import uk.co.idv.domain.entities.verificationcontext.policy.DefaultVerificationPolicy;
import uk.co.idv.domain.entities.verificationcontext.policy.VerificationPolicy;
import uk.co.idv.domain.entities.verificationcontext.policy.VerificationSequencePolicy;
import uk.co.idv.utils.json.converter.jackson.JsonNodeConverter;
import uk.co.idv.utils.json.converter.jackson.JsonParserConverter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

public class VerificationPolicyDeserializer extends StdDeserializer<VerificationPolicy> {

    public VerificationPolicyDeserializer() {
        super(VerificationPolicy.class);
    }

    @Override
    public VerificationPolicy deserialize(final JsonParser parser, final DeserializationContext context) throws IOException {
        final JsonNode node = JsonParserConverter.toNode(parser);
        return DefaultVerificationPolicy.builder()
                .id(JsonNodeConverter.toUUID(node.get("id")))
                .level(JsonNodeConverter.toObject(node.get("level"), parser, PolicyLevel.class))
                .sequencePolicies(toSequencePolicies(node, parser))
                .build();
    }

    private static Collection<VerificationSequencePolicy> toSequencePolicies(final JsonNode node, final JsonParser parser) {
        return Optional.ofNullable(node.get("sequences"))
                .map(results -> Arrays.asList(JsonNodeConverter.toObject(results, parser, VerificationSequencePolicy[].class)))
                .orElseGet(Collections::emptyList);
    }

}
