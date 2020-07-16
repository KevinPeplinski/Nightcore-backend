package ninja.peplinski.nightcore.controller;

import com.fasterxml.jackson.annotation.JsonView;
import ninja.peplinski.nightcore.errors.AlreadyExistsException;
import ninja.peplinski.nightcore.errors.NoSuchArtistException;
import ninja.peplinski.nightcore.errors.NoSuchGenreException;
import ninja.peplinski.nightcore.model.Song;
import ninja.peplinski.nightcore.model.view.JsonScope;
import ninja.peplinski.nightcore.services.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

import static ninja.peplinski.nightcore.model.specifications.SongSpecifications.songWithTitle;

@RestController
@RequestMapping(path = "/api/songs")
public class SongController {

    @Autowired
    private SongService songService;

    @GetMapping(path = "/search")
    @JsonView(JsonScope.Public.class)
    private @ResponseBody ResponseEntity<Iterable<Song>> searchArtists(@RequestParam String q,
                                                                       @RequestParam(defaultValue = "1") Integer p,
                                                                       @RequestParam(defaultValue = "10") Integer l,
                                                                       @RequestParam(defaultValue = "id") String sortBy) {

        Page<Song> pagedResult = songService.getAllSearched(songWithTitle(q), p, l , sortBy);

        if (pagedResult.hasContent()) {
            return ResponseEntity.ok(pagedResult.getContent());
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ArrayList<>());
        }
    }

    @GetMapping(path = "/all")
    @JsonView(JsonScope.Public.class)
    private @ResponseBody ResponseEntity<Iterable<Song>> getAllSongs(@RequestParam(defaultValue = "1") Integer p,
                                                                     @RequestParam(defaultValue = "10") Integer l,
                                                                     @RequestParam(defaultValue = "id") String sortBy) {

        Page<Song> pagedResult = songService.getAll(p, l, sortBy);

        if (pagedResult.hasContent()) {
            return ResponseEntity.ok(pagedResult.getContent());
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ArrayList<>());
        }
    }

    @GetMapping(path = "/{id}")
    @JsonView(JsonScope.Internal.class)
    private @ResponseBody ResponseEntity<Song> getSongById(@PathVariable Integer id) {
        Optional<Song> optionalSong = songService.getById(id);
        return optionalSong
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null));
    }

    @PostMapping(path = "/add")
    private @ResponseBody ResponseEntity<String> addNewSong(@RequestParam String title, @RequestParam Integer artistId, @RequestParam String genre, @RequestParam String ytId) {
        try {
            songService.saveSong(title, artistId, genre, ytId);
        } catch (AlreadyExistsException | NoSuchGenreException | NoSuchArtistException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("");
        }
        return ResponseEntity.ok("added Artist");
    }
}
