package vn.hoangdung.projectJava.modules.users.services.interfaces;

import java.util.Map;

import org.springframework.data.domain.Page;

import vn.hoangdung.projectJava.modules.users.entities.UserCatalogue;
import vn.hoangdung.projectJava.modules.users.requests.UserCatalogue.StoreRequest;
import vn.hoangdung.projectJava.modules.users.requests.UserCatalogue.UpdateRequest;

public interface UserCatalogueInterface {

    UserCatalogue create(StoreRequest request);
    UserCatalogue update(Long id, UpdateRequest request);
    Page<UserCatalogue> paginate(Map<String, String[]> parameters);
}
