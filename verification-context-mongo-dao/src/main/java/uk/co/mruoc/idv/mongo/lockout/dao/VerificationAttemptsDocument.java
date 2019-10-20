package uk.co.mruoc.idv.mongo.lockout.dao;

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
