package gone;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.*;

public class GameTest {
	
	private int massiveBoardSize;
	private boolean[][] massiveBoard;
	
	// Helper method
	private Set<Location> makePebbleSet(Location... locations) {
		Set<Location> set = new HashSet<>();
		for (Location location : locations) {
			set.add(location);
		}
		return set;
	}
	
	@Before
	public void setup() {
		massiveBoardSize = 5001;
		massiveBoard = new boolean[massiveBoardSize][massiveBoardSize];
		for (int i = 0;i < massiveBoardSize;i++) {
			for (int j = 0;j < massiveBoardSize;j++) {
				massiveBoard[i][j] = true;
			}
		}
		
		massiveBoard[(massiveBoardSize - 1) / 2][(massiveBoardSize - 1) / 2] = false;
	}
	
	/**
	 * Method: playWith
	 * 
	 * Structured basis: nominal case, all boolean conditions true: white pebbles exist but no pebbles replaced
	 */
	@Test
	public void playWithNominalTest() {
		Set<Location> whitePebbleLocations = makePebbleSet(new Location(0, 0));
		boolean[][] blackPebbles = new boolean[3][3];
		blackPebbles[2][2] = true;
		
		Game.Result result = Game.playWith(whitePebbleLocations, blackPebbles);
		
		assertEquals(0, result.getNumberOfIterations());
		assertTrue(result.isBlackPebblesLeft());
		assertTrue(blackPebbles[2][2]);
	}
	
	/**
	 * Method: playWith
	 * 
	 * Structured basis: for condition is false (no white pebbles)
	 */
	@Test
	public void playWithNoWhitePebblesShouldDoNothing() {
		Set<Location> whitePebbleLocations = makePebbleSet();
		boolean[][] blackPebbles = new boolean[3][3];
		blackPebbles[2][2] = true;
		
		Game.Result result = Game.playWith(whitePebbleLocations, blackPebbles);
		
		assertEquals(0, result.getNumberOfIterations());
		assertTrue(result.isBlackPebblesLeft());
		assertTrue(blackPebbles[2][2]);
	}
	
	/**
	 * Method: playWith
	 * 
	 * Structured basis: if condition is false: replacements made
	 * Good data: arrangement with replacements to be made
	 */
	@Test
	public void playWithExampleShouldWorkAsExpected() {
		Set<Location> whitePebbleLocations = makePebbleSet(new Location(0, 1), new Location(0, 2));
		boolean[][] blackPebbles = { { true, false, false }, { true, true, true }, { true, false, false }, { false, false, true } };
		
		Game.Result result = Game.playWith(whitePebbleLocations, blackPebbles);
		assertEquals(3, result.getNumberOfIterations());
		assertTrue(result.isBlackPebblesLeft());
		assertTrue(blackPebbles[3][2]);
	}
	
	/**
	 * Method: playWith
	 * 
	 * Bad data: whitePebbleLocations is null
	 */
	@Test (expected = NullPointerException.class)
	public void playWithNullWhitePebblesShouldFail() {
		Game.playWith(null, new boolean[3][3]);
	}
	
	/**
	 * Method: playWith
	 * 
	 * Stress test: extremely large board, with a high number of replacements
	 */
	@Test
	public void playWithMassiveBoard() {
		Set<Location> whitePebbleLocations = makePebbleSet(new Location((massiveBoardSize - 1) / 2, (massiveBoardSize - 1) / 2));
		
		Game.Result result = Game.playWith(whitePebbleLocations, massiveBoard);
		assertEquals(massiveBoardSize - 1, result.getNumberOfIterations());
		assertFalse(result.isBlackPebblesLeft());
	}
	
	// At the end, any pebble that's white should be touching only other white pebbles
	// Black pebbles only touching other black pebbles
	// There shouldn't be any pebbles in new locations, only color should change
	
	/**
	 * Method: boardContainsBlackPebbles
	 * 
	 * Bad data: blackPebbles is null
	 * Boundary analysis: outer for each loop skipped
	 */
	@Test (expected = NullPointerException.class)
	public void nullFailureBoardContainsBlackPebbles() {
		Game.TestHook.boardContainsBlackPebbles(null);
	}
	
	/**
	 * Method: boardContainsBlackPebbles
	 * 
	 * Structured basis: nominal case, boolean condition is true, blackPebbles is not empty
	 * Good data: nominal case: 1 black pebble
	 * Boundary analysis: multiple passes through outer for each loop
	 * Compound boundary analysis: multiple passes through inner for each loop
	 */
	@Test 
	public void boardContainsBlackPebbles() {
		boolean[][] blackPebbles = new boolean[3][3];
		blackPebbles[2][2] = true;
		assertTrue(Game.TestHook.boardContainsBlackPebbles(blackPebbles));
	}
	
