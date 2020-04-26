package uk.co.idv.api.lockout.policy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uk.co.idv.domain.entities.lockout.policy.LockoutLevel;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DefaultLockoutPolicyAttributes implements LockoutPolicyAttributes {

    private UUID id;
    private String type;
    private String recordAttempts;
    private LockoutLevel level;

}
