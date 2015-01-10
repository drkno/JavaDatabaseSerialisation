package database;

import java.lang.reflect.Field;

public class DatabaseEntityProperty
{
	private Field field;
	private DatabaseTypeMap type;
	
	public DatabaseEntityProperty(Field property)
	{
		field = property;
		field.setAccessible(true);

		String str = field.getType().getName();
		if (str.contains("."))
		{
			str = str.substring(str.lastIndexOf('.')+1);
		}
		type = DatabaseTypeMap.getEnum(str);
		
		if (type == DatabaseTypeMap.Unknown)
		{
			type = DatabaseTypeMap.Integer;
		}
	}
	
	public String getName()
	{
		return field.getName();
	}
	
	public DatabaseTypeMap getDatabaseType()
	{
		return type;
	}
	
	public void set(DatabaseEntity toSet, DatabaseEntity value) throws IllegalAccessException
	{
		field.set(toSet, value);
	}
	
	public Object getObject(DatabaseEntity toGet) throws IllegalAccessException
	{
		return field.get(toGet);
	}
	
	public String get(DatabaseEntity toGet) throws IllegalAccessException
	{
		Object value = field.get(toGet);
		DatabaseTypeMap type = getDatabaseType();
		String str;
		if (value == null)
		{
			str = type.getIsString() ? "" : "NULL";
		}
		else if (value instanceof DatabaseEnum)
		{
			Integer index = ((DatabaseEnum) value).getIndex();
			str = index.toString();
		}
		else
		{
			if (value instanceof java.util.Date)		// date fix
			{
				java.sql.Date sqlDate = new java.sql.Date(((java.util.Date)value).getTime());
			    java.sql.Time sqlTime = new java.sql.Time(((java.util.Date)value).getTime());
			    str = sqlDate + " " + sqlTime;
			}
			else
			{
				str = value.toString();
			}
		}
		
		if (type.getIsString())
		{
			str = "'" + str + "'";
		}
		return str;
	}
}
