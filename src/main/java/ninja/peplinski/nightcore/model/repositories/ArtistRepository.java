package ninja.peplinski.nightcore.model.repositories;

import ninja.peplinski.nightcore.model.Artist;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface ArtistRepository extends CrudRepository<Artist, Integer>,
        PagingAndSortingRepository<Artist, Integer>,
        JpaSpecificationExecutor<Artist> {

    public Optional<Artist> findByName(String name);
}
