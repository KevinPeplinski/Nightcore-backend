package ninja.peplinski.nightcore.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public class Listable<T> {

    @Autowired
    PagingAndSortingRepository<T, Integer> repository;

    public Page<T> getAll(Integer pageNo, Integer pageSize, String sortBy) {

        Pageable paging = PaginationService.constrainedPaging(pageNo, pageSize, sortBy);

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
