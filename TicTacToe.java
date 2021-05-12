import java.util.List;

public interface TicTacToe {
	enum LoggingType {
		ERROR,
		INFO,
		DEBUG
	}

	void log(LoggingType logType, String message);

	LoggingType getLoggingType();

	void setLoggingType(LoggingType logType);

	class GameAlreadyFinished extends Exception {
		public GameAlreadyFinished() {
			super();
		}
		public GameAlreadyFinished(String message) {
			super(message);
		}
	}

	class InvalidMove extends Exception {
		public InvalidMove() {
			super();
		}
		public InvalidMove(String message) {
			super(message);
		}
	}

	enum Status {
		WON_PLAYER_1,
		WON_PLAYER_2,
		DRAW,
		IN_PROGRESS
	}

	enum Player {
		PLAYER_1,
		PLAYER_2
	}

	void makeMove(Integer currentUserMove) throws GameAlreadyFinished, InvalidMove;

	void makeMove(Integer rowIndex, Integer columnIndex) throws GameAlreadyFinished, InvalidMove;

	List<List<Integer>> getCurrentGamePad();

	Status getGameStatus();

	Player getCurrentPlayer();
}