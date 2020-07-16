package ninja.peplinski.nightcore.controller;

import com.fasterxml.jackson.annotation.JsonView;
import ninja.peplinski.nightcore.errors.AlreadyExistsException;
import ninja.peplinski.nightcore.model.Artist;
import ninja.peplinski.nightcore.model.Song;
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

import static ninja.peplinski.nightcore.model.specifications.ArtistSpecifications.artistWithName;

@RestController
@RequestMapping(path = "/api/artists")
public class ArtistController {

    @Autowired
    private ArtistService artistService;

    @GetMapping(path = "/search")
    @JsonView(JsonScope.Public.class)
    private @ResponseBody ResponseEntity<Iterable<Artist>> searchArtists(@RequestParam String q,
                                               @RequestParam(defaultValue = "1") Integer p,
                                               @RequestParam(defaultValue = "10") Integer l,
                                               @RequestParam(defaultValue = "id") String sortBy) {

        Page<Artist> pagedResult = artistService.getAllSearched(artistWithName(q), p, l , sortBy);

        if (pagedResult.hasContent()) {
            return ResponseEntity.ok(pagedResult.getContent());
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ArrayList<>());
        }
    }

    @GetMapping(path = "/all")
    @JsonView(JsonScope.Public.class)
    private @ResponseBody ResponseEntity<Iterable<Artist>> getAllArtists(@RequestParam(defaultValue = "1") Integer p,
                                   @RequestParam(defaultValue = "10") Integer l,
                                   @RequestParam(defaultValue = "id") String sortBy) {

        Page<Artist> pagedResult = artistService.getAll(p, l, sortBy);

        if (pagedResult.hasContent()) {
            return ResponseEntity.ok(pagedResult.getContent());
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ArrayList<>());
        }
    }

    @GetMapping(path = "/{id}/songs")
    @JsonView(JsonScope.Internal.class)
    private @ResponseBody
    ResponseEntity<Iterable<Song>> getAllSongsOfArtist(@PathVariable Integer id) {

        Optional<Artist> artistOptional = artistService.getById(id);

        if (artistOptional.isPresent()) {
            return ResponseEntity.ok(Objects.requireNonNull(artistService.getById(id).orElse(null)).getSongList());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping(path = "/{id}")
    @JsonView(JsonScope.Internal.class)
    private @ResponseBody ResponseEntity<Artist> getArtistById(@PathVariable Integer id) {
        Optional<Artist> optionalArtist = artistService.getById(id);
        return optionalArtist
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null));
    }

    // TODO: REMOVE FOR PRODUCTION
    @PostMapping(path = "/add")
    private @ResponseBody ResponseEntity<String> addNewArtist(@RequestParam String name) {
        try {
            artistService.saveArtist(name);
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("");
        }
        return ResponseEntity.ok("added Artist");
    }
}