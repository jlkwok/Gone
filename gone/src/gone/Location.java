package gone;


public final class Location {
	
	private int row, column;

	/**
	 * @param row
	 * @param column
	 */
	public Location(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	public Location offsetBy(int rows, int columns) {
		return new Location(row + rows, column + columns);
	}

	
	/**
	 * @return row
	 */
	public int getRow() {
		return row;
	}

	
	/**
	 * @param row
	 */
	public void setRow(int row) {
		this.row = row;
	}

	
	/**
	 * @return column
	 */
	public int getColumn() {
		return column;
	}

	
	/**
	 * @param column
	 */
	public void setColumn(int column) {
		this.column = column;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "(" + row + ", " + column + ")";
	}
	
}
