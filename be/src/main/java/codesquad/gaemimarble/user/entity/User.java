package codesquad.gaemimarble.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false, length = 45, unique = true)
	private String playerId;
	@Column(length = 100)
	private String password;

	@Builder
	public User(Long id, String playerId, String password) {
		this.id = id;
		this.playerId = playerId;
		this.password = password;
	}
}
