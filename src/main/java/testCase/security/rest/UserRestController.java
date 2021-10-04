package testCase.security.rest;

import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import testCase.security.rest.dto.UserDto;
import testCase.security.service.UserService;

@RestController
@RequestMapping()
public class UserRestController {

   private final UserService userService;

   public UserRestController(UserService userService) {
      this.userService = userService;
   }


   @GetMapping("/initialize-database")
   public Object initializeDatabase(){
      return userService.initializeDatabase();
   }


   @GetMapping("/user/{user_id}")
   public ResponseEntity<Object> getActualUser(@PathVariable String user_id) {
      return ResponseEntity.ok(userService.getUserById(user_id));
   }

   @PreAuthorize("hasRole('ROLE_ADMIN')")
   @PostMapping("/user")
   public Object createUser(@Valid @RequestBody UserDto userDto) {
      return userService.createEndUser(userDto);
   }

   @PreAuthorize("hasRole('ROLE_ADMIN')")
   @PutMapping("/user/{user_id}")
   public Object updateUser(@RequestBody UserDto userDto,
      @PathVariable String user_id) {
      return userService.updateUserById(userDto, user_id);
   }

   @PreAuthorize("hasRole('ROLE_ADMIN')")
   @DeleteMapping("/user/{user_id}")
   public Object deleteUser(@PathVariable String user_id) {
      return userService.deleteByUserId(user_id);
   }
}
