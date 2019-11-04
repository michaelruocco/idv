package uk.co.mruoc.idv.mongo.lockout.dao.policy;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Collection;

public interface LockoutPolicyRepository extends MongoRepository<LockoutPolicyDocument, String> {

    Collection<LockoutPolicyDocument> findByLookupsChannelIdAndLookupsActivityNameAndLookupsAliasType(
            final String channelId,
            final String activityName,
            final String aliasType
    );

}
