package uk.co.idv.repository.dynamo.identity;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface IdentityRepository extends CrudRepository<IdentityDocument, IdentityId> {

    @EnableScan
    Collection<IdentityDocument> findByIdvId(String idvId);

    Collection<IdentityDocument> findByAlias(String alias);

}
