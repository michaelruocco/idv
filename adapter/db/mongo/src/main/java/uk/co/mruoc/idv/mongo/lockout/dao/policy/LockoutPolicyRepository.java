package uk.co.mruoc.idv.mongo.lockout.dao.policy;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface LockoutPolicyRepository extends MongoRepository<LockoutPolicyDocument, String> {

    //intentionally blank

}
