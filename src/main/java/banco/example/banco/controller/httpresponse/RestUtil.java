package banco.example.banco.controller.httpresponse;

import org.springframework.http.ResponseEntity;

import java.util.Optional;

public class RestUtil {

    public static <T> ResponseEntity<T> ifComflict409(Optional<T> optionalValue) {
        return optionalValue.map(ResponseEntity::ok).orElseGet(ResponseEntity.status(409)::build);
    }

    public static <T> ResponseEntity<T> ifBadRequest400(Optional<T> optionalValue) {
        return optionalValue.map(ResponseEntity::ok).orElseGet(ResponseEntity.status(400)::build);
    }

    public static <T> ResponseEntity<T> ifNotFound404(Optional<T> optionalValue) {
        return optionalValue.map(ResponseEntity::ok).orElseGet(ResponseEntity.status(404)::build);
    }
}
