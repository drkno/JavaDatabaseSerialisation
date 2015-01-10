package database;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

public class DatabaseCommon
{	
	public static String getClassName(DatabaseEntity obj)
	{
		String name = obj.getClass().getName();
		if (name.contains("."))
		{
			name = name.substring(name.indexOf('.')+1);
		}
		return name;
	}
	
	public static ArrayList<DatabaseEntityProperty> getPropertyList(DatabaseEntity obj)
	{
		ArrayList<DatabaseEntityProperty> entityList = new ArrayList<>();
		
	    Field[] allFields = obj.getClass().getDeclaredFields();
	    for (Field field : allFields)
	    {
	        if (Modifier.isStatic(field.getModifiers()) || Modifier.isFinal(field.getModifiers()))
	        {
	        	continue;
	        }
        	DatabaseEntityProperty entity = new DatabaseEntityProperty(field);
        	field.setAccessible(true);
        	try {
				if (entity.getDatabaseType() == DatabaseTypeMap.Unknown &&
						!(field.get(obj) instanceof DatabaseEnum))
				{
					continue;
				}
			} catch (Exception e) {
				continue;
			}
    		entityList.add(entity);
	    }
		
		return entityList;
	}
	
	public static ArrayList<DatabaseEntityProperty> getEntityList(DatabaseEntity obj) throws IllegalAccessException
	{
		ArrayList<DatabaseEntityProperty> entityList = new ArrayList<>();
		
	    Field[] allFields = obj.getClass().getDeclaredFields();
	    for (Field field : allFields)
	    {
	        if (Modifier.isStatic(field.getModifiers()) ||
	        		Modifier.isFinal(field.getModifiers())) continue;
        	DatabaseEntityProperty entity = new DatabaseEntityProperty(field);
        	if (entity.getDatabaseType() == DatabaseTypeMap.Unknown)
        	{
        		if (entity.getObject(obj) instanceof DatabaseEntity)
        		{
            		entityList.add(entity);
        		}
        	}
	    }
		
	    System.out.println(entityList.size());
		return entityList;
	}
	
	public static String createTableSql(DatabaseEntity obj)
	{	
		String sqlStatement = "CREATE TABLE " + DatabaseCommon.getClassName(obj) + " (";
		ArrayList<DatabaseEntityProperty> properties = DatabaseCommon.getPropertyList(obj);
		for (int i = 0; i < properties.size(); i++)
		{
			sqlStatement += properties.get(i).getName() + " " + properties.get(i).getDatabaseType().getSqlType();
			if (properties.size() != i+1)
			{
				sqlStatement += ", ";
			}
		}
		sqlStatement += ");";
		
		return sqlStatement;
	}
	
	public static ArrayList<String> createTableSqlRecursive(DatabaseEntity obj) throws IllegalAccessException
	{
		ArrayList<String> sqlStatements = new ArrayList<String>();
		sqlStatements.add(createTableSql(obj));
		for (DatabaseEntityProperty entity : getEntityList(obj))
		{
			DatabaseEntity property = (DatabaseEntity)entity.getObject(obj);
			sqlStatements.addAll(property.createTableSqlRecursive());
		}
		return sqlStatements;
	}
	
	public static String createInsertSql(DatabaseEntity obj) throws IllegalAccessException
	{
		ArrayList<DatabaseEntityProperty> properties = DatabaseCommon.getPropertyList(obj);
		String sqlStatement = "INSERT INTO " + DatabaseCommon.getClassName(obj) + " (";
		for (int i = 0; i < properties.size(); i++)
		{
			sqlStatement += properties.get(i).getName();
			if (properties.size() != i+1)
			{
				sqlStatement += ", ";
			}
		}
		sqlStatement += ") VALUES (";
		for (int i = 0; i < properties.size(); i++)
		{
			sqlStatement += properties.get(i).get(obj);
			if (properties.size() != i+1)
			{
				sqlStatement += ", ";
			}
		}
		sqlStatement += ");";
		return sqlStatement;
	}
	
	public static ArrayList<String> createInsertSqlRecursive(DatabaseEntity obj) throws IllegalAccessException
	{
		ArrayList<String> sqlStatements = new ArrayList<String>();
		sqlStatements.add(createInsertSql(obj));
		for (DatabaseEntityProperty entity : getEntityList(obj))
		{
			DatabaseEntity property = (DatabaseEntity)entity.getObject(obj);
			sqlStatements.addAll(property.createInsertSqlRecursive());
		}
		return sqlStatements;
	}

	public static String createUpdateSql(DatabaseEntity obj, String primaryKey) throws IllegalAccessException
	{
		String sqlStatement = "UPDATE " + DatabaseCommon.getClassName(obj);
		sqlStatement += " SET ";
		ArrayList<DatabaseEntityProperty> properties = DatabaseCommon.getPropertyList(obj);
		for (int i = 0; i < properties.size(); i++)
		{
			DatabaseEntityProperty entity = properties.get(i);
			sqlStatement += entity.getName() + "=" + entity.get(obj);
			if (properties.size() != i+1)
			{
				sqlStatement += ", ";
			}
		}
		
		for (DatabaseEntityProperty property : properties)
		{
			if (property.getName() == primaryKey)
			{
				sqlStatement += " WHERE " + primaryKey + "=" + property.get(obj);
				break;
			}
		}
		
		sqlStatement += ";";
		
		return sqlStatement;
	}
	
	public static ArrayList<String> createUpdateSqlRecursive(DatabaseEntity obj, String primaryKey) throws IllegalAccessException
	{
		ArrayList<String> sqlStatements = new ArrayList<String>();
		sqlStatements.add(createUpdateSql(obj, primaryKey));
		for (DatabaseEntityProperty entity : getEntityList(obj))
		{
			DatabaseEntity property = (DatabaseEntity)entity.getObject(obj);
			sqlStatements.addAll(property.createUpdateSqlRecursive());
		}
		return sqlStatements;
	}
	
	
	
	
	
	
	
	
}
