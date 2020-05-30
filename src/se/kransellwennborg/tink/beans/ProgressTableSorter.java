package se.kransellwennborg.tink.beans;

public class ProgressTableSorter {
	public enum SortCol {
	    CASE_ID, CLIENT, CASE_NAME, AMOUNT, DATE 
	}

	public enum SortDirection {
	    ASC, DESC 
	}

	SortCol sortCol;
	SortDirection sortDirection;
	public SortCol getSortCol() {
		return sortCol;
	}
	public void setSortCol(SortCol sortCol) {
		this.sortCol = sortCol;
	}
	public SortDirection getSortDirection() {
		return sortDirection;
	}
	public void setSortDirection(SortDirection sortDirection) {
		this.sortDirection = sortDirection;
	}
	
}