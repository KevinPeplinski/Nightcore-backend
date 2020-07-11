package ninja.peplinski.nightcore.services;

import ninja.peplinski.nightcore.model.Artist;
import ninja.peplinski.nightcore.model.repositories.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public class Listable<T> {

    @Autowired
    PagingAndSortingRepository<T, Integer> repository;

    public Page<T> getAll(Integer pageNo, Integer pageSize, String sortBy) {
        if (pageNo < 1) {
            pageNo = 1;
        }

        if (pageSize < 10) {
            pageSize = 10;
        }

        if (pageSize > 50) {
            pageSize = 50;
        }

        Pageable paging = PageRequest.of(pageNo - 1, pageSize, Sort.by(sortBy));

        return repository.findAll(paging);
    }

    public Optional<T> getById(Integer id) {
        return repository.findById(id);
    }

    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    public long getCount() {
        return repository.count();
    }
}
