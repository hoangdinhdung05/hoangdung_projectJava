package vn.hoangdung.projectJava.modules.users.resources;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class UserCatalogueResource {
    private final Long id;
    private final String name;
    private final Integer publish; 
}
