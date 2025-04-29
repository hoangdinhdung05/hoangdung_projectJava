package vn.hoangdung.projectJava.resources;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResource<T> {
    
    private boolean success;
    private String message;
    private T data;
    private HttpStatus status;
    private LocalDateTime timestamp;
    private ErrorResource error;

    @Getter
    @Setter
    @NoArgsConstructor
    @JsonInclude
    public static class ErrorResource {
        private String code;
        private String message;
        private String details;
        
        public ErrorResource(String message) {
            this.message = message;
        }
        
        public ErrorResource(String code, String message) {
            this.code = code;
            this.message = message;
        }

        public ErrorResource(String code, String message, String details) {
            this.code = code;
            this.message = message;
            this.details = details;
        }
    }

    private ApiResource() {
        this.timestamp = LocalDateTime.now();
    }

    //Tạo lớp builder cho ApiResource thay vì dùng constructor
    public static <T> Builders<T> builder() {
        return new Builders<>();
    }


    public static class Builders<T> {

        private final ApiResource<T> resource;

        private Builders() {
            this.resource = new ApiResource<>();
        }

        public Builders<T> success(boolean success) {
            this.resource.success = success;
            return this;
        }

        public Builders<T> message(String message) {
            this.resource.message = message;
            return this;
        }

        public Builders<T> data(T data) {
            this.resource.data = data;
            return this;
        }

        public Builders<T> status(HttpStatus status) {
            this.resource.status = status;
            return this;
        }

        public Builders<T> error(ErrorResource error) {
            this.resource.error = error;
            return this;
        }

        public ApiResource<T> build() {
            return this.resource;
        }
    }

    public static <T> ApiResource<T> ok(T data, String message) {
        return ApiResource.<T>builder()
                .success(true)
                .data(data)
                .message(message)
                .status(HttpStatus.OK)
                .build();
    }

    public static <T> ApiResource<T> message(String message, HttpStatus status) {
        return ApiResource.<T>builder()
                .success(true)
                .message(message)
                .status(status)
                .build();
    }

    public static <T> ApiResource<T> error(String code, String message, HttpStatus status) {
        return ApiResource.<T>builder()
                .success(false)
                .error(new ErrorResource(code, message))
                .message(message)
                .status(status)
                .build();
    }
}

//có data
// {
//     "success": boolean,
//     "message": string,
//     "data": {
//         "id": 1,
//         "name": "John Doe",
//         "email": 
//     },

//     "status": 200,...,
//     "timestamp": ... 
// }

// //chỉ có message
// {
//     "success": boolean,
//     "message": string,
//     "status": 200,...,
//     "timestamp": ... 
// }

// //error
// {
//     "success": boolean,
//     "status": 422,...,
//     "error": {
//         "message": string,
//         "errors": {
//             "email": [
//                 "Email is required",
//                 "Email is invalid"
//             ],
//             "password": [
//                 "Password is required"
//             ]
//         }
//     },
//     "timestamp": ... 
// }