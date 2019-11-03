package uk.co.mruoc.idv.mongo.lockout.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LockoutLookupDocument {

    private String channelId;
    private String activityName;
    private String aliasType;

}
