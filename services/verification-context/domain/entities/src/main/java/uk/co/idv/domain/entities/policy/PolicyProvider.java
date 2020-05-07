package uk.co.idv.domain.entities.policy;

import java.util.Collection;

public interface PolicyProvider<T extends Policy> {

    Collection<T> getPolicies();

}
