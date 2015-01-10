package database;

public enum DatabaseTypeMap
{
 // Name	Id	Java Type	SQL Type			Is A String
	Unknown	(-1,"",			"",					false),
	String	(0,	"String",	"VARCHAR(255)",		true),
	Integer	(1, "int", 		"INTEGER", 			false),
	Float	(2, "float", 	"FLOAT", 			false),
	Double	(3, "double", 	"DOUBLE PRECISION", false),
	Date	(4, "Date", 	"DATETIME", 		true),
	Long	(5, "long",		"BIGINT", 			false);
	
	private int id;
	private String javaType, sqlType;
	private boolean isString;
	DatabaseTypeMap(int id, String javaType, String sqlType, boolean isString)
	{
		this.id = id;
		this.javaType = javaType;
		this.sqlType = sqlType;
		this.isString = isString;
	}
	
	public int getId()
	{
		return id;
	}
	
	public String getSqlType()
	{
		return sqlType;
	}
	
	public String getJavaType()
	{
		return javaType;
	}
	
	public boolean getIsString()
	{
		return isString;
	}
	
	public static DatabaseTypeMap getEnum(String value)
	{
        for (DatabaseTypeMap v : values())
        {
        	if(v.getJavaType().equalsIgnoreCase(value)) return v;
        }
        return Unknown;
    } 
}
