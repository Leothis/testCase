package testCase.security.rest.dto;

import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.NotNull;
import testCase.security.model.Authority;

public class UserDto {

   public UserDto() {
   }

   @NotNull
   private String username;

   @NotNull
   private String password;

   @NotNull
   private String firstname;

   @NotNull
   private String lastname;

   @NotNull
   private String email;

   @NotNull
   private boolean activated;

   private Set<Authority> authorities = new HashSet<>();




   public String getUsername() {
      return username;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public String getFirstname() {
      return firstname;
   }

   public void setFirstname(String firstname) {
      this.firstname = firstname;
   }

   public String getLastname() {
      return lastname;
   }

   public void setLastname(String lastname) {
      this.lastname = lastname;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public boolean isActivated() {
      return activated;
   }

   public void setActivated(boolean activated) {
      this.activated = activated;
   }

   public Set<Authority> getAuthorities() {
      return authorities;
   }

   public void setAuthorities(Set<Authority> authorities) {
      this.authorities = authorities;
   }
}
