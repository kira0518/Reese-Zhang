import java.util.*;
import java.lang.*;



public class Flight {
    public String datetime;
    public Location dep;
    public Location arr;
    public int num_seats;
    public int num_booked;
    public static HashMap<String, Integer> week = new HashMap<String, Integer>();
    public static HashMap<Integer, String> week_num = new HashMap<Integer, String>();
    public String time1;
    public String time2;
    public String day;
    public String time;
    public String Time;
    public String hour_str;
    public String mins_str;
    public int hour;
    public int mins;
    public int Day;
    public int day_mins;
    public int hour_mins;
    public int total_mins;
    public int diff;
    public int a;
    public int b;
    public int c;
    public int week_mins;
    public int over_diff;
    private static final int speed = 12; // 12km/mins
    public double d;
    public double x = 0;
    public double y = 0;
    public double price;
    public double demand_diff;
    public double per_price;
    public double total_cost;
    public int x_dep_time;
    public int x_flight_time;
    public int x_arr_time;
    public int y_dep_time;
    public int layover_time;
    public double duration;
    public double duration_whole;
    public int dis;
    public int Id;
    public double ticket_price;
    public int capacity;
    public int xarr_time;
    public int xarr_t;
    public double booked_double;
    public double capacity_double;
    public double z;
    public String depDAY;
    public String depHOUR;
    public String depMINS;
    public int deptime_mins;
    public String arrDAY;
    public String arrMINS;
    public String arrHOURS;
    public int arrtime_mins;
    public int flighthour;
    public int flightmins;
    //public String weekday;
    public int flighthour_1;
    public int arr_hour;
    public int Day1;
    public String mins_string;
    public String hour_string;
    public String flight_duration;
    public String flight_dur;
    public int dist;
    public double flight_price;
    
    

    public Flight(int Id, String datetime, Location dep, Location arr, int capacity){
        this.datetime = datetime;
        this.dep = dep;
        this.arr = arr;
        this.capacity = capacity;
        this.num_booked = 0;
        this.Id = Id;
        this.ticket_price = 0;
        this.depDAY = "Mon";
        this.depHOUR = "00";
        this.depMINS = "00";
        this.deptime_mins = 0;
        this.arrDAY = "Mon";
        this.arrHOURS = "00";
        this.arrMINS = "00";
        this.arrtime_mins = 0;
        this.flight_duration = "0h0min";
        this.dist = 0;
        


    }

