import java.util.*;
import java.lang.*;
import java.io.*;

public class FlightScheduler {
	
	public ArrayList<Location> airports = new ArrayList<Location>();
	public ArrayList<Flight> flights = new ArrayList<Flight>();
	public ArrayList<String> airports_name = new ArrayList<String>();
	public ArrayList<Integer> flight_timesort = new ArrayList<Integer>();
	public HashMap<Integer, String> loc_msg = new HashMap<Integer, String>();
	public ArrayList<Integer> dep_timesort = new ArrayList<Integer>();
	public ArrayList<Integer> arr_timesort = new ArrayList<Integer>();
	public ArrayList<Integer> all_timesort = new ArrayList<Integer>();
	public ArrayList<Flight> all_flights = new ArrayList<Flight>();
	public double lat_double;
	public double lon_double;
	public double demand_double;
	public Location apt;
	public String apt_name;
	public int count;
	public String Datetime;
	public boolean inside;
	public int d2_hour;
	public int d2_mins;
	public static String msg_dep;
	public static String msg_arr;
	public Location newDep;
	public Location newArr;
	public int cap;
	public int abc;
    private static FlightScheduler instance;
	public String msg;
	public String a_name;
	public String b_name;
	public int cmd;
	public String weekname;
	public String dname;
	public static String aname;
	public String airport_name;
	public Location thisairport;
	public Location thisAirport;
	public Flight currentflight;
	public double book_msg;
	public int cmd_book;
	public int flight_id;
	public Location currentLocation;
	public boolean ins;
	public int flightdate;
	public boolean hasairport;
	public boolean start_present;
	public int remove_id;
	public Flight removed_flight;
	public String line_input;
	//public StringBuilder sb;
	public int f_excount;
	public double abcd;
	public double abcde;
	public boolean id_inside;

    public static void main(String[] args) {
		
        
		
		
		
		instance = new FlightScheduler(args);
        instance.run();

    }

    public static FlightScheduler getInstance() {
        return instance;
    }

    public FlightScheduler(String[] args) {}

