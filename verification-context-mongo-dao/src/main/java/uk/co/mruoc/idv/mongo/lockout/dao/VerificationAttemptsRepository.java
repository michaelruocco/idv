package uk.co.mruoc.idv.mongo.lockout.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface VerificationAttemptsRepository extends MongoRepository<VerificationAttemptsDocument, String> {

    // intentionally blank

}
