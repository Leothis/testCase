package testCase.security.service;

import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import testCase.security.SecurityUtils;
import testCase.security.model.Authority;
import testCase.security.model.User;
import testCase.security.repository.AuthorityRepository;
import testCase.security.repository.UserRepository;

import java.util.Optional;
import testCase.security.rest.dto.UserDto;

@Service
@Transactional
public class UserService {

   private final UserRepository userRepository;
   private final AuthorityRepository authorityRepository;

   public UserService(UserRepository userRepository,
      AuthorityRepository authorityRepository) {
      this.userRepository = userRepository;
      this.authorityRepository = authorityRepository;
   }

   /**
    * Method to initialize sample database users and authorities
    * */
   @Transactional
   public Object initializeDatabase() {

      Authority adminRole = new Authority();
      adminRole.setName("ROLE_ADMIN");
      Optional<Authority> optionalAdminAuthority = authorityRepository.findById("ROLE_ADMIN");
      if (optionalAdminAuthority.isEmpty()) {
         authorityRepository.save(adminRole);
      }
      Authority endUserRole = new Authority();
      endUserRole.setName("ROLE_USER");
      Optional<Authority> optionalUserAuthority = authorityRepository.findById("ROLE_USER");
      if (optionalUserAuthority.isEmpty()) {
         authorityRepository.save(endUserRole);
      }
      Optional<Authority> adminRoleToSet = authorityRepository.findById("ROLE_ADMIN");
      Optional<Authority> userRoleToSet = authorityRepository.findById("ROLE_USER");
      if (adminRoleToSet.isPresent() && userRoleToSet.isPresent()) {
         Set<Authority> adminAuthorities = Set.of(adminRoleToSet.get(), userRoleToSet.get());
         Set<Authority> endUserAuthorities = Set.of(userRoleToSet.get());

         if (userRepository.findById("b4e8e63c-2220-11ec-9621-0242ac130002").isEmpty()) {

            User admin = new User();
            admin.setId("b4e8e63c-2220-11ec-9621-0242ac130002");
            admin.setUsername("admin");
            admin.setPassword("$2a$12$LCnZNPvD1pV4VfkkAG6biugkeRc4Par.bs9KgNulGMfpV1G2HARmO");
            admin.setFirstname("admin");
            admin.setLastname("admin");
            admin.setEmail("admin@admin.com");
            admin.setActivated(true);
            admin.setAuthorities(adminAuthorities);
            userRepository.save(admin);
         }

         if (userRepository.findById("d31327a8-2220-11ec-9621-0242ac130002").isEmpty()) {

            User endUser = new User();
            endUser.setId("d31327a8-2220-11ec-9621-0242ac130002");
            endUser.setUsername("user");
            endUser.setPassword("$2a$12$hQV2PaMnJOueGDvlN7xNRu.683KO1UkfTOuE4DexyvcC70.Zx0yGm");
            endUser.setFirstname("user");
            endUser.setLastname("user");
            endUser.setEmail("user@user.com");
            endUser.setActivated(true);
            endUser.setAuthorities(endUserAuthorities);
            userRepository.save(endUser);
         }



      }
      return "Database users initialized succesfully.";
   }

   /**
    * Method to create end users by admin users, password encrpytion is done bcrpyt
    * https://bcrypt-generator.com/ can be used for encrypting the password, only end users can be
    * created.
    */
   @Transactional
   public Object createEndUser(UserDto userDto) {
      Optional<Authority> authority = authorityRepository.findById("ROLE_USER");
      User user = new User();
      user.setUsername(userDto.getUsername());
      user.setPassword(userDto.getPassword());
      user.setFirstname(userDto.getFirstname());
      user.setLastname(userDto.getLastname());
      user.setEmail(userDto.getEmail());
      user.setActivated(userDto.isActivated());
      if (authority.isPresent()) {
         Set<Authority> authorities = Set.of(authority.get());
         user.setAuthorities(authorities);
      }
      userRepository.save(user);
      return "User created succesfully.";
   }

   /**
    * Method to get user information by providing userId, both admins and users can get information
    * of any user.
    */
   @Transactional(readOnly = true)
   public Object getUserById(String id) {
      Optional<User> user = userRepository.findById(id);
      if (user.isPresent()) {
         return user.get();
      } else {
         return "Requested user is not available";
      }
   }

   /**
    * Method to update user information by providing userId and update information. Only users with
    * admin privileges have access to this method.
    */
   @Transactional
   public Object updateUserById(UserDto userDto, String id) {
      Optional<Authority> authority = authorityRepository.findById("ROLE_USER");
      Optional<User> user = userRepository.findById(id);
      if (user.isPresent()) {
         User userToBeUpdated = user.get();
         if (userDto.getAuthorities().isEmpty() && authority.isPresent()) {
            Set<Authority> authorities = Set.of(authority.get());
            userToBeUpdated.setAuthorities(authorities);
         } else {
            userToBeUpdated.setAuthorities(userDto.getAuthorities());
         }
         userToBeUpdated.setUsername(userDto.getUsername());
         userToBeUpdated.setPassword(userDto.getPassword());
         userToBeUpdated.setFirstname(userDto.getFirstname());
         userToBeUpdated.setLastname(userDto.getLastname());
         userToBeUpdated.setEmail(userDto.getEmail());
         userToBeUpdated.setActivated(userDto.isActivated());
         userRepository.save(userToBeUpdated);
         return userToBeUpdated;
      } else {
         return "Requested user is not available";
      }
   }

   /**
    * Method to delete a user by providing userId. Only users with admin privileges have access to
    * this method.
    */
   @Transactional
   public Object deleteByUserId(String id) {
      Optional<User> user = userRepository.findById(id);
      if (user.isPresent()) {
         userRepository.deleteById(id);
         return "User deleted succesfully";
      } else {
         return "There is no such user to delete";

      }
   }

   @Transactional(readOnly = true)
   public Optional<User> getUserWithAuthorities() {
      return SecurityUtils.getCurrentUsername()
         .flatMap(userRepository::findOneWithAuthoritiesByUsername);
   }
}
