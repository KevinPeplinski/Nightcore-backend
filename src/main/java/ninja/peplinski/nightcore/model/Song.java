package ninja.peplinski.nightcore.model;

import com.fasterxml.jackson.annotation.*;
import ninja.peplinski.nightcore.model.view.JsonScope;

import javax.persistence.*;

@Entity
public class Song extends SearchableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(JsonScope.Public.class)
    private Integer id;
    @JsonView(JsonScope.Public.class)
    private String title;
    @JsonView(JsonScope.Public.class)
    private String ytId;
    @Enumerated(EnumType.STRING)
    private Genre genre;
    private Integer clicks;
    @ManyToOne
    @JsonView(JsonScope.Public.class)
    @JsonIgnoreProperties({"songList"})
    private Artist artist;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Artist getArtist() {
        return artist;
    }

    public String getYtId() {
        return ytId;
    }

    public void setYtId(String ytId) {
        this.ytId = ytId;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Integer getClicks() {
        return clicks;
    }

    public void setClicks(Integer clicks) {
        this.clicks = clicks;
    }
}
