package testCase.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import testCase.security.model.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
