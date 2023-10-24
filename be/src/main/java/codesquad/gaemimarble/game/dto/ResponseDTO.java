package codesquad.gaemimarble.game.dto;

import lombok.Getter;

@Getter
public class ResponseDTO<T> {
	private String type;
	private T data;

	public ResponseDTO(String type, T data) {
		this.type = type;
		this.data = data;
	}
}
