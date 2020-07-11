package ninja.peplinski.nightcore.model.repositories;

import ninja.peplinski.nightcore.model.Song;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface SongRepository extends CrudRepository<Song, Integer>, PagingAndSortingRepository<Song, Integer> {
    public Optional<Song> findByYtId(String ytId);
}