	/**
	 * Method: boardContainsBlackPebbles
	 * 
	 * Structured basis: nominal case, boolean condition is true, blackPebbles is empty
	 * Good data: nominal case: 0 black pebbles
	 * Boundary analysis: multiple passes through outer for each loop
	 * Compound boundary analysis: multiple passes through inner for each loop
	 */
	@Test 
	public void boardContainsNoBlackPebbles() {
		assertFalse(Game.TestHook.boardContainsBlackPebbles(new boolean[3][3]));
	}
	
	/**
	 * Method: boardContainsBlackPebbles
	 * 
	 * Structured basis: outer for loop condition not met
	 * Bad data: too little data
	 * Boundary analysis: 0 passes through outer loop
	 * Compound boundary analysis: 0 passes through inner loop
	 */
	@Test 
	public void emptyBoardContainsBlackPebbles() {
		assertFalse(Game.TestHook.boardContainsBlackPebbles(new boolean[0][0]));
	}
	
	/**
	 * Method: replaceBlackPebblesAroundLocation
	 * 
	 * Structured basis: nominal case, all boolean conditions are true
	 * Good data: normal size board, all adjacent locations on board
	 * Data flow: blackPebbles being replaced based on location
	 */
	@Test
	public void shouldReplacePebblesAroundLocationNominal() {
		boolean[][] blackPebbles = { { true, true, true }, { true, true, true }, { true, true, true } };
		Set<Location> newWhitePebbles = makePebbleSet();
		
		Game.TestHook.replaceBlackPebblesAroundLocation(new Location(1, 1), blackPebbles, newWhitePebbles);
		
		assertEquals(4, newWhitePebbles.size());
		assertFalse(blackPebbles[0][1]);
		assertFalse(blackPebbles[1][0]);
		assertFalse(blackPebbles[1][2]);
		assertFalse(blackPebbles[2][1]);
	}
	
	/**
	 * Method: replaceBlackPebblesAroundLocation
	 * 
	 * Structured basis: if condition is false
	 * Good data: normal size board, location on top left edge
	 * Data flow: blackPebbles being replaced based on location
	 */
	@Test
	public void shouldReplacePebblesOnBoardAndSkipPebblesOffBoard() {
		boolean[][] blackPebbles = { { true, true, true }, { true, true, true }, { true, true, true } };
		Set<Location> newWhitePebbles = makePebbleSet();
		
		Game.TestHook.replaceBlackPebblesAroundLocation(new Location(0, 0), blackPebbles, newWhitePebbles);
		
		assertEquals(2, newWhitePebbles.size());
		assertFalse(blackPebbles[0][1]);
		assertFalse(blackPebbles[1][0]);
	}
	
	/**
	 * Method: replaceBlackPebblesAroundLocation
	 * 
	 * Bad data: location is null
	 */
	@Test (expected = NullPointerException.class)
	public void shouldFailReplacingAroundNullLocation() {
		boolean[][] board = new boolean[3][3];
		Set<Location> newWhitePebbles = makePebbleSet();
		
		Game.TestHook.replaceBlackPebblesAroundLocation(null, board, newWhitePebbles);
	}
	
	/**
	 * Method: replaceBlackPebblesAroundLocation
	 * 
	 * Bad data: location is null
	 */
	@Test (expected = IndexOutOfBoundsException.class)
	public void shouldFailReplacingWithNonUniformBoardSize() {
		boolean[][] board = { { true, true, true }, { true, true }, { true, true, true } };
		Set<Location> newWhitePebbles = makePebbleSet();
		
		Game.TestHook.replaceBlackPebblesAroundLocation(new Location(0, 2), board, newWhitePebbles);
	}
	
	/**
	 * Method: locationIsOnBoard
	 * 
	 * Boundary analysis: 0 < location.getRow() < board.length
	 * Boundary analysis: 0 < location.getColumn() < board[0].length
	 */
	@Test
	public void locationShouldBeOnBoardCenter() {
		assertTrue(Game.TestHook.locationIsOnBoard(new Location(1, 1), new boolean[3][3]));
	}
	
	/**
	 * Method: locationIsOnBoard
	 * 
	 * Boundary analysis: location.getRow() = 0
	 */
	@Test
	public void locationShouldBeOnBoardTopEdge() {
		assertTrue(Game.TestHook.locationIsOnBoard(new Location(0, 1), new boolean[3][3]));
	}
	
	/**
	 * Method: locationIsOnBoard
	 * 
	 * Boundary analysis: location.getRow() < 0
	 */
	@Test
	public void locationShouldBeOffBoardTopEdge() {
		assertFalse(Game.TestHook.locationIsOnBoard(new Location(-1, 1), new boolean[3][3]));
	}
	
	/**
	 * Method: locationIsOnBoard
	 * 
	 * Boundary analysis: location.getRow() >= board.length
	 */
	@Test
	public void locationShouldBeOffBoardBottomEdge() {
		assertFalse(Game.TestHook.locationIsOnBoard(new Location(3, 1), new boolean[3][3]));
	}
	
