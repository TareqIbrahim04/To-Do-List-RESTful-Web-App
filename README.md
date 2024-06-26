# A To-Do List Project

## 💻 Technologies_Used
- **SpringBoot** (java version <17>)
- **IntelliJ IDEA**
- **MongoDB**
- **PostMan**

## ✨ Features
- Based on concept of REST Api
- Uses JWT Authentication and Authorization for security
- Tested with Unit Testing (Mockito & JUnit5)


## How To Use
- Clone Project on your Computer
- Run IntelliJ & MongoDB & PostMan
	
- First, The App make an initial User with 
    Username: “tareq@gmail.com”
    Password: “12345”

  **Note:** u can make your own user with unique username, password and name, by using a post request
“https://localhost:8081/api/v1/auth/signup” and then login with a post request "https://localhost:8081/api/v1/auth/login" (copy the token)

- After logging in, u can create a to-do with its title & description, by using a post request
“https://localhost:8081/api/v1/todos” and pass the to-do attributes (title, description) as a JSON body in postman (don’t forget to pass the token as a Bearer Authentication with the request)

- You can access your to-do’s by using a get request 
“https://localhost:8081/api/v1/todos” (don’t forget to pass the token as a Bearer Authentication with the request)

- You can access a specific to-do by using a get request 
“https://localhost:8081/api/v1/todos/id” (don’t forget to pass the token as a Bearer Authentication with the request)

- You can update a specific to-do by using a put request 
“https://localhost:8081/api/v1/todos/id” and pass the attributes u want to change as a JSON body in postman (don’t forget to pass the token as a Bearer Authentication with the request)

- You can delete a specific to-do by using a delete request 
“https://localhost:8081/api/v1/todos/id”
(don’t forget to pass the token as a Bearer Authentication with the request)

## 📞 Contact_Us
- ### If you have any questions or require further details, please feel free to contact me: 
- ### Gmail: tareq.ibra.04@gmail.com
