package uk.co.mruoc.idv.mongo.lockout.dao;

import lombok.Builder;
import uk.co.mruoc.idv.lockout.dao.LockoutPolicyDao;
import uk.co.mruoc.idv.lockout.domain.model.AbstractLockoutPolicyParameters;
import uk.co.mruoc.idv.lockout.domain.model.LockoutPolicy;
import uk.co.mruoc.idv.lockout.domain.service.LockoutPolicyParametersConverter;
import uk.co.mruoc.idv.lockout.domain.service.LockoutRequest;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Builder
public class MongoLockoutPolicyDao implements LockoutPolicyDao {

    private final LockoutPolicyRepository repository;
    private final LockoutPolicyParametersConverterDelegator documentConverter;
    private final LockoutPolicyParametersConverter parametersConverter;

    @Override
    public void save(final LockoutPolicy policy) {
        final AbstractLockoutPolicyParameters parameters = policy.getParameters();
        final LockoutPolicyDocument document = documentConverter.toDocument(parameters);
        repository.save(document);
    }

    @Override
    public Optional<LockoutPolicy> load(final UUID id) {
        final Optional<LockoutPolicyDocument> document = repository.findById(id.toString());
        return document
                .map(documentConverter::toParameters)
                .map(parametersConverter::toPolicy);
    }

    @Override
    public Optional<LockoutPolicy> load(final LockoutRequest request) {
        final String channelId = request.getChannelId();
        final String activityName = request.getActivityName();
        final String aliasType = request.getAliasType();
        final Collection<LockoutPolicyDocument> documents = repository.findByLookupsChannelIdAndLookupsActivityNameAndLookupsAliasType(
                channelId,
                activityName,
                aliasType
        );
        if (documents.isEmpty()) {
            return Optional.empty();
        }
        if (documents.size() == 1) {
            return Optional.of(toPolicy(documents.iterator().next()));
        }
        throw new MultipleLockoutPoliciesFoundForRequestException(request); // should never happen!
    }

    @Override
    public Collection<LockoutPolicy> load() {
        final Collection<LockoutPolicyDocument> documents = repository.findAll();
        return documents.stream()
                .map(documentConverter::toParameters)
                .map(parametersConverter::toPolicy)
                .collect(Collectors.toList());
    }

    private LockoutPolicy toPolicy(final LockoutPolicyDocument document) {
        final AbstractLockoutPolicyParameters parameters = documentConverter.toParameters(document);
        return parametersConverter.toPolicy(parameters);
    }

    public static class MultipleLockoutPoliciesFoundForRequestException extends RuntimeException {

        private final LockoutRequest request;

        public MultipleLockoutPoliciesFoundForRequestException(final LockoutRequest request) {
            super(toMessage(request));
            this.request = request;
        }

        public LockoutRequest getRequest() {
            return request;
        }

        private static String toMessage(final LockoutRequest request) {
            return String.format("channelId %s, activityName %s, aliasType %s",
                    request.getChannelId(),
                    request.getActivityName(),
                    request.getAliasType());
        }

    }

}
