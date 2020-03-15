package uk.co.idv.api;

import com.fasterxml.jackson.databind.Module;
import org.apache.commons.collections4.ListUtils;
import uk.co.idv.api.lockout.LockoutStateModule;
import uk.co.idv.api.verification.onetimepasscode.ApiOneTimePasscodeModule;
import uk.co.idv.api.verificationcontext.ApiVerificationContextModule;
import uk.co.idv.json.ObjectMapperFactory;
import uk.co.idv.json.activity.ActivityModule;
import uk.co.mruoc.jsonapi.ApiModule;

import java.util.Arrays;
import java.util.List;

public class ApiObjectMapperFactory extends ObjectMapperFactory {

    public ApiObjectMapperFactory() {
        super(defaultModules());
    }

    public ApiObjectMapperFactory(final List<Module> modules) {
        super(modules);
    }

    public static List<Module> defaultModules() {
        return modules(new ActivityModule());
    }

    public static List<Module> modules(final ActivityModule activityModule) {
        final List<Module> jsonModules = ObjectMapperFactory.modules(activityModule);
        final List<Module> apiModules = Arrays.asList(
                new ApiModule(),
                new ApiVerificationContextModule(),
                new ApiOneTimePasscodeModule(),
                new LockoutStateModule()
        );
        return ListUtils.union(jsonModules, apiModules);
    }

}
