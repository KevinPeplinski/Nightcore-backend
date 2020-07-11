package ninja.peplinski.nightcore.services;

import ninja.peplinski.nightcore.model.Request;
import ninja.peplinski.nightcore.model.repositories.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class RequestService extends Listable<Request> {

    @Autowired
    private RequestRepository repository;

    public long getOpenRequestCount() {
        return repository.countByOpen(true);
    }

    public Page<Request> getAllOpenRequests(Integer pageNo, Integer pageSize, String sortBy) {
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

        return repository.findAllByOpen(paging, true);
    }

    public void closeRequest(Request request) {
        request.setOpen(false);
        repository.save(request);
    }
}
