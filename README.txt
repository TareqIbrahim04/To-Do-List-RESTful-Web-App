# A To-Do List Project

Used:
	SpringBoot (java version <17>)
	MongoDB
	PostMan

	
Based on concept of REST Api

Uses JWT Authentication and Authorization for security 


How To Use: 
	Clone Project on your Computer
	Run IntelliJ & MongoDB & PostMan
	
	First, The App make an initial User with 
	Username: “tareq@gmail.com”
	Password: “12345”
	
Note: u can make your own user with unique user name, password and name, by using a post request
“https://localhost:8081/api/v1/auth/login”

After logging in, u can create a to-do with its title & description, by using a post request
“https://localhost:8081/api/v1/todos” (don’t forget to pass the token as a Bearer Authentication with the request)

You can access your to-do’s by using a get request 
“https://localhost:8081/api/v1/todos” (don’t forget to pass the token as a Bearer Authentication with the request)

You can delete a specific to-do by using a delete request 
“https://localhost:8081/api/v1/todos/<id>”
(don’t forget to pass the token as a Bearer Authentication with the request)

	
