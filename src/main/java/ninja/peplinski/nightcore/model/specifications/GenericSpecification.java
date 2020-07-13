package ninja.peplinski.nightcore.model.specifications;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

public class GenericSpecification <T> implements Specification<T> {

    private final SearchCriteria criteria;

    public GenericSpecification(SearchCriteria searchCriteria) {
        this.criteria = searchCriteria;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        switch (criteria.getOperation()) {
            case LIKE:
                if (root.get(criteria.getKey()).getJavaType() == String.class) {
                    return criteriaBuilder.like(
                            root.<String>get(criteria.getKey()), "%" + criteria.getValue() + "%");
                } else {
                    if (criteria.getInsideRelationshipTypeKey() != null) {
                        final Path<SearchableInsideRelationshipChain> artist = root.<SearchableInsideRelationshipChain> get(criteria.getKey());
                        return criteriaBuilder.like(
                                artist.<String>get(criteria.getInsideRelationshipTypeKey()), "%" + criteria.getValue() + "%");
                    } else {
                        return criteriaBuilder.equal(root.get(criteria.getKey()), criteria.getValue());
                    }
                }
            case LESS_THAN:
                return criteriaBuilder.lessThanOrEqualTo(
                        root.<String> get(criteria.getKey()), criteria.getValue().toString());
            case GREATER_THAN:
                return criteriaBuilder.greaterThanOrEqualTo(
                        root.<String> get(criteria.getKey()), criteria.getValue().toString());
            default:
                return null;
        }
    }
}