	/**
	 * Method: locationIsOnBoard
	 * 
	 * Boundary analysis: location.getColumn() = 0
	 */
	@Test
	public void locationShouldBeOnBoardLeftEdge() {
		assertTrue(Game.TestHook.locationIsOnBoard(new Location(1, 0), new boolean[3][3]));
	}
	
	/**
	 * Method: locationIsOnBoard
	 * 
	 * Boundary analysis: location.getColumn() < 0
	 */
	@Test
	public void locationShouldBeOffBoardLeftEdge() {
		assertFalse(Game.TestHook.locationIsOnBoard(new Location(1, -1), new boolean[3][3]));
	}
	
	/**
	 * Method: locationIsOnBoard
	 * 
	 * Boundary analysis: location.getColumn() >= board[0].length
	 */
	@Test
	public void locationShouldBeOffBoardRightEdge() {
		assertFalse(Game.TestHook.locationIsOnBoard(new Location(1, 3), new boolean[3][3]));
	}
	
	/**
	 * Method: locationIsOnBoard
	 * 
	 * Bad data: null board
	 */
	@Test (expected = NullPointerException.class)
	public void shouldFailToCheckLocationOnNullBoard() {
		Game.TestHook.locationIsOnBoard(new Location(1, 3), null);
	}
	
	/**
	 * Method: locationIsOnBoard
	 * 
	 * Bad data: out of bounds index
	 * Boundary analysis: location.getRow() >= board.length
	 */
	@Test (expected = IndexOutOfBoundsException.class)
	public void replaceIfBlackPebbleLocationOffBoardBottomEdge() {
		Game.TestHook.replaceIfBlackPebble(new Location(3, 1), new boolean[3][3], makePebbleSet());
	}
	
	/**
	 * Method: locationIsOnBoard
	 * 
	 * Bad data: out of bounds index
	 * Compound boundary analysis: location.getColumn() < 0
	 */
	@Test (expected = IndexOutOfBoundsException.class)
	public void replaceIfBlackPebbleLocationOffBoardLeftEdge() {
		Game.TestHook.replaceIfBlackPebble(new Location(1, -1), new boolean[3][3], makePebbleSet());
	}
	
	/**
	 * Method: locationIsOnBoard
	 * 
	 * Bad data: out of bounds index
	 * Compound boundary analysis: location.getColumn() >= board[0].length
	 */
	@Test (expected = IndexOutOfBoundsException.class)
	public void replaceIfBlackPebbleLocationOffBoardRightEdge() {
		Game.TestHook.replaceIfBlackPebble(new Location(1, 3), new boolean[3][3], makePebbleSet());
	}
	
	/**
	 * Method: replaceIfBlackPebble
	 * 
	 * Bad data: all params are null
	 */
	@Test (expected = NullPointerException.class)
	public void nullParamsReplaceIfBlackPebble() {
		Game.TestHook.replaceIfBlackPebble(null, null, null);
	}
	
	/**
	 * Method: replaceIfBlackPebble
	 * 
	 * Structured basis: boolean condition true
	 * Good data: nominal case: location is a black pebble
	 * Boundary analysis: location.getRow() within array indices
	 * Compound boundary analysis: location.getColumn() within array indices
	 * Data flow: newWhitePebbles being altered if blackPebble is replaced
	 */
	@Test 
	public void replaceIfBlackPebbleTrue() {
		boolean[][] blackPebbles = { { true, true, true }, { true, true, true }, { true, true, true } };
		Set<Location> newWhitePebbles = makePebbleSet();
		
		Game.TestHook.replaceIfBlackPebble(new Location(0, 0), blackPebbles, newWhitePebbles);
		
		assertEquals(1, newWhitePebbles.size());
		assertFalse(blackPebbles[0][0]);
	}
	
	/**
	 * Method: replaceIfBlackPebble
	 * 
	 * Structured basis: boolean condition false
	 * Good data: nominal case: location is not a black pebble
	 * Boundary analysis: location.getRow() within array indices
	 * Compoun boundary analysis: location.getColumn() within array indices
	 * Data flow: newWhitePebbles being altered if blackPebble is replaced
	 */
	@Test
	public void replaceIfBlackPebbleNullSpotOnBoard() {
		boolean[][] blackPebbles = { { true, true, true }, { true, true, false }, { true, true, true } };
		Set<Location> newWhitePebbles = makePebbleSet();
		
		Game.TestHook.replaceIfBlackPebble(new Location(1, 2), blackPebbles, newWhitePebbles);
		
		assertEquals(0, newWhitePebbles.size());
	}
	
	/**
	 * Method: replaceIfBlackPebble
	 * 
	 * Bad data: out of bounds index
	 * Boundary analysis: location.getRow() < 0
	 */
	@Test (expected = IndexOutOfBoundsException.class)
	public void replaceIfBlackPebbleLocationOffBoardTopEdge() {
		Game.TestHook.replaceIfBlackPebble(new Location(-1, 1), new boolean[3][3], makePebbleSet());
	}
	
}
