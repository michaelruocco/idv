package uk.co.idv.repository.mongo.lockout.attempt;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collection;

@Data
@Document("verificationAttempts")
public class VerificationAttemptsDocument {

    @Id
    private String idvId;
    private String id;
    private Collection<VerificationAttemptDocument> attempts;

}