    public void run() {
        // Do not use System.exit() anywhere in your code,
        // otherwise it will also exit the auto test suite.
        // Also, do not use static attributes otherwise
        // they will maintain the same values between testcases.

        // START YOUR CODE HERE
	

	// 	System.out.println(airports);
	// 	int abc = addLocation("berlin", "52.5", "13.15", "0.22222");
	// 	int cba = addLocation("NewYork", "40.7", "-74.26", "-0.874");
	// 	// System.out.println(abc);
	// 	// System.out.println(airports);

		Scanner scanner = new Scanner(System.in);
 		System.out.print("User: ");
		while (scanner.hasNextLine())
		{
		String line = scanner.nextLine();
		
		String[] command = line.split(" ");
		//System.out.println(line);
		if (command[0].equalsIgnoreCase("HELP"))
		{
			if (command.length != 1)
			{
				System.out.println("Invalid command. Type 'help' for a list of commands.");
			}else
			{
				System.out.println("FLIGHTS - list all available flights ordered by departure time, then departure location name\n"+
				"FLIGHT ADD <departure time> <from> <to> <capacity> - add a flight\n"+
				"FLIGHT IMPORT/EXPORT <filename> - import/export flights to csv file\n"+
				"FLIGHT <id> - view information about a flight (from->to, departure arrival times, current ticket price, capacity, passengers booked)\n"+
				"FLIGHT <id> BOOK <num> - book a certain number of passengers for the flight at the current ticket price, and then adjust the ticket price to reflect the reduced capacity remaining. If no number is given, book 1 passenger. If the given number of bookings is more than the remaining capacity, only accept bookings until the capacity is full.\n"+
				"FLIGHT <id> REMOVE - remove a flight from the schedule\n"+
				"FLIGHT <id> RESET - reset the number of passengers booked to 0, and the ticket price to its original state.\n\n"+
				"LOCATIONS - list all available locations in alphabetical order\n"+
				"LOCATION ADD <name> <lat> <long> <demand_coefficient> - add a location\n"+
				"LOCATION <name> - view details about a location (it's name, coordinates, demand coefficient)\n"+
				"LOCATION IMPORT/EXPORT <filename> - import/export locations to csv file\n"+
				"SCHEDULE <location_name> - list all departing and arriving flights, in order of the time they arrive/depart\n"+
				"DEPARTURES <location_name> - list all departing flights, in order of departure time\n"+
				"ARRIVALS <location_name> - list all arriving flights, in order of arrival time\n\n"+
				"TRAVEL <from> <to> [sort] [n] - list the nth possible flight route between a starting location and destination, with a maximum of 3 stopovers. Default ordering is for shortest overall duration. If n is not provided, display the first one in the order. If n is larger than the number of flights available, display the last one in the ordering.\n\n"+
				"can have other orderings:\n"+
				"TRAVEL <from> <to> cost - minimum current cost\n"+
				"TRAVEL <from> <to> duration - minimum total duration\n"+
				"TRAVEL <from> <to> stopovers - minimum stopovers\n"+
				"TRAVEL <from> <to> layover - minimum layover time\n"+
				"TRAVEL <from> <to> flight_time - minimum flight time\n\n"+
				"HELP - outputs this help string.\n"+
				"EXIT - end the program.");
			}
		

		}else if (command[0].equalsIgnoreCase("exit"))
		{
			if (command.length != 1)
			{
				System.out.println("Invalid command. Type 'help' for a list of commands.");
			}else
			{
				System.out.println("Application closed.");
				return;
			}
			
		}else if (command[0].equalsIgnoreCase("location"))
		{
			if (command.length == 1)
			{
				System.out.println("Usage:\nLOCATION <name>\nLOCATION ADD <name> <latitude> <longitude> <demand_coefficient>\nLOCATION IMPORT/EXPORT <filename>");
			}else if (command[1].equalsIgnoreCase("import"))
			{
				if(command.length == 3)
				{
					
					String[] importcommand = {command[0],command[1], command[2]};
					importLocations(importcommand);
				}
				
			}else if (command[1].equalsIgnoreCase("export"))
			{
				if (command.length != 3)
				{
					System.out.println("Error writing file.");
				}else
				{
					try (PrintWriter writer = new PrintWriter(new File(command[2]))) 
					{
					StringBuilder sb = new StringBuilder();
					int f_excount	= 0;
					for (int i=0;i<airports.size();i++)
					{
						sb.append(airports.get(i).name);
						sb.append(", ");
						sb.append(airports.get(i).lat);
						sb.append(", ");
						sb.append(+airports.get(i).lon);
						sb.append(", ");
						sb.append(airports.get(i).demand);
						sb.append("\n");
						f_excount = f_excount + 1;

						
					}
						writer.write(sb.toString());
						if (f_excount == 1)
						{
							System.out.println("Exported "+f_excount+" location.");
						}else
						{
							System.out.println("Exported "+f_excount+" locations.");
						}
						

					} catch (FileNotFoundException e) 
				{
					System.out.println("Error writing file.");
				}
				}
				

				
			}else if (command[1].equalsIgnoreCase("add"))
			{
				if (command.length != 6)
				{
					System.out.println("Usage: LOCATION ADD <name> <lat> <long> <demand_coefficient>\nExample: LOCATION ADD Sydney -33.847927 150.651786 0.2");
				}else
				{
					
					int msg = addLocation(command[2], command[3], command[4], command[5]);
					if (msg == -1)
					{
						System.out.println("This location already exists.");
					}else if (msg == -2)
					{
						System.out.println("Invalid latitude. It must be a number of degrees between -85 and +85.");
					}else if (msg == -3)
					{
						System.out.println("Invalid longitude. It must be a number of degrees between -180 and +180.");
					}else if (msg == -4)
					{
						System.out.println("Invalid demand coefficient. It must be a number between -1 and +1.");
					}else if (msg == 1)
					{
						//Location newapt = get_airport(command[2]);
						System.out.println("Successfully added location "+ command[2] +".");
									
					}	
				
				
				}
			}else if (command.length == 2)
			{
				boolean ins = false;
				for(int i=0;i<airports.size();i++)
				{
					if (airports.get(i).name.equalsIgnoreCase(command[1]))
					{
						ins = true;
						break;
					}
				}
				
				if(ins == false)
				{
					System.out.println("Invalid location name.");
				}else
				{
					Location currentLocation = get_airport(command[1]);
					System.out.print("Location:    "+ currentLocation.name+"\n"+
					"Latitude:    ");
					System.out.printf("%.6f", currentLocation.lat);
					System.out.print("\n"+"Longitude:   ");
					System.out.printf("%.6f", currentLocation.lon);
					System.out.print("\n"+"Demand:      ");
					if (currentLocation.demand < 0)
					{
						System.out.printf("%.4f", currentLocation.demand);
					}else
					{
						System.out.print("+");
						System.out.printf("%.4f", currentLocation.demand);
					}
					System.out.println();
				}
				
				
				
				

			}

			
		}else if (command[0].equalsIgnoreCase("flight")) // commands
		{
			if (command.length == 1)
			{
				System.out.println("Usage:\nFLIGHT <id> [BOOK/REMOVE/RESET] [num]\nFLIGHT ADD <departure time> <from> <to> <capacity>\nFLIGHT IMPORT/EXPORT <filename>");
			}else if (command[1].equalsIgnoreCase("add"))   // flight commands
			{	
				//System.out.println(command[1]);
				if (command.length != 7)
				{	
					System.out.println("Usage: FLIGHT ADD <departure time> <from> <to> <capacity>\nExample: FLIGHT ADD Monday 18:00 Sydney Melbourne 120");

				}else
				{	
					int msg = addFlight(command[2], command[3], command[4], command[5], command[6]);
					if (msg == -1)
					{
						System.out.println("Invalid departure time. Use the format <day_of_week> <hour:minute>, with 24h time.");
					}else if(msg == -2)
					{
						System.out.println("Invalid starting location.");
					}else if(msg == -3)
					{
						System.out.println("Invalid ending location.");
					}else if(msg == -4)
					{
						System.out.println("Invalid positive integer capacity.");
					}else if(msg == -5)
					{
						System.out.println("Source and destination cannot be the same place");
					}else if(msg == -6)
					{
						System.out.println("Scheduling conflict! This flight clashes with "+ msg_dep);
					}else if(msg == -7)
					{
						System.out.println("Scheduling conflict! This flight clashes with "+msg_arr);
					}else if(msg == 1)
					{	
						System.out.println("Successfully added Flight "+ (flights.size()-1)+".");
					}
				}
			}else if (command[1].equalsIgnoreCase("import"))
			{
				String[] importcommand = {command[0],command[1], command[2]};
				importFlights(importcommand);
			}else if (command[1].equalsIgnoreCase("export"))    // 
			{
				if (command.length != 3)
				{
					System.out.println("Error writing file.");
				}else
				{
					try (PrintWriter writer = new PrintWriter(new File(command[2]))) 
					{
					StringBuilder sb = new StringBuilder();
					int f_excount	 = 0;
					for (int i=0;i<flights.size();i++)
					{
						sb.append(flights.get(i).datetime);
						sb.append(", ");
						sb.append(flights.get(i).dep.name);
						sb.append(", ");
						sb.append(flights.get(i).arr.name);
						sb.append(", ");
						sb.append(flights.get(i).capacity);
						sb.append(", ");
						sb.append(flights.get(i).num_booked);
						sb.append("\n");
						f_excount = f_excount + 1;

						
					}
						writer.write(sb.toString());
						if (f_excount == 1)
						{
							System.out.println("Exported "+f_excount+" flight.");
						}else
						{
							System.out.println("Exported "+f_excount+" flights.");
						}
						

					} catch (FileNotFoundException e) 
					{
						System.out.println("Error writing file.");
					}
				}
				
				
			}else
			{		
				try
				{
					int cmd = Integer.parseInt(command[1]);
					if (command.length == 2)
					{
						if (cmd <0)
						{
							System.out.println("Invalid Flight ID.");
							//System.out.println();
						}
						boolean id_inside = false;
						for (int j=0;j<flights.size();j++)
						{
							if (flights.get(j).Id == cmd)
							{
								id_inside = true;
								break;
							}
						}
						if (id_inside == false)
						{
							System.out.println("Invalid Flight ID.");
						}else
						{
							for(int i=0;i<flights.size(); i++)
							{
								if(cmd == flights.get(i).Id)
								{
									Flight currentflight = get_flight(command[1]);
									System.out.print("Flight "+cmd+"\n"+
									"Departure:    "+currentflight.depDAY+" "+currentflight.depHOUR+":"+currentflight.depMINS+" "+currentflight.dep.name+"\n"+
									"Arrival:      "+currentflight.arrDAY+" "+currentflight.arrHOURS+":"+currentflight.arrMINS+" "+currentflight.arr.name+"\n"+
									"Distance:     ");
									System.out.printf("%,1d", currentflight.dist);

									System.out.print("km\n"+ "Duration:     "+ currentflight.flight_duration + "\nTicket Cost:  "+"$");
									System.out.printf("%.2f",currentflight.ticket_price);
									System.out.println("\n"+"Passengers:   "+currentflight.num_booked+"/"+currentflight.capacity);		            
								}
							}
						}
						
						
					}else
					{
						if (command[2].equalsIgnoreCase("BOOK"))
						{
							try
							{
								Flight currentflight = get_flight(command[1]);
								if (command.length == 3)
								{
									currentflight.book(1);
								}else
								{
									int cmd_book = Integer.parseInt(command[3]);
									//boolean full = currentflight.isFull;
									if (currentflight != null)
									{	
										
										if (currentflight.isFull() == true)
										{
											
											System.out.print("Invalid number of passengers to book.");
										}else if (cmd_book<0)
										{
											System.out.println("Invalid number of passengers to book.");
										}else
										{
											if (cmd_book == 0)
											{
												if ((currentflight.num_booked + 1) > currentflight.capacity)
												{
												int book_diff = currentflight.capacity - currentflight.num_booked;
												abcde = 0;

												book_msg = currentflight.book(1);
												abcde = abcde + book_msg;
												
		
												System.out.print("Booked "+ 1 +" passengers on flight "+command[1]+" for a total cost of $");
												System.out.printf("%.2f", abcde);
												System.out.println();
												System.out.print("Flight is now full.");
												}
												else
												{
													abcd = 0;
													book_msg = currentflight.book(1);
													//double book_msg = currentflight.book(cmd_book);
													System.out.print("Booked "+ 1 +" passengers on flight "+command[1]+" for a total cost of $");
													System.out.printf("%.2f", abcd);
												}
											}else
											{
												if ((currentflight.num_booked + cmd_book) > currentflight.capacity)
												{
												int book_diff = currentflight.capacity - currentflight.num_booked;
												abcde = 0;
												for (int i=0;i<book_diff;i++)
												{
													book_msg = currentflight.book(1);
													abcde = abcde + book_msg;
												}

												System.out.print("Booked "+ book_diff +" passengers on flight "+command[1]+" for a total cost of $");
												System.out.printf("%.2f", abcde);
												System.out.println();
												System.out.print("Flight is now full.");
												}else
												{
												abcd = 0;
												for (int i=0;i<cmd_book;i++)
												{
													book_msg = currentflight.book(1);
													abcd = abcd + book_msg;
												}
												
												
												//double book_msg = currentflight.book(cmd_book);
												System.out.print("Booked "+ cmd_book +" passengers on flight "+command[1]+" for a total cost of $");
												System.out.printf("%.2f", abcd);
												}
											}
											
										}
									}
									
								}
								

							}catch (NumberFormatException e)
							{
								System.out.print("Invalid number of passengers to book.");
							}
							System.out.println();
						}else if (command[2].equalsIgnoreCase("REMOVE"))
						{
							int flight_id = Integer.parseInt(command[1]);
							for (int i=0;i<flights.size();i++)
							{
								if(flight_id == flights.get(i).Id)
								{
									Flight removed_flight = flights.get(i);
									int remove_id = flights.get(i).Id;
									flights.remove(i);
									
									System.out.println("Removed Flight "+remove_id+", "+removed_flight.depDAY+" "+removed_flight.depHOUR+":"+removed_flight.depMINS+" "+removed_flight.dep.name+" --> "+
									removed_flight.arr.name+", from the flight schedule.");
								}
							}
						}else if (command[2].equalsIgnoreCase("RESET"))
						{
							Flight currentflight = get_flight(command[1]);
							currentflight.num_booked = 0;
							currentflight.getTicketPrice();
							System.out.println("Reset passengers booked to 0 for Flight "+currentflight.Id+", "+currentflight.datetime+" "+
							currentflight.dep.name+" --> "+currentflight.arr.name+".");
						}
					}
				
				}catch (NumberFormatException e)
				{
					System.out.println("Invalid Flight ID.");
					//System.out.println();
				}
			}
				
		}else if (command[0].equalsIgnoreCase("EXIT"))
		{
			
			System.out.println("Application closed.");
			return;
		}else if (command[0].equalsIgnoreCase("locations"))
		{
			if (airports.size()==0)
			{
				System.out.println("Locations (0):");
				System.out.println("(None)");
			}else
			{
				for(int i=0;i<airports.size();i++)
				{
					airports_name.add(airports.get(i).name);
				}
				Collections.sort(airports_name);
				System.out.println("Locations ("+airports_name.size()+"):");
				for (int j=0;j<airports_name.size();j++)
				{
					
					Location thislocation = get_airport(airports_name.get(j));
					if (j <airports_name.size()-1)
					{
						System.out.print(thislocation.name+", ");
					}else
					{
						System.out.print(thislocation.name);
					}
					
				}
				System.out.println();		

				airports_name.clear();
			}
			
			
			
			
			

		}else if (command[0].equalsIgnoreCase("flights"))
		{
			if (flights.size() == 0)
			{
				System.out.println("Flights");
				System.out.println("-------------------------------------------------------");
				System.out.println("ID   Departure   Arrival     Source --> Destination");
				System.out.println("-------------------------------------------------------");
				System.out.println("(None)");
			}else
			{
				System.out.println("Flights");
				System.out.println("-------------------------------------------------------");
				System.out.println("ID   Departure   Arrival     Source --> Destination");
				System.out.println("-------------------------------------------------------");
				
				for(int i=0;i<flights.size();i++)
				{
					flight_timesort.add(flights.get(i).deptime_mins);
				}
				Collections.sort(flight_timesort);
				for (int j=0;j<flight_timesort.size();j++)
				{
					Flight thisallflight = get_flight_in_time(flight_timesort.get(j));
					System.out.printf("%4s", thisallflight.Id);
					System.out.println(" "+thisallflight.depDAY+" "+thisallflight.depHOUR+":"+thisallflight.depMINS+ "   "+thisallflight.arrDAY+" "+thisallflight.arrHOURS+":"+thisallflight.arrMINS+"   "+thisallflight.dep.name+" --> "+thisallflight.arr.name);
				}
				flight_timesort.clear();
				
			}
		}else if (command[0].equalsIgnoreCase("DEPARTURES"))
		{
			if (command.length < 2)
			{
				System.out.println("This location does not exist in the system.");
			}else
			{
				boolean hasairport = false;
				for (int x=0;x<airports.size();x++)
				{
					if (airports.get(x).name.equalsIgnoreCase(command[1]))
					{
						hasairport = true;
						break;
					}
				}

				if (hasairport == false)
				{
					System.out.println("This location does not exist in the system.");
				}else
				{
					System.out.println(command[1]);
					System.out.println("-------------------------------------------------------");
					System.out.println("ID   Time        Departure/Arrival to/from Location");
					System.out.println("-------------------------------------------------------");
					Location currentLocation = get_airport(command[1]);
					
					if (currentLocation.Departure.size() == 0)
					{
						System.out.println("(None)");
					}else
					{
						for(int i=0;i<currentLocation.Departure.size();i++)
						{
							dep_timesort.add(currentLocation.Departure.get(i).deptime_mins);
						}
						Collections.sort(dep_timesort);
						for (int j=0;j<dep_timesort.size();j++)
						{

							for(int k=0;k<currentLocation.Departure.size();k++)
							{
								if(currentLocation.Departure.get(k).deptime_mins == dep_timesort.get(j))
								{
									Flight thisallflight = currentLocation.Departure.get(k);
									System.out.printf("%4s", thisallflight.Id);
									System.out.println(" "+thisallflight.depDAY+" "+thisallflight.depHOUR+":"+thisallflight.depMINS+"   "+"Departure to "+thisallflight.arr.name);
								}
							}

							
						}
						dep_timesort.clear();
					}
				}
				
				
			}

		}else if (command[0].equalsIgnoreCase("arrivals"))
		{
			if (command.length < 2)
			{
				System.out.println("This location does not exist in the system.");
			}else
			{
				boolean hasairport = false;
				for (int x=0;x<airports.size();x++)
				{
					if (airports.get(x).name.equalsIgnoreCase(command[1]))
					{
						hasairport = true;
						break;
					}
				}
				if (hasairport == false)
				{
					System.out.println("This location does not exist in the system.");
				}else
				{
					System.out.println(command[1]);
					System.out.println("-------------------------------------------------------");
					System.out.println("ID   Time        Departure/Arrival to/from Location");
					System.out.println("-------------------------------------------------------");
					Location currentLocation = get_airport(command[1]);

					
					if (currentLocation.Arrival.size() == 0)
					{
						System.out.println("(None)");
					}else
					{
						for(int i=0;i<currentLocation.Arrival.size();i++)
						{
							arr_timesort.add(currentLocation.Arrival.get(i).arrtime_mins);
						}
						Collections.sort(arr_timesort);

						for (int j=0;j<arr_timesort.size();j++)
						{
							for(int k=0;k<currentLocation.Arrival.size();k++)
							{
								if(currentLocation.Arrival.get(k).arrtime_mins == arr_timesort.get(j))
								{
									
									Flight thisallflight = currentLocation.Arrival.get(k);
									System.out.printf("%4s", thisallflight.Id);
									System.out.println(" "+thisallflight.arrDAY+" "+thisallflight.arrHOURS+":"+thisallflight.arrMINS+"   "+"Arrival from "+thisallflight.dep.name);
								}
							}
							
						}


					}

				}
				arr_timesort.clear();
			
			
			
			
			
			}
			
		}else if (command[0].equalsIgnoreCase("schedule"))
		{
			if (command.length < 2)
			{
				System.out.println("This location does not exist in the system.");
			}else
			{
				boolean hasairport = false;
				for (int x=0;x<airports.size();x++)
				{
					if (airports.get(x).name.equalsIgnoreCase(command[1]))
					{
						hasairport = true;
						break;
					}
				}
				if (hasairport == false)
				{
					System.out.println("This location does not exist in the system.");
				}else
				{
					System.out.println(command[1]);
					System.out.println("-------------------------------------------------------");
					System.out.println("ID   Time        Departure/Arrival to/from Location");
					System.out.println("-------------------------------------------------------");
					Location currentLocation = get_airport(command[1]);
					if (currentLocation.Arrival.size() == 0 && currentLocation.Departure.size() == 0)
					{
						System.out.println("(None)");
					}else
					{
						for(int i=0;i<currentLocation.Arrival.size();i++)
						{
							all_timesort.add(currentLocation.Arrival.get(i).arrtime_mins);
						}
						for(int l=0;l<currentLocation.Departure.size();l++)
						{
							all_timesort.add(currentLocation.Departure.get(l).deptime_mins);
						}
						Collections.sort(all_timesort);
						for(int i=0;i<currentLocation.Arrival.size();i++)
						{
							all_flights.add(currentLocation.Arrival.get(i));
						}
						for(int l=0;l<currentLocation.Departure.size();l++)
						{
							all_flights.add(currentLocation.Departure.get(l));
						}
						
						for (int i=0;i<all_flights.size();i++)
						{
							for (int j=0;j<all_timesort.size();j++)
							{
								if (all_flights.get(i).arrtime_mins == all_timesort.get(j))
								{
									Flight thisallflight = all_flights.get(i);
									System.out.printf("%4s", thisallflight.Id);
									System.out.println(" "+thisallflight.arrDAY+" "+thisallflight.arrHOURS+":"+thisallflight.arrMINS+"   "+"Arrival from "+thisallflight.dep.name);
									
								}else if (all_flights.get(i).deptime_mins == all_timesort.get(j))
								{
									Flight thisallflight = all_flights.get(i);
									System.out.printf("%4s", thisallflight.Id);
									System.out.println(" "+thisallflight.depDAY+" "+thisallflight.depHOUR+":"+thisallflight.depMINS+"   "+"Departure to "+thisallflight.arr.name);
								}
							}
						}
						all_timesort.clear();

						


						
					
					
					
					
					
					
					
					}





				}
			}


		}else
		{
			System.out.println("Invalid command. Type 'help' for a list of commands.");
			
		}
			

			
		
		System.out.println();
		System.out.print("User: ");
		

	}


	}

    	
	// Add a flight to the database
	// handle error cases and return status negative if error 
	// (different status codes for different messages)
	// do not print out anything in this function
	public int addFlight(String date1, String date2, String start, String end, String capacity){
		//check scheduling conflicts for starting location first, the ending location.
		String[] weekday = {"monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday"};
		String date1_lower = date1.toLowerCase();
		boolean inside = false;
		
		for (int i=0;i<weekday.length; i++)
		{	
			if (weekday[i].equalsIgnoreCase(date1_lower)== true)
			{
				inside = true;
				break;
			}
		}
		if (inside == false)
		{
			return -1;
		}

		try
		{
			String date2_hour = date2.split(":")[0];
			String date2_mins = date2.split(":")[1];
			int d2_hour = Integer.parseInt(date2_hour);
			int d2_mins = Integer.parseInt(date2_mins);
			if (d2_hour < 0 || d2_hour > 23)
			{
				return -1;
			}else if (d2_mins < 0 || d2_mins > 50)
			{
				return -1;
			}

		}catch (Exception e)
		{
			return -1;
		}
		//System.out.println(airports.size());
		inside = false;
		for (int j = 0; j < airports.size(); j++)
		{	
			Location apt = airports.get(j);
			String apt_name = start;
			
			if (apt_name.equalsIgnoreCase(start) == true)
			{
				inside = true;
				break;
			}

		}
		if (inside == false)
		{
			return -2;
		}


		inside = false;
		for (int i = 0; i < airports.size(); i++)
		{
			Location apt = airports.get(i);
			String apt_name = apt.name;
			
			if (apt_name.equalsIgnoreCase(end) == true)
			{
				inside = true;
				break;
			}

		}
		//System.out.println(apt_name);
		if (inside == false)
		{
			return -3;
		}

		try
		{
			cap = Integer.parseInt(capacity);
			
			if (cap <= 0)
			{
				return -4;
			}
		}catch (Exception e)
		{
			return -4;
		}
		if (start.equalsIgnoreCase(end)==true)
		{
			return -5;
		}
		
		
		for(int i=0;i < airports.size(); i++)
		{	
			
			if(airports.get(i).name.equalsIgnoreCase(start) == true)
			{
				
				//System.out.println(airports.get(i));
				newDep = airports.get(i);
				//System.out.println(newDep);

			} else if(airports.get(i).name.equalsIgnoreCase(end) == true)
				{
					// System.out.print(airports.get(i).name+",");
					// System.out.println(end);
					newArr = airports.get(i);
					// System.out.println(airports.get(i).name);
					
				} 
		}
		//System.out.println(newArr);
		//System.out.println(newDep);
		int Id = flights.size();
		String Datetime = date1+" "+date2;
		//System.out.println(newArr.Arrival);
		
		if (newDep != null && newArr != null)
		{
			Flight newflight = new Flight(Id, Datetime, newDep, newArr, cap);
			//System.out.println(newflight.arr.Departure);
			msg_dep = newDep.hasRunwayDepartureSpace(newflight);
			msg_arr = newArr.hasRunwayDepartureSpace(newflight);
			//System.out.println(newflight.arr.Departure);
			msg_dep = newDep.hasRunwayDepartureSpace(newflight);
			msg_arr = newArr.hasRunwayDepartureSpace(newflight);
			if (msg_dep != null)
			{
				//System.out.println("Scheduling conflict! This flight clashes with "+msg_dep);
				return -6;
			}else if (msg_arr != null)
			{	
				//System.out.println("Scheduling conflict! This flight clashes with "+msg_arr);
				return -7;
			}else if (msg_dep == null && msg_arr == null)
			{
				
				if (newflight != null)
				{
					flights.add(newflight);
					newflight.dep.addDeparture(newflight);
					newflight.arr.addArrival(newflight);
					
					newflight.find_mins(newflight.datetime);
					
					//System.out.println(newArr.name);
					return 1;
				}
				
			}
		}
		
		//System.out.println(newArr.name);
		return 0;
	}
	


	
	// Add a location to the database
    // do not print out anything in this function
    // return negative numbers for error cases
	public int addLocation(String name, String lat, String lon, String demand) {
		
		
		//System.out.println(apt_name);
		//System.out.println(name);
		int count = 0;
		for (int i = 0; i < airports.size(); i++)
		{
			Location apt = airports.get(i);
			String apt_name = apt.name.toLowerCase();
			
			if (apt_name.equalsIgnoreCase(name) == true)
			{
				return -1;
			}

		}
		double lat_double = 0.0;
		double lon_double = 0.0;
		double demand_double = 0.0; 
		try
        {
        	lat_double = Double.parseDouble(lat);
			lon_double = Double.parseDouble(lon);
			demand_double = Double.parseDouble(demand);
        }catch (NumberFormatException e)
        {
            
			return -2;
        }
		if (lat_double > 85 || lat_double < -85)
		{
			
			return -2;
		}else if (lon_double > 180 || lon_double < -180)
			{
				
				return -3;
			}else if (demand_double > 1 || demand_double < -1)
				{
					
					return -4;
				}else
			{
				Location newlocation = new Location(name, lat_double, lon_double, demand_double);
				airports.add(newlocation);
				aname = newlocation.name;
				//String b_name = a_name;
				//System.out.println(b_name);
				return 1;
			}


	}
	
