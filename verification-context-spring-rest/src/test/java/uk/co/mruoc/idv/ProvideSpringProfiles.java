package uk.co.mruoc.idv;

import org.junit.contrib.java.lang.system.ProvideSystemProperty;

public class ProvideSpringProfiles extends ProvideSystemProperty {

    public ProvideSpringProfiles(final String profiles) {
        super("spring.profiles.active", profiles);
    }

}
