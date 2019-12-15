package uk.co.idv.repository.dynamo.identity.alias;

import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface AliasMappingRepository extends CrudRepository<AliasMappingDocument, String> {

    Collection<AliasMappingDocument> findByIdvId(String idvId);

}
