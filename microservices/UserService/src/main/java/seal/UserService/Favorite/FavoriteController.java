package seal.UserService.Favorite;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

import javax.validation.Valid;
import seal.UserService.Filter.TokenAuthenticationService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    @GetMapping("/favorites/user/{user_id}")
    public ResponseEntity<List<Favorite>> getAllFavorite(@PathVariable(name = "user_id") Long userId, HttpServletRequest request) {
        TokenAuthenticationService.validateJWTAuthentication(request);
        List<Favorite> favorites = favoriteService.getFavoriteByUserId(userId);
        return new ResponseEntity<List<Favorite>>(favorites, HttpStatus.OK);
    }

    @PostMapping("/favorite/user/{user_id}/subject")
    public ResponseEntity<Favorite> createFavorite(@PathVariable(name = "user_id") Long userId,  @Valid @RequestBody Favorite favorite, HttpServletRequest request) {
        TokenAuthenticationService.validateJWTAuthentication(request);
        Favorite favorite_object = favoriteService.createFavorite(userId, favorite);
        return new ResponseEntity<Favorite>(favorite_object, HttpStatus.OK);
    }

    @DeleteMapping("/favorite/{user_id}/{favorite_id}")
    public ResponseEntity<Favorite> deleteFavorite(@PathVariable(name = "favorite_id") Long id,  @PathVariable(name = "user_id") Long userId, HttpServletRequest request) {
        TokenAuthenticationService.validateJWTAuthentication(request);
        Favorite favorite_object = favoriteService.deleteFavoriteById(userId, id);
        return new ResponseEntity<Favorite>(favorite_object, HttpStatus.OK);
    }
}