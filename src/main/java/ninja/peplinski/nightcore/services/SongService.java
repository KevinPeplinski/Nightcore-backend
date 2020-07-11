package ninja.peplinski.nightcore.services;

import ninja.peplinski.nightcore.errors.AlreadyExistsException;
import ninja.peplinski.nightcore.errors.NoSuchArtistException;
import ninja.peplinski.nightcore.errors.NoSuchGenreException;
import ninja.peplinski.nightcore.model.Artist;
import ninja.peplinski.nightcore.model.Genre;
import ninja.peplinski.nightcore.model.Song;
import ninja.peplinski.nightcore.model.repositories.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SongService extends Listable<Song> {

    @Autowired
    private SongRepository repository;
    @Autowired
    private ArtistService artistService;

    @Override
    public void deleteById(Integer id) {
        Optional<Song> optionalSong =  repository.findById(id);
        optionalSong.ifPresent(song -> {
            song.getArtist().removeSong(song);
            artistService.updateArtist(song.getArtist());
            repository.delete(song);
        });
    }

    public void saveSong(String title, Integer artistId, String genre, String ytId, Integer clicks)
            throws AlreadyExistsException, NoSuchArtistException, NoSuchGenreException {

        Song song = new Song();

        if (repository.findByYtId(ytId).isPresent()) throw new AlreadyExistsException("");

        Optional<Artist> optionalArtist = artistService.getById(artistId);
        if (!optionalArtist.isPresent()) throw new NoSuchArtistException("");
        song.setArtist(optionalArtist.get());

        try {
            song.setGenre(Genre.valueOf(genre));
        } catch (IllegalArgumentException e) {
            throw new NoSuchGenreException("");
        }

        song.setClicks(clicks);
        song.setTitle(title);
        song.setYtId(ytId);

        repository.save(song);
    }

    public void saveSong(String title, Integer artistId, String genre, String ytId)
            throws AlreadyExistsException, NoSuchArtistException, NoSuchGenreException {

        this.saveSong(title, artistId, genre, ytId, 0);
    }

}
