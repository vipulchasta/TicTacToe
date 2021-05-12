import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ConsoleGame {

	private static void waitForOneSec() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			System.out.println("Sleep Not Supported");
		}
	}

	private static void clearScreen() {
		try {
			System.out.print("\033[H\033[2J");
			System.out.flush();
			final String os = System.getProperty("os.name");
			if (os.contains("Windows"))
			{
				Runtime.getRuntime().exec("cls");
			}
			else
			{
				Runtime.getRuntime().exec("clear");
			}
		} catch (IOException e) {
			System.out.println("Clear Screen Not Supported");
		}
	}

	private static void printCurrentGamePad(List<List<Integer>> currentGamePad) {
		for (List<Integer> gameRow : currentGamePad) {
			for (Integer column : gameRow) {
				String markRepresentation = column.equals(0) ? " " : (column.equals(1) ? "0" : "x");
				System.out.print("| " + markRepresentation + " |");
			}
			System.out.println("\n---------------");
		}
	}

	public static void main(String[] args) {
		System.out.println("Welcome to TikTacToe");

		TicTacToe ticTacToe = new TicTacToeImpl();
		ticTacToe.setLoggingType(TicTacToe.LoggingType.ERROR);

		boolean clearScreenAndReprintGamePad = true;
		while(ticTacToe.getGameStatus().equals(TicTacToe.Status.IN_PROGRESS)) {
			if(clearScreenAndReprintGamePad) {
				clearScreen();
				printCurrentGamePad(ticTacToe.getCurrentGamePad());
				System.out.println("Your Turn: " + ticTacToe.getCurrentPlayer());
			}

			System.out.print("Enter Choice [0-8]: ");
			Scanner sc = new Scanner(System.in);
			try {
				Integer userChoice = sc.nextInt();
				ticTacToe.makeMove(userChoice);
				clearScreenAndReprintGamePad = true;
			} catch (TicTacToe.GameAlreadyFinished gameAlreadyFinished) {
				clearScreenAndReprintGamePad = false;
				System.out.println("Game Already Finished");
				break;
			} catch (InputMismatchException | TicTacToe.InvalidMove invalidMove) {
				clearScreenAndReprintGamePad = false;
				System.out.println("Invalid Move");
			}

		}

		clearScreen();
		printCurrentGamePad(ticTacToe.getCurrentGamePad());
		System.out.println("TikTacToe Result: " + ticTacToe.getGameStatus());

	}
}