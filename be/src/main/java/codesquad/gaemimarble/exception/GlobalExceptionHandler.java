package codesquad.gaemimarble.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import codesquad.gaemimarble.game.controller.SocketDataSender;

@RestControllerAdvice
public class GlobalExceptionHandler {

	private final SocketDataSender socketDataSender;

	public GlobalExceptionHandler(SocketDataSender socketDataSender) {
		this.socketDataSender = socketDataSender;
	}

	@ExceptionHandler(CustomException.class)
	public void handleCustomException(CustomException ex) {
		socketDataSender.sendErrorMessage(ex.getGameId(), ex.getPlayerId(), ex.getMessage());
	}
}
