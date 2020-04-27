package uk.co.idv.json.lockout.policy.nonlocking;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.domain.entities.lockout.policy.nonlocking.NonLockingStateCalculator;

public class NonLockingStateCalculatorDeserializer extends StdDeserializer<NonLockingStateCalculator> {

    public NonLockingStateCalculatorDeserializer() {
        super(NonLockingStateCalculator.class);
    }

    @Override
    public NonLockingStateCalculator deserialize(final JsonParser parser, final DeserializationContext context) {
        return new NonLockingStateCalculator();
    }

}
