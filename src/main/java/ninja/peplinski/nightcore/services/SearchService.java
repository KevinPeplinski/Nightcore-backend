package ninja.peplinski.nightcore.services;

import com.fasterxml.jackson.annotation.JsonView;
import ninja.peplinski.nightcore.model.Artist;
import ninja.peplinski.nightcore.model.SearchableEntity;
import ninja.peplinski.nightcore.model.Song;
import ninja.peplinski.nightcore.model.specifications.GenericSpecification;
import ninja.peplinski.nightcore.model.specifications.SearchCriteria;
import ninja.peplinski.nightcore.model.view.JsonScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchService {

    @Autowired
    private ArtistService artistService;
    @Autowired
    private SongService songService;

    public Iterable<SearchWrapper<?>> genericSearch(String q) {
        List<SearchWrapper<?>> searchWrappers = new ArrayList<>();

        GenericSpecification<Artist> artistGenericSpecification = new GenericSpecification<>(
                new SearchCriteria("name", ":", q));
        Page<Artist> artistsPage = artistService.getAllSearched(artistGenericSpecification, 1, 5, "id");
        Iterable<Artist> artists = artistsPage.getContent();
        SearchService.SearchWrapper<Artist> artistSearchWrapper = new SearchService.SearchWrapper<>("artist", artists);
        searchWrappers.add(artistSearchWrapper);

        GenericSpecification<Song> songGenericSpecification = new GenericSpecification<>(
                new SearchCriteria("title", ":", q));
        Page<Song> songPage = songService.getAllSearched(songGenericSpecification, 1, 5, "id");
        Iterable<Song> songs = songPage.getContent();
        SearchService.SearchWrapper<Song> songSearchWrapper = new SearchService.SearchWrapper<>("song", songs);
        searchWrappers.add(songSearchWrapper);

        return searchWrappers;
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
