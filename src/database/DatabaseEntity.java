package database;

import java.util.ArrayList;

public interface DatabaseEntity
{
	default String createTableSql()
	{	
		return DatabaseCommon.createTableSql(this);
	}
	
	default ArrayList<String> createTableSqlRecursive() throws IllegalAccessException
	{
		return DatabaseCommon.createTableSqlRecursive(this);
	}
	
	default String createInsertSql() throws IllegalAccessException
	{
		return DatabaseCommon.createInsertSql(this);
	}
	
	default ArrayList<String> createInsertSqlRecursive() throws IllegalAccessException
	{
		return DatabaseCommon.createInsertSqlRecursive(this);
	}
	
	default String createUpdateSql(String primaryKey) throws IllegalAccessException
	{
		return DatabaseCommon.createUpdateSql(this, primaryKey);
	}
	
	default ArrayList<String> createUpdateSqlRecursive() throws IllegalAccessException
	{
		return DatabaseCommon.createUpdateSqlRecursive(this, "");
	}
}
