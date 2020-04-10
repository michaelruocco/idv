package uk.co.idv.api;

import com.fasterxml.jackson.databind.Module;
import uk.co.idv.api.lockout.LockoutStateModule;
import uk.co.idv.api.verification.onetimepasscode.ApiOneTimePasscodeModule;
import uk.co.idv.api.verificationcontext.ApiVerificationContextModule;
import uk.co.idv.json.DefaultModuleProvider;
import uk.co.idv.json.activity.ActivityModule;
import uk.co.mruoc.jsonapi.ApiModule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class ApiModuleProvider extends DefaultModuleProvider {

    public ApiModuleProvider(final Module... additionalModules) {
        this(Arrays.asList(additionalModules));
    }

    public ApiModuleProvider(final Collection<Module> additionalModules) {
        super(appendToDefaultModules(additionalModules));
    }

    private static Collection<Module> appendToDefaultModules(final Collection<Module> additionalModules) {
        final Collection<Module> allModules = defaultModules();
        allModules.addAll(additionalModules);
        return Collections.unmodifiableCollection(allModules);
    }

    private static Collection<Module> defaultModules() {
        return new ArrayList<>(Arrays.asList(
                new ActivityModule(),
                new ApiModule(),
                new ApiVerificationContextModule(),
                new ApiOneTimePasscodeModule(),
                new LockoutStateModule()
        ));
    }

}
