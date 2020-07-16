package ninja.peplinski.nightcore.services;

import com.fasterxml.jackson.annotation.JsonView;
import ninja.peplinski.nightcore.model.Artist;
import ninja.peplinski.nightcore.model.SearchableEntity;
import ninja.peplinski.nightcore.model.Song;
import ninja.peplinski.nightcore.model.view.JsonScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static ninja.peplinski.nightcore.model.specifications.ArtistSpecifications.artistWithName;
import static ninja.peplinski.nightcore.model.specifications.SongSpecifications.songWithArtistName;
import static ninja.peplinski.nightcore.model.specifications.SongSpecifications.songWithTitle;

@Service
public class SearchService {

    @Autowired
    private ArtistService artistService;
    @Autowired
    private SongService songService;

    public Iterable<SearchWrapper<?>> genericSearch(String q) {
        List<SearchWrapper<?>> searchWrappers = new ArrayList<>();

        SearchWrapperCreator<Artist> artists = new SearchWrapperCreator<>(artistService, artistWithName(q), "artist");

        Specification<Song> songSpecification = Specification.where(songWithTitle(q)).or(songWithArtistName(q));
        SearchWrapperCreator<Song> songs = new SearchWrapperCreator<>(songService, songSpecification, "song");

        searchWrappers.add(artists.getSearchWrapper());
        searchWrappers.add(songs.getSearchWrapper());

        return searchWrappers;
    }

    static class SearchWrapperCreator<T extends SearchableEntity> {
        private final Searchable<T> service;
        private final Specification<T> specification;
        private final String type;

        public SearchWrapperCreator(Searchable<T> service, Specification<T> specification, String type) {
            this.service = service;
            this.specification = specification;
            this.type = type;
        }

        public SearchWrapper<T> getSearchWrapper() {
            String sortBy = "id";
            Page<T> page = this.service.getAllSearched(this.specification, 1, 5, sortBy);
            Iterable<T> content = page.getContent();
            return new SearchWrapper<>(this.type, content);
        }
    }

    public static class SearchWrapper<T extends SearchableEntity> {
        @JsonView(JsonScope.Public.class)
        private final String type;
        @JsonView(JsonScope.Public.class)
        private final Iterable<T> content;

        public SearchWrapper(String type, Iterable<T> content) {
            this.type = type;
            this.content = content;
        }

        public Iterable<T> getContent() {
            return content;
        }

        public String getType() {
            return type;
        }
    }
}
