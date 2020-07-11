package ninja.peplinski.nightcore.controller;

import ninja.peplinski.nightcore.model.Artist;
import ninja.peplinski.nightcore.model.Genre;
import ninja.peplinski.nightcore.model.Song;
import ninja.peplinski.nightcore.model.repositories.ArtistRepository;
import ninja.peplinski.nightcore.model.repositories.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/api/songs")
public class SongController {

    @Autowired
    private SongRepository songRepository;
    @Autowired
    private ArtistRepository artistRepository;

    @GetMapping(path = "/all")
    private @ResponseBody Iterable<Song> getAllSongs() {
        return songRepository.findAll();
    }

    @PostMapping(path = "/add")
    private @ResponseBody String addNewSong(@RequestParam String title, @RequestParam Integer artistId, @RequestParam String genre, @RequestParam String ytId) {
        Song song = new Song();
        song.setTitle(title);
        song.setYtId(ytId);

        Optional<Artist> optionalArtist = artistRepository.findById(artistId);

        try {
            song.setGenre(Genre.valueOf(genre));
        } catch (IllegalArgumentException e) {
            return "Illegal Genre:" + genre;
        }

        if (optionalArtist.isPresent()) {
            song.setArtist(optionalArtist.get());
            optionalArtist.get().addSong(song);
        } else {
            return "Artist unavailable";
        }
        songRepository.save(song);
        return "Saved " + song;
    }
}
