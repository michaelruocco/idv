package uk.co.mruoc.idv.mongo.lockout.dao.policy;

import lombok.Data;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collection;

@Data
@Document("lockoutPolicies")
@CompoundIndex(def = "{'requests.channelId':1, 'requests.activityName':1, 'requests.aliasType':1}", name = "load-index", unique = true)
public class LockoutPolicyDocument {

    private String id;
    private String lockoutType;
    private String recordAttemptStrategyType;
    private Collection<LockoutLookupDocument> lookups;

    public LockoutLookupDocument getLookup(final int index) {
        return IterableUtils.get(lookups, index);
    }

}
