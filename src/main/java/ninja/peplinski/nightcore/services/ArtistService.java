package ninja.peplinski.nightcore.services;

import ninja.peplinski.nightcore.errors.AlreadyExistsException;
import ninja.peplinski.nightcore.model.Artist;
import ninja.peplinski.nightcore.model.repositories.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArtistService extends Searchable<Artist> {

    @Autowired
    ArtistRepository repository;

    public void saveArtist(String name) throws AlreadyExistsException {

        Artist artist = new Artist();
        artist.setName(name);

        if (repository.findByName(artist.getName()).isPresent()) throw new AlreadyExistsException("");

        repository.save(artist);
    }

    public void updateArtist(Artist artist) {
        repository.save(artist);
    }
}
