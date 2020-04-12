package uk.co.idv.json.activity;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.zalando.jackson.datatype.money.MoneyModule;
import uk.co.idv.domain.entities.activity.Activity;
import uk.co.idv.json.cardnumber.CardNumberModule;

import java.util.Arrays;

@Slf4j
public class ActivityModule extends SimpleModule {

    private static final String NAME = "activity-module";

    public ActivityModule() {
        this(new DefaultActivityDeserializer());
    }

    public ActivityModule(final ActivityDeserializer deserializer) {
        super(NAME, Version.unknownVersion());

        setMixInAnnotation(Activity.class, ActivityMixin.class);

        log.info("creating {} with deserializer {}", NAME, deserializer);
        addDeserializer(Activity.class, deserializer);
    }

    @Override
    public Iterable<? extends Module> getDependencies() {
        return Arrays.asList(
                new Jdk8Module(),
                new JavaTimeModule(),
                new MoneyModule(),
                new CardNumberModule()
        );
    }

}
