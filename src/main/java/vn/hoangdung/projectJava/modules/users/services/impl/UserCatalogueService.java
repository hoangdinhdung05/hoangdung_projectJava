package vn.hoangdung.projectJava.modules.users.services.impl;

import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityNotFoundException;
import vn.hoangdung.projectJava.modules.users.entities.UserCatalogue;
import vn.hoangdung.projectJava.modules.users.repositories.UserCatalogueRepository;
import vn.hoangdung.projectJava.modules.users.requests.UserCatalogue.StoreRequest;
import vn.hoangdung.projectJava.modules.users.requests.UserCatalogue.UpdateRequest;
import vn.hoangdung.projectJava.modules.users.services.interfaces.UserCatalogueInterface;
import vn.hoangdung.projectJava.services.BaseService;

@Service
public class UserCatalogueService extends BaseService implements UserCatalogueInterface {

    private final UserCatalogueRepository userCatalogueRepository;

    @Override
    public Page<UserCatalogue> paginate(Map<String, String[]> parameters) {

        int page = parameters.containsKey("page") ? Integer.parseInt(parameters.get("page")[0]) : 1;
        int perpage = parameters.containsKey("perpage") ? Integer.parseInt(parameters.get("perpage")[0]) : 10;

        String sortParam = parameters.containsKey("sort") ? parameters.get("sort")[0] : null;

        Sort sort = createSort(sortParam);
        Pageable pageable = PageRequest.of(page - 1, perpage, sort);
        return this.userCatalogueRepository.findAll(pageable);
    }

    public UserCatalogueService(UserCatalogueRepository userCatalogueRepository) {
        this.userCatalogueRepository = userCatalogueRepository;
    }

    @Override
    @Transactional
    public UserCatalogue create(StoreRequest request) {
        try {
            UserCatalogue userCatalogue = UserCatalogue.builder()
                    .name(request.getName())
                    .publish(request.getPublish())
                    .build();
            return this.userCatalogueRepository.save(userCatalogue);
        } catch (Exception e) {
            throw new RuntimeException("Error " + e.getMessage());
        }
    }
    
    @Override
    @Transactional
    public UserCatalogue update(Long id, UpdateRequest request) {
        UserCatalogue userCatalogue = this.userCatalogueRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Nhóm thành viên không tồn tại"));
        UserCatalogue payLoad = userCatalogue.toBuilder()
                .name(request.getName())
                .publish(request.getPublish())
                .build();
        return this.userCatalogueRepository.save(payLoad);
    }

}
