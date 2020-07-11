package ninja.peplinski.nightcore.model.repositories;

import ninja.peplinski.nightcore.model.Request;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RequestRepository extends CrudRepository<Request, Integer>, PagingAndSortingRepository<Request, Integer> {
    public Page<Request> findAllByOpen(Pageable pageable, boolean open);
    public Long countByOpen(boolean open);
}
