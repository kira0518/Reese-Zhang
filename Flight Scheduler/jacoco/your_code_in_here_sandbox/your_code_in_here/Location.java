import java.util.*;
import java.lang.*;

public class Location {

	public String name;
	public double lat;
	public double lon;
	public double demand;
	//private static final int R = 6371;
	public ArrayList<Flight> Departure = new ArrayList<Flight>();
	public ArrayList<Flight> Arrival = new ArrayList<Flight>();
	public ArrayList<Integer> aftertime = new ArrayList<Integer>();
	public ArrayList<Integer> beforetime = new ArrayList<Integer>();
	public ArrayList<Integer> conflicttime = new ArrayList<Integer>();
	public ArrayList<Integer> beforeconflict_time = new ArrayList<Integer>();
	public ArrayList<Integer> afterconflict_time = new ArrayList<Integer>();
	public HashMap<Integer, Flight> conflict_flights = new HashMap<Integer, Flight>();
	public double latitude_1;
	public double latitude_2;
	public double longitude_1;
	public double longitude_2;
	public double direct_lat;
	public double direct_lon;
	public double rad_lat;
	public double rad_lat1;
	public double rad_lat2;
	public double rad_lon;
	public double h;
	public double d;
	public double distanse;
	public String f_time;
	public String dep_time;
	public String z;
	public int dist;
	public int t_dif;
	public boolean no_conflit = false;
	public String arr_dtime;
	public int flight_time;
	public int d_t;
	public int d_time;
	public int time_dif;
	public String con_strname;
	public int arr_dt;
	public int arr_t;
	public Flight conflict_flight;
	public int con_name;
	public String msg;
	public int f_t;
	

	public Location(String name, double lat, double lon, double demand) {
		this.name = name;
		this.lat = lat;
		this.lon = lon;
		this.demand = demand;
		

	}

    //Implement the Haversine formula - return value in kilometres
    public static double distance(Location l1, Location l2) {
		double latitude_1 = l1.lat;
		double longitude_1 = l1.lon;
		double latitude_2 = l2.lat;
		double longitude_2 = l2.lon;
		double direct_lat = latitude_2 - latitude_1;
		double direct_lon = longitude_2 - longitude_1;
		double rad_lat = direct_lat * Math.PI / 180;
		double rad_lon = direct_lon * Math.PI / 180;
		double rad_lat1 = latitude_1 * Math.PI / 180;
		double rad_lat2 = latitude_2 * Math.PI / 180;
		double h = Math.pow(Math.sin(rad_lat / 2), 2) 
				+  Math.cos(rad_lat1) * Math.cos(rad_lat2) *
				   Math.pow(Math.sin(rad_lon / 2), 2);
		double d = 2 * Math.atan2(Math.sqrt(h), Math.sqrt(1 - h));
		double distanse = d * 6371;
		//int dist = (int)Math.round(distanse);
		return distanse;
		

				
    }

    public void addArrival(Flight f) {
		Departure.add(f);
	}
	
	public void addDeparture(Flight f) {
		Arrival.add(f);

	}
	


