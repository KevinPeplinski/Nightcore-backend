package ninja.peplinski.nightcore.model;

import com.fasterxml.jackson.annotation.JsonView;
import ninja.peplinski.nightcore.model.view.JsonScope;
import ninja.peplinski.nightcore.model.specifications.SearchableInsideRelationshipChain;

import javax.persistence.*;
import java.util.List;

@Entity
public class Artist extends SearchableEntity implements SearchableInsideRelationshipChain {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(JsonScope.Public.class)
    private Integer id;
    @JsonView(JsonScope.Public.class)
    private String name;
    @JsonView(JsonScope.Internal.class)
    @OneToMany(targetEntity=Song.class, cascade = CascadeType.ALL)
    private List<Song> songList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Song> getSongList() {
        return songList;
    }

    public void setSongList(List<Song> songList) {
        this.songList = songList;
    }

    public void addSong(Song song) {
        this.songList.add(song);
    }

    public void removeSong(Song song) {
        this.songList.removeIf(x -> {
            return song.getId() == x.getId();
        });
    }

    @Override
    public String getKeyForSearchableProperty() {
        return "name";
    }
}