	//flight import <filename>
	public void importFlights(String[] command) {
		try {
			if (command.length < 3) throw new FileNotFoundException();
			BufferedReader br = new BufferedReader(new FileReader(new File(command[2])));
			String line;
			int count = 0;
			int err = 0;
			
			while ((line = br.readLine()) != null) {
				String[] lparts = line.split(",");
				if (lparts.length < 5) continue;
				String[] dparts = lparts[0].split(" ");
				if (dparts.length < 2) continue;
				int booked = 0;
				
				try {
					booked = Integer.parseInt(lparts[4]);
					
				} catch (NumberFormatException e) {
					continue;
				}
				
				int status = addFlight(dparts[0], dparts[1], lparts[1], lparts[2], lparts[3]);
				//System.out.println(lparts[1]);
				if (status < 0) {
					err++;
					continue;
				}
				count++;
			}
			br.close();
			System.out.println("Imported "+count+" flight"+(count!=1?"s":"")+".");
			if (err > 0) {
				if (err == 1) System.out.println("1 line was invalid.");
				else System.out.println(err+" lines were invalid.");
			}
			
		}catch (IOException e) {
			System.out.println("Error reading file.");
			return;
		}
	}
	
	//location import <filename>
	public void importLocations(String[] command) {
		try {
			if (command.length < 3) throw new FileNotFoundException();
			BufferedReader br = new BufferedReader(new FileReader(new File(command[2])));
			String line;
			int count = 0;
			int err = 0;
			
			while ((line = br.readLine()) != null) {
				String[] lparts = line.split(",");
				if (lparts.length < 4) continue;
								
				int status = addLocation(lparts[0], lparts[1], lparts[2], lparts[3]);
				if (status < 0) {
					err++;
					continue;
				}
				count++;
			}
			br.close();
			System.out.println("Imported "+count+" location"+(count!=1?"s":"")+".");
			if (err > 0) {
				if (err == 1) System.out.println("1 line was invalid.");
				else System.out.println(err+" lines were invalid.");
			}
			
		} catch (IOException e) {
			System.out.println("Error reading file.");
			return;
		}
	}


