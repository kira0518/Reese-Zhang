
"""
   Dorrigo Mobile Phone Contact Class.
  
   INFO1110 Assignment, Semester 2, 2020.
  
   dorrigo_contact
   
   == Contact data ==
   Each dorrigo_contact stores the first name, last name and phone number of a person. 
   These can be queried by calling the appropriate get method. They are updated 
   with new values. The phone number can only be a 6 - 14 digit number.
   The chat history is also stored. 
   
   
   == Chat history ==
   Each dorrigo_contact stores the history of chat messages related to this contact. 
   Suppose there is a conversation between Angus and Beatrice:
   
   Angus: Man, I'm so hungry! Can you buy me a burrito?
   Beatrice: I don't have any money to buy you a burrito.
   Angus: Please? I haven't eaten anything all day.
   
   Each time a message is added the name of the person and the message is 
   combined as above and recorded in the sequence it was received.
   
   The messages are stored in the instance variable. 
   Unfortunately there are only a maximum number of messages maximum to store and no more. 
   When there are more than 20 messages, oldest messages from this array are discarded and 
   only the most recent 20 messages remain. 
   
   The functions for chat history are 
     add_chat_message()
     get_last_message()
     get_oldest_message()
     clear_chat_history()
  
   Using the above conversation as an example
     add_chat_message("Angus", "Man, I'm so hungry! Can you buy me a burrito?");
     add_chat_message("Beatrice", "I don't have any money to buy you a burrito.");
     add_chat_message("Angus", "Please? I haven't eaten anything all day.");
  
     get_last_message() returns "Angus: Please? I haven't eaten anything all day."
     get_oldest_message() returns "Angus: Man, I'm so hungry! Can you buy me a burrito?"
  
     clear_chat_history()
     get_last_message() returns None
     get_oldest_message() returns None
  
  
   == Copy of contact ==
   It is necessary to make copy of this object that contains exactly the same data. 
   There are many hackers working in other parts of Dorrigo, so we cannot trust them 
   changing the data. A copy will have all the private data and chat history included.
  
  
   Please implement the methods provided, as some of the marking is
   making sure that these methods work as specified.
  
   @author A INFO1110 tutor.
   @date September, 2020
  
"""
class dorrigo_contact:
	
	def __init__(self, fname, lname, pnumber):
		""" construct a new dorrigo_contact
		    first name
		    last name
		    phone number
		    Initialise other values to sensible default
		"""
		self.fname = fname
		pass
	
	def get_first_name(self): 
		""" return the first name of the contact """
		return self.fname

	def get_last_name(self): 
		""" return the last name of the contact """
		pass

	def get_phone_number(self): 
		""" return the phone number of the contact as a string """
		pass

	def update_first_name(self, first_name):
		""" if first_name is None the method will do nothing and return. 
			The name will only update if the datatype is appropriate
		"""
		pass

	def update_last_name(self, last_name): 
		""" if last_name is None the method will do nothing and return
			The name will only update if the datatype is appropriate
		"""
		pass
	
	def update_phone_number(self, number): 
		""" only allows integer numbers (int type) between 6 and 14 digits
		   no spaces allowed, or prefixes of + symbols
		   leading 0 digits are allowed
		   return True if successfully updated
		   if number is None, number is set to an empty string and the method returns False
		"""
		pass
	
	def add_chat_message(self, who_said_it, message): 
		""" add a new message to the chat
		   The message will take the form
		   who_said_it + ": " + message
		   
		   if the history is full, the oldest message is replaced
		   Hint: keep track of the position of the oldest or newest message!
		   always returns True
		"""
		pass

	def clear_chat_history(self): 
		""" after this, both last and oldest message should be referring to index 0
		   all entries of chat history are set to None
		   always returns True
		"""
		pass

	def get_last_message(self): 
		""" returns the last message 
		   	if no messages, returns None
		"""
		pass
	
	def get_oldest_message(self): 
		""" returns the oldest message in the chat history
		   depending on if there was ever MAXIMUM_CHAT_HISTORY messages
		   1) less than MAXIMUM_CHAT_HISTORY, returns the first message
		   2) more than MAXIMUM_CHAT_HISTORY, returns the oldest
		   returns None if no messages exist
		"""
		pass


	def create_copy(self): 
		""" creates a copy of this contact
		   returns a new dorrigo_contact object with all data same as the current object
		"""
		pass
	
	def print_messages_oldest_to_newest(self): 
		""" -- NOT TESTED --
		   You can impelement this to help with debugging when failing ed tests 
		   involving chat history. You can print whatever you like
		   Implementers notes: the format is printf("%d %s\n", index, line); 
		"""
		pass