    public void find_mins(String Time){   
        week.put("monday", 0);
        week.put("tuesday", 1);
        week.put("wednesday",2);
        week.put("thursday", 3);
        week.put("friday", 4);
        week.put("saturday", 5);
        week.put("sunday", 6);
        
        String day = Time.split(" ")[0].toLowerCase();
        String time = Time.split(" ")[1];
        String hour_str = time.split(":")[0];
        String mins_str = time.split(":")[1];
        int hour = Integer.parseInt(hour_str);
        int mins = Integer.parseInt(mins_str);
        

        int Day = week.get(day);
        int day_mins = Day * 24 * 60;
        int hour_mins = hour * 60;
        int total_mins = day_mins + hour_mins + mins;
        String day_1 = day.substring(0, 1).toUpperCase() + day.substring(1, 3);

        if (hour <=9)
        {
            this.depHOUR = "0"+String.format("%d", hour);
        }else
        {
            this.depHOUR = String.format("%d", hour);
        }
        if (mins <= 9)
        {
            this.depMINS = "0"+String.format("%d", mins);
        }else
        {
            this.depMINS = String.format("%d", mins);
        }
        
        this.depDAY = day_1;
        this.deptime_mins = total_mins;
        
        ///////////////find arrival time/////////////////////////////////////

        int flightduration = getDuration();
        int flighthour = (int)flightduration / 60;
        int flightmins = flightduration - 60 * flighthour;
        String flight_dur = String.format("%d", flighthour) + "h "+String.format("%d", flightmins)+"m";
        this.flight_duration = flight_dur;
        week_num.put(0, "monday");
        week_num.put(1, "tuesday");
        week_num.put(2, "wednesday");
        week_num.put(3, "thursday");
        week_num.put(4, "friday");
        week_num.put(5, "saturday");
        week_num.put(6, "sunday");
        //  System.out.println(flightduration);
        
        if ((mins + flightmins)>=60)
        {
            int arr_mins = mins + flightmins - 60;
            int flighthour_1 = flighthour + 1;
            //System.out.println(flighthour_1);
            if (hour+flighthour_1 > 23)
            {
                
                int arr_hour = hour + flighthour_1 - 24;
                if (Day+1>6)
                {
                    int Day1 = Day + 1 - 7;
                    String arrday = week_num.get(Day1).substring(0, 1).toUpperCase() + week_num.get(Day1).substring(1, 3);
                    this.arrDAY = arrday;
                    if (arr_mins <= 9)
                    {
                        this.arrMINS = "0"+ String.format("%d", arr_mins);
                    }else{
                        this.arrMINS = String.format("%d", arr_mins);
                    }
                    if (arr_hour <= 9)
                    {
                        this.arrHOURS = "0"+ String.format("%d", arr_hour);
                    }else
                    {
                        this.arrHOURS = String.format("%d", arr_hour);
                    }
                    this.arrtime_mins = Day1 * 24 * 40 + arr_hour * 60 + arr_mins;
 
                }else
                {
                    int Day1 = Day + 1;
                    String arrday = week_num.get(Day1).substring(0, 1).toUpperCase() + week_num.get(Day1).substring(1, 3);
                    this.arrDAY = arrday;
                    if (arr_mins <= 9)
                    {
                        this.arrMINS = "0"+ String.format("%d", arr_mins);
                    }else
                    {
                        this.arrMINS = String.format("%d", arr_mins);
                    }
                    if (arr_hour <= 9)
                    {
                        this.arrHOURS = "0"+ String.format("%d", arr_hour);
                    }else
                    { 
                        this.arrHOURS = String.format("%d", arr_hour);
                    }
                    this.arrtime_mins = Day1 * 24 * 60 + arr_hour * 60 + arr_mins;
                }
                
            }else
            {   
                int arr_hour = hour + flighthour_1;
                
                String arrday = week_num.get(Day).substring(0, 1).toUpperCase() + week_num.get(Day).substring(1, 3);
                this.arrDAY = arrday;
                if (arr_mins <= 9)
                {
                    this.arrMINS = "0"+ String.format("%d", arr_mins);
                }else
                {
                    this.arrMINS = String.format("%d", arr_mins);
                }
                 if (arr_hour <= 9)
                {
                    this.arrHOURS = "0"+ String.format("%d", arr_hour);
                }else
                {
                    this.arrHOURS = String.format("%d", arr_hour);
                }
                this.arrtime_mins = Day * 24 * 60 + arr_hour * 60 + arr_mins;

            }
            
        }else
        {
            int arr_mins = mins + flightmins;
            if (hour+flighthour > 23)
            {
                int arr_hour = hour + flighthour - 24;

                if (Day+1>6)
                {
                    int Day1 = Day + 1 - 7;
                    String arrday = week_num.get(Day1).substring(0, 1).toUpperCase() + week_num.get(Day1).substring(1, 3);
                    this.arrDAY = arrday;
                    if (arr_mins <= 9)
                    {
                        this.arrMINS = "0"+ String.format("%d", arr_mins);
                    }else{
                        this.arrMINS = String.format("%d", arr_mins);
                    }
                    if (arr_hour <= 9)
                    {
                        this.arrHOURS = "0"+ String.format("%d", arr_hour);
                    }else
                    {
                        this.arrHOURS = String.format("%d", arr_hour);
                    }
                    this.arrtime_mins = Day1 * 24 * 40 + arr_hour * 60 + arr_mins;
 
                }else
                {
                    int Day1 = Day + 1;
                    String arrday = week_num.get(Day1).substring(0, 1).toUpperCase() + week_num.get(Day1).substring(1, 3);
                    this.arrDAY = arrday;
                    
                    if (arr_mins <= 9)
                    {
                        this.arrMINS = "0"+ String.format("%d", arr_mins);
                    }else
                    {
                        this.arrMINS = String.format("%d", arr_mins);
                    }
                    if (arr_hour <= 9)
                    {
                        this.arrHOURS = "0"+ String.format("%d", arr_hour);
                    }else
                    { 
                        this.arrHOURS = String.format("%d", arr_hour);
                    }
                    this.arrtime_mins = Day1 * 24 * 60 + arr_hour * 60 + arr_mins;
                }   
            }else
            {
                String arrday = week_num.get(Day).substring(0, 1).toUpperCase() + week_num.get(Day).substring(1, 3);
                this.arrDAY = arrday;
                int arr_hour = hour + flighthour;
                if (arr_mins <= 9)
                {
                    this.arrMINS = "0"+ String.format("%d", arr_mins);
                }else
                {
                    this.arrMINS = String.format("%d", arr_mins);
                }
                if (arr_hour <= 9)
                {
                    this.arrHOURS = "0"+ String.format("%d", arr_hour);
                }else
                {
                    this.arrHOURS = String.format("%d", arr_hour);
                }   

                this.arrtime_mins = Day * 24 * 60 + arr_hour * 60 + arr_mins;

            }
        }






            
        
        
        
        double flight_dist = getDistance();
        int flight_distint = (int)Math.round(flight_dist);
        this.dist = flight_distint;  
        double flight_price = Math.round(getTicketPrice() * 100.0) / 100.0;
        
        
        
        
        


    }
    
