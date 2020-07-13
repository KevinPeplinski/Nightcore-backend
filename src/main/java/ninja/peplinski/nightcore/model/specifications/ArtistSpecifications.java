package ninja.peplinski.nightcore.model.specifications;

import ninja.peplinski.nightcore.model.Artist;
import org.springframework.data.jpa.domain.Specification;

public class ArtistSpecifications {
    public static Specification<Artist> artistWithName(String name) {
        return new GenericSpecification<>(
                new SearchCriteria("name", SearchCriteria.Operation.LIKE, name)
        );
    }
}
