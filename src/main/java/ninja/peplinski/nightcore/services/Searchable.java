package ninja.peplinski.nightcore.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public class Searchable <T> extends Listable<T> {

    @Autowired
    JpaSpecificationExecutor<T> repository;

    public Page<T> getAllSearched(Specification<T> specification, Integer pageNo, Integer pageSize, String sortBy) {

        Pageable paging = PaginationService.constrainedPaging(pageNo, pageSize, sortBy);

        return repository.findAll(specification, paging);
    }
}