    public static int find_time(String Time){
        week.put("monday", 0);
        week.put("tuesday", 1);
        week.put("wednesday",2);
        week.put("thursday", 3);
        week.put("friday", 4);
        week.put("saturday", 5);
        week.put("sunday", 6);
        String day = Time.split(" ")[0].toLowerCase();
        String time = Time.split(" ")[1];
        String hour_str = time.split(":")[0];
        String mins_str = time.split(":")[1];
        int hour = Integer.parseInt(hour_str);
        int mins = Integer.parseInt(mins_str);
        //System.out.println(day);
        
        int Day = week.get(day);
        int day_mins = Day * 24 * 60;
        int hour_mins = hour * 60;
        int total_mins = day_mins + hour_mins + mins;
        return total_mins;

    }
 
    // public static int get_diff(String time1, String time2){
    //     int a = find_time(time1);
    //     int b = find_time(time2);
    //     int diff = b - a;
    //     if (diff >= 0)
    //     {
    //         return diff;
    //     }else
    //     {
    //         int week_mins = 7 * 24 * 60;
    //         int over_diff = diff + week_mins;
    //         return over_diff;
    //     }
     
    // }


    //get the number of minutes this flight takes (round to nearest whole number)
    public int getDuration() {
        double d = Location.distance(this.dep, this.arr);
        double duration = d / speed;
        int duration_whole = (int)Math.round(duration);
        return duration_whole;

    }

    //implement the ticket price formula
    public double getTicketPrice(){
        double booked_double = this.num_booked;
        double capacity_double = this.capacity;
        double x = booked_double / capacity_double;
        double demand_diff = this.arr.demand - this.dep.demand;
        double d = getDistance();
        if (x > 0.0 && x <= 0.5)
        {
            y = -0.4 * x + 1;
        }else if (x > 0.5 && x <= 0.7)
        {
            double y = x + 0.3;
        }else if (x > 0.7 && x <= 1)
        {
            double y = (0.2 / Math.PI) * Math.atan(20*x -14) + 1;
        }else if (x == 0.0)
        {
            price = (d /100) * (30 + 4*(demand_diff));
            this.ticket_price = price;
            return price;
        }
        
        price = y * (d /100) * (30 + 4*(demand_diff));
        this.ticket_price = price;
        return price;

    }

    //book the given number of passengers onto this flight, returning the total cost
    public double book(int num) {
        
        double per_price = getTicketPrice();
        this.num_booked = this.num_booked + num;
        double total_cost = per_price * num;
        
        
        return total_cost;

    }

    //return whether or not this flight is full
    public boolean isFull() {
		if (this.num_booked == this.capacity)
        {
            return true;
        }else{
            return false;
        }
	}

    //get the distance of this flight in km
    public double getDistance() {
		double dis = Location.distance(this.dep, this.arr);
        return dis;
	}



    public static int find_arrtime(Flight x){
        int x_dep_time = find_time(x.datetime);
        int x_flight_time = x.getDuration();                        
        int xarr_time = x_dep_time + x_flight_time;
        return xarr_time;
    }






    //get the layover time, in minutes, between two flights
    public static int layover(Flight x, Flight y) {
        int y_dep_time = find_time(y.datetime);
        int xarr_t = find_arrtime(x);
        int lay_time = Math.abs(y_dep_time - xarr_t);
        return lay_time;

    }







    // public static void main(String[] args){
    //     Location Sydney = new Location("Sydney", -33.847927, 150.651786, 0.4);
	// 	Location NewYork = new Location("NewYork", 40.7, -74.26, -0.874);
    //     Location Berlin = new Location("Berlin", 52.5, 13.15, 0.22222);
    //     Location newloc1 = new Location("perth", -32.0397559, 115.681346, 0.5);
    //     Location London = new Location("London", 51.5285582, -0.2416812, 0.1);
    //     Location AliceSprings = new Location("AliceSprings", -23.7206896, 133.7977352, 0.056);
	// 	// Flight newflight1 = new Flight(1, "wednesday 04:00", newloc, newloc1, 250);
	// 	// Flight newflight2 = new Flight(2, "wednesday 03:15", newloc, newloc1, 280);
	// 	// Flight newflight3 = new Flight(3, "wednesday 08:20", newloc1, newloc, 280);
	// 	//Flight newflight4 = new Flight(4, "sunday 23:45", newloc, newloc1, 280);
	// 	Flight newflight5 = new Flight(5, "Monday 12:00", Sydney, AliceSprings, 0);
	//     newflight5.find_mins("Monday 12:00");
    //     System.out.println(newflight5.arrHOURS);


    // }

}










