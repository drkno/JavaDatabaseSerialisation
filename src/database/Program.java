package database;

import java.util.ArrayList;
import java.util.Date;

import model.Activity;
import model.DataPoint;
import model.Gender;
import model.Location;
import persistence.CSVActivitySerializer;
import profiles.UserProfile;

public class Program {
	public static UserProfile profile;
	public static void main(String[] args) throws Exception
	{
		profile = new UserProfile("name", new Date(), 1.0f, 2.0f, Gender.Male, new ArrayList<Activity>());
		CSVActivitySerializer serialiser = new CSVActivitySerializer("sample_data.csv");
		profile.getActivities().addAll(serialiser.loadData());
		
		for (String sql : profile.createTableSqlRecursive())
		{
			System.out.println(sql);
		}
		System.out.println("---");
		for (String sql : profile.createInsertSqlRecursive())
		{
			System.out.println(sql);
		}
		System.out.println("---");
		for (String sql : profile.createUpdateSqlRecursive())
		{
			System.out.println(sql);
		}
		System.out.println("----");
		DataPoint point = new DataPoint(new Date(), 50, new Location(10, 10, 15), null);
		System.out.println(point.createInsertSql());
		System.out.println(point.createTableSql());
		System.out.println(point.createUpdateSql(null));
		System.out.println("----");
		Gender g = Gender.Male;
		System.out.println(g.createInsertSql());
		System.out.println(g.createUpdateSql(null));
		System.out.println(g.createTableSql());
	}
}
