import sys
import socket
import os

def main():
	if len(sys.argv) < 2:  #to determin whether has configuration Argument
		sys.exit("Missing Configuration Argument")

	file_list = ['a', 'b', 'c', 'd']
	cmd_list = ['a', 'b', 'c', 'd']
	try:
		f = open(sys.argv[1], 'r')  #open the config file
		lines = f.readlines() 

		f.close()
		i = 0	
		while i < len(lines):
			line = lines[i].strip()
			cmd = line.split("=")
			i += 1
			#determine the config file has correct content or not
			if cmd[0] == "staticfiles":
				staticfiles = cmd[1]
				file_list[0] = cmd[1]
				cmd_list[0] = cmd[0] 
			elif cmd[0] == "cgibin":
				cgibin = cmd[1]
				file_list[1] = cmd[1]
				cmd_list[1] = cmd[0] 
			elif cmd[0] == "port":
				port = cmd[1]
				file_list[2] = cmd[1]
				cmd_list[2] = cmd[0] 
			elif cmd[0] == "exec":
				exec = cmd[1]
				file_list[3] = cmd[1]
				cmd_list[3] = cmd[0] 


	except IOError:
		sys.exit("Unable To Load Configuration File")



	if "staticfiles" not in cmd_list:
		print("Missing Field From Configuration File")
		sys.exit()
	if "cgibin" not in cmd_list:
		print("Missing Field From Configuration File")
		sys.exit()
	if "port" not in cmd_list:
		print("Missing Field From Configuration File")
		sys.exit()
	if "exec" not in cmd_list:
		print("Missing Field From Configuration File")
		sys.exit()
				

	cgi_name = cgibin.split("/")



	port = int(port) 
	server = socket.socket(socket.AF_INET, socket.SOCK_STREAM) #setup tcp server
	server.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
	server_address = ('127.0.0.1', port)
	server.bind(server_address) # bind the server address
	server.listen(5)#allow five clients to connect
	os.environ['SERVER_ADDR'] = '127.0.0.1' #set the environment variable 
	os.environ['SERVER_PORT'] = str(port)	#set the environment variable
	
	while True:
		client_socket, clientAddr = server.accept()		#setup the client

		
		os.environ["REMOTE_ADDRESS"] = str(clientAddr[0])
		os.environ["REMOTE_PORT"] = str(clientAddr[1])

		recv_data = client_socket.recv(1024).decode() #receive the data get from the server
		recv_data_list = list(recv_data.split("\r\n"))
		con_pid = os.fork()
		if con_pid == 0:   

			name = recv_data_list[0]
			e = name.split(" ")
			ex = e[1]
			os.environ["REQUEST_URI"] = ex #set the environment variable
			cgi_judge = ex.split("/")
			query_name = name.split(" ") 
			cgi_query = "".join(query_name[1])
			query_path = query_name[1].split("?")
			if '?' in cgi_query:  #determine it is query string or not
				os.environ["QUERY_STRING"] = query_path[1] #set up the environment variable
				name = query_name[0] + " " + query_path[0]+ " " + query_name[2] #update the path from the query string
				if cgi_judge[1] == cgi_name[1]:
					ex_path = ex.split("?")
					ex = ex_path[0]
					os.environ["QUERY_STRING"] = ex_path[1]


			for line in recv_data_list:
				line = line.split(", ")
				for lines in line:
					lines = lines.split(" ")
					
					if lines[0] == "GET":
						os.environ["REQUEST_METHOD"] = lines[0]   #set up the environment variable
					elif lines[0] == "User-Agent:":
						os.environ["HTTP_USER_AGENT"] = lines[1]
					elif lines[0] == "Host":
						os.environ["HTTP_HOST"] = lines[1]
					elif lines[0] == "Accept":
						os.environ["HTTP_ACCEPT_ENCODING"] = lines[2]
					
			
			file_ending = os.path.splitext(ex)[1]
			ending = ["text/plain", "text/html", "application/javascript", "text/css", "image/png", "image/jpeg", "image/jpeg" , "text/xml"]


			if cgi_judge[1] == cgi_name[1]: #check it is cgi or not
				
				filename = "." + ex
				read, write = os.pipe() #pipe the output for execle
					
				pid = os.fork() 
				if pid == 0:  #child process
					os.close(read)
					writing = os.fdopen(write, "w") 
					os.dup2(write, 1) #put the output of execle into parent
					writing.close()
					try:
						os.execle(exec, exec, filename, os.environ) #run the file
					except:

						sys.exit(1)
						
						

				
				else:
					if os.wait()[1] == 0: #check whether execle suceed or not
						os.close(write)
						result = os.fdopen(read,'r') # read the output in the child
						content = result.read() #read the content in result
						print(content)
						list_content = content.split("\n")			

						header = list_content[0].split(":")
						if "Content-Type" in header: #check whether has Content type line in first line
							list_content.remove(list_content[0]) #remove the content type line
							content = "".join(list_content)	
							p = name.split(" ")
							server_format = p[2] + " 200 OK"
							content.strip(",")
							client_socket.send(server_format.encode()) #send to the server
							client_socket.send("\n".encode())
							client_socket.send("Content-Type: text/html\n".encode())
							client_socket.send("\n".encode())
							client_socket.send(content.encode())	
							client_socket.send("\n".encode())

							client_socket.close() 
						
						elif "Status-Code" in header: #check whether has custom status line in first line
							list_content.remove(list_content[0]) #remove the custom status lines
							content = "".join(list_content)
							p = name.split(" ")
							server_format = p[2] + header[1]				
							content.strip(",")
							client_socket.send(server_format.encode())
							client_socket.send("\n".encode())
							client_socket.send("Content-Type: text/html\n".encode())
							client_socket.send("\n".encode())
							client_socket.send(content.encode())
							client_socket.send("\n".encode())

							client_socket.close() 
						



						else: 
							p = name.split(" ")
							server_format = p[2] + " 200 OK"
							client_socket.send(server_format.encode())
							client_socket.send("\n".encode())
							client_socket.send("Content-Type: text/html\n".encode())
							client_socket.send("\n".encode())
							client_socket.send(content.encode())
							client_socket.close() 
					else: #Here has error
						p = name.split(" ")
						error_filename = 'cgi_error.html'
						x = open(error_filename, 'r')
						error_content = x.read()
						x.close()
						cgierror_format = p[2] + " 500 Internal Server Error"
					
						client_socket.send(cgierror_format.encode())

						client_socket.send("\n".encode())
						
						client_socket.send("Content-Type: text/html\n".encode())

						client_socket.send("\n".encode())
						client_socket.send(error_content.encode())
						client_socket.close()



			elif file_ending == ".png": #check file type is png or not
				try:
					p = name.split(" ")
					path = p[1]
					
					filename = file_list[0] + path
					if path == "/":
						filename = file_list[0] + "/index.html"
					a = open(filename, 'rb') # read in binary as its a png file
					content = a.read()
					a.close()
					server_format = p[2] + " 200 OK"
					content_type = "image/png"
					content_send = "Content-Type: " + content_type + "\n"
					client_socket.send(server_format.encode())
					client_socket.send("\n".encode())
					client_socket.send(content_send.encode())
					client_socket.send("\n".encode())
					client_socket.send(content)
				

					client_socket.close()
					
				except FileNotFoundError: #if can not find the file
					p = name.split(" ")
					error_filename = 'error.html'
					x = open(error_filename, 'r')
					error_content = x.read()
					x.close()
					error_format = p[2] + " 404 File not found"
					
					client_socket.send(error_format.encode())
					client_socket.send("\n".encode())
					client_socket.send("Content-Type: text/html\n".encode())
					client_socket.send("\n".encode())
					client_socket.send(error_content.encode())
					sys.exit()
			
					client_socket.close()


			elif file_ending == ".jpg" or file_ending == ".jpeg": #check file type
				try:
					p = name.split(" ")
					path = p[1]
					
					filename = file_list[0] + path
					if path == "/":
						filename = file_list[0] + "/index.html"
					a = open(filename, 'rb')
					content = a.read()
					a.close()
					server_format = p[2] + " 200 OK"
					content_type = "image/jpeg"
					content_send = "Content-Type: " + content_type + "\n"
					client_socket.send(server_format.encode())
					client_socket.send("\n".encode())
					client_socket.send(content_send.encode())
					client_socket.send("\n".encode())
					client_socket.send(content)
				

					client_socket.close()
					
				except FileNotFoundError:
					p = name.split(" ")
					error_filename = 'error.html'
					x = open(error_filename, 'r')
					error_content = x.read()
					x.close()
					error_format = p[2] + " 404 File not found"
					
					client_socket.send(error_format.encode())
					client_socket.send("\n".encode())
					client_socket.send("Content-Type: text/html\n".encode())
					client_socket.send("\n".encode())
					client_socket.send(error_content.encode())
					sys.exit()
			
					client_socket.close()
			
						
			
			elif file_ending == ".ico":
				try:
					p = name.split(" ")
					path = p[1]
			
					filename = file_list[0] + path
					if path == "/":
						filename = file_list[0] + "/index.html"
					a = open(filename, 'rb')
					content = a.read()
					a.close()
					server_format = p[2] + " 200 OK"
					content_type = "image/png"
					content_send = "Content-Type: " + content_type + "\n"
					client_socket.send(server_format.encode())
					client_socket.send("\n".encode())
					client_socket.send(content_send.encode())
					client_socket.send("\n".encode())
					client_socket.send(content)
				

					client_socket.close()
					
				except FileNotFoundError:
					p = name.split(" ")
					error_filename = 'error.html'
					x = open(error_filename, 'r')
					error_content = x.read()
					x.close()
					error_format = p[2] + " 404 File not found"
					
					client_socket.send(error_format.encode())
					client_socket.send("\n".encode())
					client_socket.send("Content-Type: text/html\n".encode())
					client_socket.send("\n".encode())
					client_socket.send(error_content.encode())
					sys.exit()
			
					client_socket.close()



			else:
				try:
					p = name.split(" ")
					path = p[1]
					filename = file_list[0] + path		
					if path == "/":
						filename = file_list[0] + "/index.html"
					a = open(filename, 'r')
					content = a.read()
					a.close()
					server_format = p[2] + " 200 OK"

					# this will determine the content type
					if file_ending == ".txt":
						content_type = ending[0]
					elif file_ending == ".html":
						content_type = ending[1]
					elif file_ending == ".js":
						content_type = ending[2]
					elif file_ending == ".css":
						content_type = ending[3]
					elif file_ending == ".png":
						content_type = ending[4]
					elif file_ending == ".jpg":
						content_type = ending[5]
					elif file_ending == ".jpeg":
						content_type = ending[6]
					elif file_ending == ".xml":
						content_type = ending[7]
					elif file_ending == "":
						content_type = ending[1]
					else:
						content_type = "text/xml"


					content_send = "Content-Type: " + content_type + "\n"
					client_socket.send(server_format.encode())
					client_socket.send("\n".encode())
					client_socket.send(content_send.encode())
					client_socket.send("\n".encode())
					client_socket.send(content.encode())
				

					client_socket.close()
					
				except FileNotFoundError:
					p = name.split(" ")
					error_filename = 'error.html'
					x = open(error_filename, 'r')
					error_content = x.read()
					x.close()
					error_format = p[2] + " 404 File not found"
					
					client_socket.send(error_format.encode())
					client_socket.send("\n".encode())
					client_socket.send("Content-Type: text/html\n".encode())
					client_socket.send("\n".encode())
					client_socket.send(error_content.encode())
					sys.exit()
			
					client_socket.close()
		else:
			client_socket.close()



if __name__ == '__main__':
	main()