	public Location get_airport(String airport_name){
		for(int i=0;i<airports.size();i++)
		{
			if(airport_name.equalsIgnoreCase(airports.get(i).name))
			{
				Location thisairport = airports.get(i);
				return thisairport;
			}
		}
		return null;


	}

	public Flight get_flight(String flightid){
		int flightid_int = Integer.parseInt(flightid);
		for(int i=0;i<flights.size();i++)
		{
			if(flightid_int == flights.get(i).Id)
			{
				Flight thisflight = flights.get(i);
				return thisflight;
			}
		}
		return null;


	}

	public Flight get_flight_in_time(int flightdate){
		for(int i=0;i<flights.size();i++)
		{
			if(flightdate == flights.get(i).deptime_mins)
			{
				Flight thisflight = flights.get(i);
				return thisflight;
			}
		}
		return null;

	}
	public Flight get_arrflight_in_time(int arrflightdate){
		
		for(int i=0;i<flights.size();i++)
		{
			if(flightdate == flights.get(i).arrtime_mins)
			{
				Flight thisflight = flights.get(i);
				return thisflight;
			}
		}
		return null;

	}
	
	
	public Flight get_depflight_in_time(int flightdate){
		
		for(int i=0;i<flights.size();i++)
		{
			if(flightdate == flights.get(i).deptime_mins)
			{
				Flight thisflight = flights.get(i);
				return thisflight;
			}
		}
		return null;

	}
	
	
	


	














}
