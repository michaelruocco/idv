package uk.co.idv.repository.mongo.lockout.policy;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collection;

@Data
@Document("lockoutPolicies")
public class LockoutPolicyDocument {

    @Id
    private String id;

    @Indexed
    private String channelId;

    private String lockoutType;
    private Collection<String> activityNames;
    private Collection<String> aliasTypes;
    private String recordAttemptStrategyType;

}
