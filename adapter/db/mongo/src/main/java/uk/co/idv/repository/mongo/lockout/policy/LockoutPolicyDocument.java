package uk.co.idv.repository.mongo.lockout.policy;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("lockoutPolicies")
public class LockoutPolicyDocument {

    @Id
    private String key;
    private String id;
    private String lockoutType;
    private String recordAttemptStrategyType;
    private boolean aliasLevel;

}
