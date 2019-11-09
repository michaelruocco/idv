package uk.co.mruoc.idv.mongo.identity.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Collection;

public interface IdentityRepository extends MongoRepository<IdentityDocument, String> {

    Collection<IdentityDocument> findByAliasesTypeAndAliasesValue(final String aliasType, final String aliasValue);

}
