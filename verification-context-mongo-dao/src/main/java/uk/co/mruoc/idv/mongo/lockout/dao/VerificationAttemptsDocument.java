package uk.co.mruoc.idv.mongo.lockout.dao;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Collection;

@Data
public class VerificationAttemptsDocument {

    @Id
    private String idvId;
    private String id;
    private Collection<VerificationAttemptDocument> attempts;

}
