package uk.co.idv.utils.json.jackson;

import com.fasterxml.jackson.databind.Module;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public interface ModuleProvider {

    Collection<Module> getModules();

    static Collection<Module> merge(final Collection<Module> modules1, final Collection<Module> modules2) {
        final Collection<Module> allModules = new ArrayList<>(modules1);
        allModules.addAll(modules2);
        return Collections.unmodifiableCollection(allModules);
    }


}