	/**
	 * Check to see if Flight f can depart from this location.
	 * If there is a clash, the clashing flight string is returned, otherwise null is returned.
	 * A conflict is determined by if any other flights are arriving or departing at this location within an hour of this flight's departure time.
	 * @param f The flight to check.
	 * @return "Flight <id> [departing/arriving] from <name> on <clashingFlightTime>". Return null if there is no clash.
	 */
	public String hasRunwayDepartureSpace(Flight f) {
		//check departures first

		//then check arrivals
		String f_time = f.datetime;
		int f_t = Flight.find_time(f_time);
		
		for (int i = 0; i < Departure.size(); i ++)
		{
			String dep_time = Departure.get(i).datetime;
			int dep_t = Flight.find_time(dep_time);	
			int time_dif =  f_t - dep_t;
			
			if (time_dif >= 5760)
			{
				int week_time = 10080 - time_dif;
				
				if (week_time < 60)
				{
					aftertime.add(week_time);
					conflict_flights.put(week_time, Departure.get(i));
				}
				
			}else if (time_dif < -5760)
			{
				int week_time = 10080 + time_dif;
				if (week_time < 60)
				{
					beforetime.add(week_time);
					conflict_flights.put(week_time, Departure.get(i));
				}
				

			}
			int time_diff = Math.abs(time_dif);
			if (time_diff < 60)
			{
				if (time_dif < 0) 
				{	
					aftertime.add(time_diff);
					conflict_flights.put(time_diff, Departure.get(i));
				}else if (time_dif >= 0)
					{
					//System.out.println(time_diff);
					beforetime.add(time_diff);
					conflict_flights.put(time_diff, Departure.get(i));
					}
			}
			

		}
		
	
		
		if (aftertime.size() > 0)
		{
			Collections.sort(aftertime);
			Flight conflict_flight = conflict_flights.get(aftertime.get(0));
			int con_name = conflict_flight.Id;
			String msg = "Flight "+conflict_flight.Id+" departing from "+conflict_flight.dep.name+" on "+conflict_flight.datetime.substring(0, 1).toUpperCase() + 
			conflict_flight.datetime.substring(1)+".";
			return msg;
		}else if (aftertime.size() == 0)
		{
			if (beforetime.size() > 0)
			{
				Collections.sort(beforetime);
				Flight conflict_flight = conflict_flights.get(beforetime.get(0));
				String msg = "Flight "+conflict_flight.Id+" departing from "+conflict_flight.dep.name+" on "+conflict_flight.datetime.substring(0, 1).toUpperCase() + 
				conflict_flight.datetime.substring(1)+".";
				return msg;
			}
		}
		

		
		beforetime.clear();
		aftertime.clear();

		for (int i = 0; i < Arrival.size(); i ++)
		{
			String arr_dtime = Arrival.get(i).datetime;
			Flight flight_i = Arrival.get(i);
			int flight_time = Arrival.get(i).getDuration();
			int arr_dt = Flight.find_time(arr_dtime);
			int arr_t = flight_time + arr_dt;
			//System.out.println(arr_dt);
			//System.out.println(f_t);
			int t_dif = f_t - arr_t;
			int time_diff = Math.abs(t_dif);
			
			if (time_dif >= 5760)
			{
				int week_time = 10080 - time_dif;
				
				if (week_time < 60)
				{
					aftertime.add(week_time);
					conflict_flights.put(week_time, Arrival.get(i));
				}
				
			}else if (time_dif < -5760)
			{
				int week_time = 10080 + time_dif;
				if (week_time < 60)
				{
					beforetime.add(week_time);
					conflict_flights.put(week_time, Arrival.get(i));
				}
				

			}

			if (time_diff < 60)
			{
				if (t_dif < 0) 
				{
					aftertime.add(time_diff);
					conflict_flights.put(time_diff, Arrival.get(i));
				}else if (t_dif >= 0)
				{
					beforetime.add(time_diff);
					conflict_flights.put(time_diff, Arrival.get(i));
				}
			}
			
		}
		
		
		//System.out.println(time_dif);

		if (aftertime.size() > 0)
		{
			Collections.sort(aftertime);
			Flight conflict_flight = conflict_flights.get(aftertime.get(0));
			int con_name = conflict_flight.Id;
			String msg = "Flight "+conflict_flight.Id+" arriving from "+conflict_flight.dep.name+" on "+conflict_flight.datetime.substring(0, 1).toUpperCase() + 
			conflict_flight.datetime.substring(1)+".";
			return msg;
		}else if (aftertime.size() == 0)
		{
			if (beforetime.size() > 0)
			{
				Collections.sort(beforetime);
				Flight conflict_flight = conflict_flights.get(beforetime.get(0));
				String msg = "Flight "+conflict_flight.Id+" arriving from "+conflict_flight.dep.name+" on "+conflict_flight.datetime.substring(0, 1).toUpperCase() + 
				conflict_flight.datetime.substring(1)+".";
				return msg;
			}
		}


		return null;

    }



