package ninja.peplinski.nightcore.services;

import ninja.peplinski.nightcore.model.Request;
import ninja.peplinski.nightcore.model.repositories.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import org.springframework.stereotype.Service;

@Service
public class RequestService extends Listable<Request> {

    @Autowired
    private RequestRepository repository;

    public long getOpenRequestCount() {
        return repository.countByOpen(true);
    }

    public Page<Request> getAllOpenRequests(Integer pageNo, Integer pageSize, String sortBy) {

        return repository.findAllByOpen(PaginationService.constrainedPaging(pageNo, pageSize, sortBy), true);
    }

    public void closeRequest(Request request) {
        request.setOpen(false);
        repository.save(request);
    }
}
