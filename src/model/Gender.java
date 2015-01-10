package model;

import database.DatabaseEnum;

/**
 * Enum to store the gender of the user
 */
public enum Gender implements DatabaseEnum {
	Male(0),
	Female(1);
	
	private int index;
	Gender(int index)
	{
		this.index = index;
	}
	
	@Override
	public int getIndex() {
		return index;
	}

	@Override
	public String getName() {
		return this.toString();
	}
}
