package uk.co.idv.json.identity.alias;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.domain.entities.identity.alias.Alias;
import uk.co.idv.domain.entities.identity.alias.CreditCardNumber;
import uk.co.idv.domain.entities.identity.alias.DebitCardNumber;
import uk.co.idv.domain.entities.identity.alias.IdvId;
import uk.co.idv.utils.json.converter.jackson.JsonNodeConverter;
import uk.co.idv.utils.json.converter.jackson.JsonParserConverter;

import java.util.Map;
import java.util.Optional;

public class AliasDeserializer extends StdDeserializer<Alias> {

    private final Map<String, Class<? extends Alias>> mappings;

    public AliasDeserializer() {
        this(buildDefaultMappings());
    }

    public AliasDeserializer(final Map<String, Class<? extends Alias>> mappings) {
        super(Alias.class);
        this.mappings = mappings;
    }

    @Override
    public Alias deserialize(final JsonParser parser, final DeserializationContext context) {
        final JsonNode node = JsonParserConverter.toNode(parser);
        final String type = extractType(node);
        final Optional<Class<? extends Alias>> aliasType = toMappingType(type);
        return aliasType.map(t -> toAlias(node, parser, t))
                .orElseThrow(() -> new AliasNotSupportedException(type));
    }

    private Optional<Class<? extends Alias>> toMappingType(final String name) {
        return Optional.ofNullable(mappings.get(name));
    }

    private Alias toAlias(final JsonNode node, final JsonParser parser, final Class<? extends Alias> type) {
        return JsonNodeConverter.toObject(node, parser, type);
    }

    private static String extractType(final JsonNode node) {
        return node.get("type").asText();
    }

    private static Map<String, Class<? extends Alias>> buildDefaultMappings() {
        return Map.of(
                IdvId.TYPE, IdvId.class,
                CreditCardNumber.TYPE, CreditCardNumber.class,
                DebitCardNumber.TYPE, DebitCardNumber.class
        );
    }

    public static class AliasNotSupportedException extends RuntimeException {

        public AliasNotSupportedException(final String type) {
            super(type);
        }

    }

}
