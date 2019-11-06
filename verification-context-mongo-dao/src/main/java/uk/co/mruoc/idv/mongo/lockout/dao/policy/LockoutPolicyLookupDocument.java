package uk.co.mruoc.idv.mongo.lockout.dao.policy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LockoutPolicyLookupDocument {

    private String channelId;
    private String activityName;
    private String aliasType;

}
