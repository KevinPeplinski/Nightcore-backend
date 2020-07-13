package ninja.peplinski.nightcore.model.specifications;

import ninja.peplinski.nightcore.model.Song;
import org.springframework.data.jpa.domain.Specification;

public class SongSpecifications {
    public static Specification<Song> songWithTitle(String title) {
        return new GenericSpecification<>(
                new SearchCriteria("title", SearchCriteria.Operation.LIKE, title)
        );
    }

    public static Specification<Song> songWithArtistName(String artistName) {
        return new GenericSpecification<>(
                new SearchCriteria("artist", SearchCriteria.Operation.LIKE, artistName, "name")
        );
    }
}
