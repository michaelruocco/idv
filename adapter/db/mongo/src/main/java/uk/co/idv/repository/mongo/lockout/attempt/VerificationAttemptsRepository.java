package uk.co.idv.repository.mongo.lockout.attempt;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface VerificationAttemptsRepository extends MongoRepository<VerificationAttemptsDocument, String> {

    // intentionally blank

}
