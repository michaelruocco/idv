package uk.co.idv.repository.mongo.lockout.policy;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface LockoutPolicyRepository extends MongoRepository<LockoutPolicyDocument, String> {

    //intentionally blank

}
