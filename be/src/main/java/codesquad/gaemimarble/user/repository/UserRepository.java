package codesquad.gaemimarble.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import codesquad.gaemimarble.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByPlayerId(String playerId);

	boolean existsByPlayerId(String playerId);

	@Query("select u.id from User u where u.playerId = :playerId")
	Long findIdByPlayerId(String playerId);
}
