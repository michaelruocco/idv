package uk.co.idv.json.verificationcontext.method;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;

@Slf4j
public abstract class AbstractVerificationMethodJsonNodeConverter implements VerificationMethodJsonNodeConverter {

    private final String supportedName;

    public AbstractVerificationMethodJsonNodeConverter(final String supportedName) {
        this.supportedName = supportedName;
    }

    @Override
    public boolean supportsMethod(String name) {
        boolean supported = supportedName.equals(name);
        log.debug("returning supported {} for method name {}", supported, name);
        return supported;
    }

    @Override
    public abstract VerificationMethod toMethod(JsonNode node,
                                                JsonParser parser,
                                                DeserializationContext context);

}