    /**
	 * Check to see if Flight f can arrive at this location.
	 * A conflict is determined by if any other flights are arriving or departing at this location within an hour of this flight's arrival time.
	 * @param f The flight to check.
	 * @return String representing the clashing flight, or null if there is no clash. Eg. "Flight <id> [departing/arriving] from <name> on <clashingFlightTime>"
	 */
	public String hasRunwayArrivalSpace(Flight f) {
		//check departures first
		//then check arrivals
		String d_time = f.datetime;
		int f_dura = f.getDuration();
		int d_t = Flight.find_time(d_time);
		int f_t = d_t + f_dura;
		for (int i = 0; i < Departure.size(); i ++)
		{
			String dep_time = Departure.get(i).datetime;
			int dep_t = Flight.find_time(dep_time);	
			int time_dif =  f_t - dep_t;
			if (time_dif >= 5760)
			{
				int week_time = 10080 - time_dif;
				
				if (week_time < 60)
				{
					aftertime.add(week_time);
					conflict_flights.put(week_time, Departure.get(i));
				}
				
			}else if (time_dif < -5760)
			{
				int week_time = 10080 + time_dif;
				//System.out.println(week_time);
				if (week_time < 60)
				{
					beforetime.add(week_time);
					conflict_flights.put(week_time, Departure.get(i));
				}
				

			}
			int time_diff = Math.abs(time_dif);
			
			if (time_diff < 60)
			{
				if (time_dif < 0) 
				{	
					aftertime.add(time_diff);
					conflict_flights.put(time_diff, Departure.get(i));
				}else if (time_dif >= 0)
					{
					//System.out.println(time_diff);
					beforetime.add(time_diff);
					conflict_flights.put(time_diff, Departure.get(i));
					}
			}
			

		}
		
	
		
		if (aftertime.size() > 0)
		{
			Collections.sort(aftertime);
			Flight conflict_flight = conflict_flights.get(aftertime.get(0));
			int con_name = conflict_flight.Id;
			String msg = "Flight "+conflict_flight.Id+" departing from "+conflict_flight.dep.name+" on "+conflict_flight.datetime.substring(0, 1).toUpperCase() + 
			conflict_flight.datetime.substring(1)+".";
			return msg;
		}else if (aftertime.size() == 0)
		{
			if (beforetime.size() > 0)
			{
				Collections.sort(beforetime);
				Flight conflict_flight = conflict_flights.get(beforetime.get(0));
				String msg = "Flight "+conflict_flight.Id+" departing from "+conflict_flight.dep.name+" on "+conflict_flight.datetime.substring(0, 1).toUpperCase() + 
				conflict_flight.datetime.substring(1)+".";
				return msg;
			}
		}
		

		
		beforetime.clear();
		aftertime.clear();

		for (int i = 0; i < Arrival.size(); i ++)
		{
			String arr_dtime = Arrival.get(i).datetime;
			Flight flight_i = Arrival.get(i);
			int flight_time = Arrival.get(i).getDuration();
			int arr_dt = Flight.find_time(arr_dtime);
			int arr_t = flight_time + arr_dt;
			// System.out.println(arr_dt);
			// System.out.println(f_t);
			int t_dif = f_t - arr_t;
			int time_diff = Math.abs(t_dif);
			if (t_dif >= 5760)
			{
				int week_time = 10080 - t_dif;
				
				if (week_time < 60)
				{
					aftertime.add(week_time);
					conflict_flights.put(week_time, Arrival.get(i));
				}
				
			}else if (t_dif < -5760)
			{
				int week_time = 10080 + t_dif;
				
				if (week_time < 60)
				{
					beforetime.add(week_time);
					conflict_flights.put(week_time, Arrival.get(i));
				}
				

			}

			if (time_diff < 60)
			{
				if (t_dif < 0) 
				{
					aftertime.add(time_diff);
					conflict_flights.put(time_diff, Arrival.get(i));
				}else if (t_dif >= 0)
				{
					beforetime.add(time_diff);
					conflict_flights.put(time_diff, Arrival.get(i));
				}
			}
			
		}
		
		

		if (aftertime.size() > 0)
		{
			Collections.sort(aftertime);
			Flight conflict_flight = conflict_flights.get(aftertime.get(0));
			int con_name = conflict_flight.Id;
			String msg = "Flight "+conflict_flight.Id+" arriving from "+conflict_flight.dep.name+" on "+conflict_flight.datetime.substring(0, 1).toUpperCase() + 
			conflict_flight.datetime.substring(1)+".";
			return msg;
		}else if (aftertime.size() == 0)
		{
			if (beforetime.size() > 0)
			{
				Collections.sort(beforetime);
				Flight conflict_flight = conflict_flights.get(beforetime.get(0));
				String msg = "Flight "+conflict_flight.Id+" arriving from "+conflict_flight.dep.name+" on "+conflict_flight.datetime.substring(0, 1).toUpperCase() + 
				conflict_flight.datetime.substring(1)+".";
				return msg;
			}
		}

		return null;

    }

		

	
	public static void main(String[] args){
        Location Sydney = new Location("Sydney", -33.847927, 150.651786, 0.4);
		Location NewYork = new Location("NewYork", 40.7, -74.26, -0.874);
        Location Berlin = new Location("Berlin", 52.5, 13.15, 0.22222);
        Location perth = new Location("perth", -32.0397559, 115.681346, 0.5);
        Location London = new Location("London", 51.5285582, -0.2416812, 0.1);
        Location AliceSprings = new Location("AliceSprings", -23.7206896, 133.7977352, 0.056);
		Location Mumbai = new Location("Mumbai", 19.08, 72.741, 0.4);
        Location NewDelhi = new Location("NewDelhi", 28.527, 77.0688988, -0.123);
		Flight newflight1 = new Flight(1, "monday 08:00", Mumbai, NewDelhi, 120);
		Flight newflight2 = new Flight(2, "wednesday 03:15", Berlin, NewYork, 280);
		Flight newflight3 = new Flight(3, "wednesday 06:00", Sydney, perth, 280);
		Flight newflight4 = new Flight(4, "sunday 23:45", London, Berlin, 280);
		Flight newflight5 = new Flight(5, "wednesday 01:20", perth, Sydney, 120);
	    Sydney.addDeparture(newflight3);
		perth.addArrival(newflight3);
		String a = Sydney.hasRunwayArrivalSpace(newflight5);
		


    }


}
