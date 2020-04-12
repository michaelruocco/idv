package uk.co.idv.utils.json.converter.jackson;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class FakeModule extends SimpleModule {

    public FakeModule() {
        super("fake-module", Version.unknownVersion());
    }

}
