package ninja.peplinski.nightcore.controller;

import com.fasterxml.jackson.annotation.JsonView;
import ninja.peplinski.nightcore.model.Artist;
import ninja.peplinski.nightcore.model.Song;
import ninja.peplinski.nightcore.model.repositories.ArtistRepository;
import ninja.peplinski.nightcore.model.view.JsonScope;
import ninja.peplinski.nightcore.services.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/artists")
public class ArtistController {

    @Autowired
    private ArtistRepository artistRepository;
    @Autowired
    private ArtistService artistService;

    @GetMapping(path = "/all")
    @JsonView(JsonScope.Public.class)
    private @ResponseBody
    Iterable<Artist> getAllArtists(@RequestParam(defaultValue = "1") Integer p,
                                   @RequestParam(defaultValue = "10") Integer l,
                                   @RequestParam(defaultValue = "id") String sortBy) {

        Page<Artist> pagedResult = artistService.getAll(p, l, sortBy);

        if (pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<>();
        }
    }

    @GetMapping(path = "/{id}/songs")
    private @ResponseBody
    ResponseEntity<Iterable<Song>> getAllSongsOfArtist(@PathVariable Integer id) {

        Optional<Artist> artistOptional = artistRepository.findById(id);

        if (artistOptional.isPresent()) {
            return ResponseEntity.ok(Objects.requireNonNull(artistRepository.findById(id).orElse(null)).getSongList());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping(path = "/{id}")
    private @ResponseBody ResponseEntity<Artist> getArtistById(@PathVariable Integer id) {

        Optional<Artist> optionalArtist = artistRepository.findById(id);

        return optionalArtist.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null));
    }

    @PostMapping(path = "/add")
    private @ResponseBody String addNewSong(@RequestParam String name) {
        Artist artist = new Artist();
        artist.setName(name);

        artistRepository.save(artist);
        return "Saved " + artist;
    }
}