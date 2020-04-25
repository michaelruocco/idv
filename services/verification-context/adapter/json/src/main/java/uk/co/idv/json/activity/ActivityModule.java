package uk.co.idv.json.activity;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.zalando.jackson.datatype.money.MoneyModule;
import uk.co.idv.domain.entities.activity.Activity;
import uk.co.idv.domain.entities.activity.OnlinePurchase;
import uk.co.idv.json.activity.onlinepurchase.OnlinePurchaseDeserializer;
import uk.co.idv.json.activity.onlinepurchase.OnlinePurchaseMixin;
import uk.co.idv.json.cardnumber.CardNumberModule;

import java.util.Arrays;

@Slf4j
public class ActivityModule extends SimpleModule {

    public ActivityModule() {
        super("activity-module", Version.unknownVersion());

        setMixInAnnotation(Activity.class, ActivityMixin.class);
        setMixInAnnotation(OnlinePurchase.class, OnlinePurchaseMixin.class);

        addDeserializer(Activity.class, new ActivityDeserializer());
        addDeserializer(OnlinePurchase.class, new OnlinePurchaseDeserializer());
    }

    @Override
    public Iterable<? extends Module> getDependencies() {
        return Arrays.asList(
                new JavaTimeModule(),
                new MoneyModule(),
                new CardNumberModule()
        );
    }

}
