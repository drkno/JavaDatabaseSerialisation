package database;

import java.util.ArrayList;

public interface DatabaseEnum extends DatabaseEntity
{
	int getIndex();
	String getName();
	
	default String createTableSql()
	{	
		if (!(this instanceof Enum))
		{
			return "";
		}
		
		String sqlStatement = "CREATE TABLE " + DatabaseCommon.getClassName(this) + " ("
				+ DatabaseCommon.getClassName(this) + "Id INTEGER, Value VARCHAR(255))";
		return sqlStatement;
	}
	
	default ArrayList<String> createTableSqlRecursive() throws IllegalAccessException
	{
		ArrayList<String> sqlStatements = new ArrayList<String>();
		sqlStatements.add(createTableSql());
		return sqlStatements;
	}
	
	default String createInsertSql() throws IllegalAccessException
	{
		if (!(this instanceof Enum))
		{
			return "";
		}
		
		String sqlStatement = "INSERT INTO " + DatabaseCommon.getClassName(this) + " ("
				+ DatabaseCommon.getClassName(this) + "Id, Value) VALUES (";

		int index = getIndex();
		String name = getName();
		
		sqlStatement += index + ", '" + name + "');";
		return sqlStatement;
	}
	
	default ArrayList<String> createInsertSqlRecursive() throws IllegalAccessException
	{
		ArrayList<String> sqlStatements = new ArrayList<String>();
		sqlStatements.add(createInsertSql());
		return sqlStatements;
	}
	
	default String createUpdateSql(String primaryKey) throws IllegalAccessException
	{
		if (!(this instanceof Enum))
		{
			return "";
		}
		
		primaryKey = DatabaseCommon.getClassName(this) + "Id"; // ignore passed key
		
		String sqlStatement = "UPDATE " + DatabaseCommon.getClassName(this) + " SET Value='"
				+ getName() + "' WHERE " + primaryKey + "=" + this.getIndex();
		
		sqlStatement += ";";
		
		return sqlStatement;
	}
	
	default ArrayList<String> createUpdateSqlRecursive() throws IllegalAccessException
	{
		ArrayList<String> sqlStatements = new ArrayList<String>();
		sqlStatements.add(createUpdateSql(null));
		return sqlStatements;
	}
}
