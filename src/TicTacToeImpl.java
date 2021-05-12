import java.util.*;

public class TicTacToeImpl implements TicTacToe {
	LoggingType loggingType = LoggingType.ERROR;

	List<List<Integer>> currentGamePad;
	Status status = Status.IN_PROGRESS;
	Player player = Player.PLAYER_1;

	Integer sumDiagonal = 0;
	Integer sumReverseDiagonal = 0;
	List<Integer> sumRow = new ArrayList<>(Arrays.asList(0, 0, 0));
	List<Integer> sumColumn = new ArrayList<>(Arrays.asList(0, 0, 0));

	Integer remainingMove = 9;

	@Override
	public void log(LoggingType logType, String message) {
		boolean logToBePrinted = false;

		if(logType.equals(LoggingType.ERROR)) {
			logToBePrinted = true;
		} else if(loggingType.equals(LoggingType.INFO) && logType.equals(LoggingType.INFO)) {
			logToBePrinted = true;
		} else if(loggingType.equals(LoggingType.DEBUG)) {
			logToBePrinted = true;
		}

		if(logToBePrinted) {
			System.out.println(logType.toString() + " :: " + message);
		}
	}

	@Override
	public LoggingType getLoggingType() {
		return loggingType;
	}

	@Override
	public void setLoggingType(LoggingType logType) {
		loggingType = logType;
	}

	public TicTacToeImpl() {
		this.currentGamePad = new ArrayList<> (
											Arrays.asList(
													Arrays.asList(0, 0, 0),
													Arrays.asList(0, 0, 0),
													Arrays.asList(0, 0, 0)
											)
										);
		log(LoggingType.DEBUG, "TicTacToe Initialized");
	}

	@Override
	public void makeMove(Integer currentUserMove) throws GameAlreadyFinished, InvalidMove {
		if(currentUserMove < 0 || currentUserMove > 8) {
			log(LoggingType.DEBUG, "TicTacToe.makeMove() => InvalidMove(), Condition not matching -> currentUserMove < 0 || currentUserMove > 8");
			throw new InvalidMove();
		}
		int rowIndex = currentUserMove / 3;

		currentUserMove -= rowIndex*3;
		int columnIndex = currentUserMove % 3;
		makeMove(rowIndex, columnIndex);
	}

	@Override
	public void makeMove(Integer rowIndex, Integer columnIndex) throws GameAlreadyFinished, InvalidMove {
		log(LoggingType.DEBUG, "TicTacToe.makeMove() => currentPlayer: " + getCurrentPlayer() + "rowIndex: " + rowIndex + ", columnIndex: " + columnIndex);
		if(!this.status.equals(Status.IN_PROGRESS)) {
			/* Game already finished */
			log(LoggingType.DEBUG, "TicTacToe.makeMove() => GameAlreadyFinished()");
			throw new GameAlreadyFinished();
		}

		Integer currentMark;
		if(player.equals(Player.PLAYER_1)) {
			currentMark = 1;
		} else {
			currentMark = -1;
		}

		if(this.currentGamePad.get(rowIndex).get(columnIndex).equals(0)) {
			this.currentGamePad.get(rowIndex).set(columnIndex, currentMark);
		} else {
			/* Someone already played on the given place */
			log(LoggingType.DEBUG, "TicTacToe.makeMove() => InvalidMove(), Someone already played on the given place");
			throw new InvalidMove();
		}

		/* Diagonal Learning */
		if(rowIndex.equals(columnIndex)) {
			sumDiagonal += currentMark;
		}

		/* Reverse Diagonal Learning */
		if(rowIndex.equals( 2 - columnIndex )) {
			sumReverseDiagonal += currentMark;
		}

		/* Row & Column wise Learning */
		sumRow.set(rowIndex, sumRow.get(rowIndex) + currentMark);
		sumColumn.set(columnIndex, sumColumn.get(columnIndex) + currentMark);

		boolean userWon = false;

		/* Check if current user Won */
		if(Math.abs(sumDiagonal) == 3 || Math.abs(sumReverseDiagonal) == 3) {
			userWon = true;
		}
		if(Math.abs(sumRow.get(rowIndex)) == 3 || Math.abs(sumColumn.get(columnIndex)) == 3) {
			userWon = true;
		}

		if(userWon) {
			if(player.equals(Player.PLAYER_1)) {
				status = Status.WON_PLAYER_1;
			} else if (player.equals(Player.PLAYER_2)) {
				status = Status.WON_PLAYER_2;
			}
		}

		if(remainingMove > 1) {
			remainingMove--;

			if(player.equals(Player.PLAYER_2)) {
				player = Player.PLAYER_1;
			} else {
				player = Player.PLAYER_2;
			}
		} else {
			status = Status.DRAW;
		}
	}

	@Override
	public List<List<Integer>> getCurrentGamePad() {
		return this.currentGamePad;
	}

	@Override
	public Status getGameStatus() {
		return this.status;
	}

	@Override
	public Player getCurrentPlayer() {
		return this.player;
	}
}