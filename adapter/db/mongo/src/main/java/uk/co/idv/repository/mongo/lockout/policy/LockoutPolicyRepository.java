package uk.co.idv.repository.mongo.lockout.policy;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Collection;

public interface LockoutPolicyRepository extends MongoRepository<LockoutPolicyDocument, String> {

    Collection<LockoutPolicyDocument> findByChannelId(final String channelId);

}
