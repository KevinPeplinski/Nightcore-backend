<p align="center">
<img src="https://raw.githubusercontent.com/KevinPeplinski/Nightcore-backend/master/src/main/resources/static/img/Icon-102.png" alt="BlastText" title="BlastText" width="80"/>Nightcore Backend 
</p>

<p align="center">
<img src="https://img.shields.io/badge/language-Java-red">
<img src="https://img.shields.io/badge/db-MySQL-blue">
</p>

Nightcore-Backend is used to manage content via a user interface and to serve the content via a REST Api.
The data that can be obtained via the REST Api should be usable by a corresponding companion app. 



## Requirements

- Java 8+
- MySQL
- Maven 

## Admin interface to manage content

Simple user interface to manage artists, their songs and the corresponding links to the Youtube video. Provides all common operations, such as creating, viewing, modifying and deleting entities. 
Users of the companion app have the possibility to make song suggestions, these suggestions are displayed visibly and can be quickly rejected or added to the database in the form of a new song/artist. The data can be searched and is displayed using a pagination. 

<p align="center">
<img src="https://raw.githubusercontent.com/KevinPeplinski/Nightcore-backend/master/src/main/resources/static/img/nightcore-backend-screenshot.png" alt="BlastText" title="BlastText" width="800"/>
</p>

## REST Api
The api exists to provide the data and functionality of the companion app.

---
### Universal Search
#### Search for any searchable entity
Generic search that applies to any `SearchableEntity`, such as Artist or Song. 
Returns json data for multiple `SearchableEntity`, each one being wrapped inside a SearchWrapper that holds the specific type and its content. 

```Java
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
```

- **URL** `/api/search`
- **Method** `GET`
- **Path Variable** `Required: q=[String]`
---
### Artists

#### All Artists
Returns json data for multiple artists by page, length and sort property. 
- **URL** `/api/artists/all`
- **Method** `GET`
- **Params** `Optional: p=[Integer] l=[Integer] sortBy=[String]`

#### Search Artists
Returns json data for multiple artists by search query, page, length and sort property. 
- **URL** `/api/artists/search`
- **Method** `GET`
- **Params** `Required: q=[String] Optional: p=[Integer] l=[Integer] sortBy=[String]`

#### Single Artist
Returns json data for a single artist by artist id. 
- **URL** `/api/artists/{id}`
- **Method** `GET`
- **Path Variable** `Required: id=[Integer]`

#### Single Artist Songs
Returns json data for all songs of a single artist by artist id. 
- **URL** `/api/artists/{id}/songs`
- **Method** `GET`
- **Path Variable** `Required: id=[Integer]`
---
### Songs

#### All Songs
Returns json data for multiple songs by page, length and sort property. 
- **URL** `/api/songs/all`
- **Method** `GET`
- **Params** `Optional: p=[Integer] l=[Integer] sortBy=[String]`

#### Search Songs
Returns json data for multiple songs by search query, page, length and sort property. 
- **URL** `/api/songs/search`
- **Method** `GET`
- **Params** `Required: q=[String] Optional: p=[Integer] l=[Integer] sortBy=[String]`

#### Single Song
Returns json data for a single song by artist id. 
- **URL** `/api/songs/{id}`
- **Method** `GET`
- **Path Variable** `Required: id=[Integer]`
---
### User Requests

#### Send Request based on youtube video
- **URL** `/api/request/add`
- **Method** `POST`
- **Params** `Required: url=[String] date=[Date(pattern="dd-MM-yyyy")]`