package vn.hoangdung.projectJava.modules.users.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import vn.hoangdung.projectJava.modules.users.entities.UserCatalogue;
import vn.hoangdung.projectJava.modules.users.repositories.UserCatalogueRepository;
import vn.hoangdung.projectJava.modules.users.requests.UserCatalogue.StoreRequest;
import vn.hoangdung.projectJava.modules.users.requests.UserCatalogue.UpdateRequest;
import vn.hoangdung.projectJava.modules.users.resources.UserCatalogueResource;
import vn.hoangdung.projectJava.modules.users.services.interfaces.UserCatalogueInterface;
import vn.hoangdung.projectJava.resources.ApiResource;

@RestController
@RequestMapping("/api/v1")
public class UserCatalogueController {

    private final UserCatalogueInterface userCatalogueInterface;
    private final UserCatalogueRepository userCatalogueRepository;

    public UserCatalogueController(UserCatalogueInterface userCatalogueInterface, UserCatalogueRepository userCatalogueRepository) {
        this.userCatalogueInterface = userCatalogueInterface;
        this.userCatalogueRepository = userCatalogueRepository;
    }

    @PostMapping("/user-catalogue")
    public ResponseEntity<?> create(@Valid @RequestBody StoreRequest request) {
        UserCatalogue userCatalogue = userCatalogueInterface.create(request);
        UserCatalogueResource userCatalogueResource = UserCatalogueResource.builder()
                .id(userCatalogue.getId())
                .name(userCatalogue.getName())
                .publish(userCatalogue.getPublish())
                .build();
        ApiResource<UserCatalogueResource> response = ApiResource.ok(userCatalogueResource, "Thêm mới nhóm thành viên thành công");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/user-catalogue/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,
             @Valid @RequestBody UpdateRequest request) {

        try {
            UserCatalogue userCatalogue = userCatalogueInterface.update(id, request);
            UserCatalogueResource userCatalogueResource = UserCatalogueResource.builder()
                    .id(userCatalogue.getId())  
                    .name(userCatalogue.getName())
                    .publish(userCatalogue.getPublish())
                    .build();

            ApiResource<UserCatalogueResource> response = ApiResource.ok(userCatalogueResource, "Cập nhật nhóm thành viên thành công");
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ApiResource.error("NOT_FOUND", e.getMessage(), HttpStatus.NOT_FOUND)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                ApiResource.error("INTERNAL_SERVER_ERROR", "Có vấn đề khi cập nhật", HttpStatus.INTERNAL_SERVER_ERROR)
            );
        }
    }
    
}
