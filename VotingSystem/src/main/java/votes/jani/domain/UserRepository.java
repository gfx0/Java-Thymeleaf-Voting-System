package votes.jani.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import votes.jani.domain.User;

public interface UserRepository extends CrudRepository<User, Long> {
	User findByUsername(String username);
	List<User> findByRole(String role);
}
