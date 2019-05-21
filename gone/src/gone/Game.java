package gone;

import java.util.HashSet;
import java.util.Set;

/* 
 * Lucas: playWith, replaceBlackPebblesAroundLocation, edits from last week
 * Jess: boardContainsBlackPebbles, replaceIfBlackPebble, offsetBy
 * all types of testing for each method
 */

public class Game {
	
	public static Result playWith(Set<Location> whitePebbleLocations, boolean[][] blackPebbles) {
		Set<Location> newWhitePebbleLocations = new HashSet<Location>();
		
		for (Location whitePebble : whitePebbleLocations) {
			replaceBlackPebblesAroundLocation(whitePebble, blackPebbles, newWhitePebbleLocations);
		}
		
		if (newWhitePebbleLocations.isEmpty()) {
			// Will only be true whenever the for loop doesn't run
			return new Result(0, boardContainsBlackPebbles(blackPebbles));
		} else {
			Result nextTurn = playWith(newWhitePebbleLocations, blackPebbles);
			nextTurn.setNumberOfIterations(nextTurn.getNumberOfIterations() + 1);
			
			return nextTurn;
		}
	}
	
	private static boolean boardContainsBlackPebbles(boolean[][] blackPebbles) {
		// Improve variable names
		for (boolean[] row : blackPebbles) {
			for (boolean spot : row) {
				if (spot == true) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	private static void replaceBlackPebblesAroundLocation(Location location, boolean[][] blackPebbles, Set<Location> newWhitePebbles) {
		Set<Location> adjacentLocations = new HashSet<Location>();
		adjacentLocations.add(location.offsetBy(-1, 0));	// left
		adjacentLocations.add(location.offsetBy(1, 0));		// right
		adjacentLocations.add(location.offsetBy(0, -1));	// above
		adjacentLocations.add(location.offsetBy(0, 1));		// below
		
		for (Location adjacentLocation : adjacentLocations) {
			if (locationIsOnBoard(adjacentLocation, blackPebbles)) {
				replaceIfBlackPebble(adjacentLocation, blackPebbles, newWhitePebbles);
			}
		}
	}
	
	private static boolean locationIsOnBoard(Location location, boolean[][] board) {
		// Follow number line
		// Pull out into its own method
		return location.getRow() >= 0 && location.getRow() < board.length && location.getColumn() >= 0 && location.getColumn() < board[0].length;
	}
	
	private static void replaceIfBlackPebble(Location location, boolean[][] blackPebbles, Set<Location> newWhitePebbles) {
		if (blackPebbles[location.getRow()][location.getColumn()]) {
			blackPebbles[location.getRow()][location.getColumn()] = false;
			newWhitePebbles.add(location);
		}
	}
	
	public static class Result {
		
		private int numberOfIterations;
		private boolean blackPebblesLeft;
		
		/**
		 * @param numberOfIterations
		 * @param blackPebblesLeft
		 */
		public Result(int numberOfIterations, boolean blackPebblesLeft) {
			this.numberOfIterations = numberOfIterations;
			this.blackPebblesLeft = blackPebblesLeft;
		}
		
		/**
		 * @return numberOfIterations
		 */
		public int getNumberOfIterations() {
			return numberOfIterations;
		}
		
		/**
		 * @param numberOfIterations
		 */
		public void setNumberOfIterations(int numberOfIterations) {
			this.numberOfIterations = numberOfIterations;
		}
		
		/**
		 * @return blackPebblesLeft
		 */
		public boolean isBlackPebblesLeft() {
			return blackPebblesLeft;
		}
		
		/**
		 * @param blackPebblesLeft
		 */
		public void setBlackPebblesLeft(boolean blackPebblesLeft) {
			this.blackPebblesLeft = blackPebblesLeft;
		}
		
	}
	
	public static class TestHook {
		
		public static boolean locationIsOnBoard(Location location, boolean[][] board) {
			return Game.locationIsOnBoard(location, board);
		}
		
		public static void replaceBlackPebblesAroundLocation(Location location, boolean[][] blackPebbles, Set<Location> newWhitePebbles) {
			Game.replaceBlackPebblesAroundLocation(location, blackPebbles, newWhitePebbles);
		}
		
		public static boolean boardContainsBlackPebbles(boolean[][] blackPebbles) {
			return Game.boardContainsBlackPebbles(blackPebbles);
		}
		
		public static void replaceIfBlackPebble(Location location, boolean[][] blackPebbles, Set<Location> newWhitePebbles) {
			Game.replaceIfBlackPebble(location, blackPebbles, newWhitePebbles);
		}
		
	}
	
}
