package uk.co.idv.json;

import com.fasterxml.jackson.databind.Module;

import java.util.Collection;

public interface ModuleProvider {

    Collection<Module> getModules();

}
