# Chat android client

The chat client consists both web and android options that can communicate with each other thanks to the server.
The client android app consists 6 pages: login,register,contacts,chats,settings,AddNewContacts
The client side built using java with Room as a local db and firebase for cloud messaging.      

Add contact with server: 10.0.2.2:7038
## You are currently in the repository of the android client.


## How to use the app  

When you first open the app, you will see the login page  
At first the database will be empty of users.  
In order to add a user go over to the signup page and sign up.  
After signing up you will be directed directly to the chat page.  
In order to start a conversion you will need to sign up another user.  
Then you will be able to add each other and enjoy real time chat.  
However, if you choose to add users manualy to the entity framework database please notice the following instructions:  
1. In the User Table the id property must be the same as the username property (done automatically when signing up)  
2. In the Contact Table the id propery will function as the contact's Username  
3. In the Contact Table The UserId is required and will function as the UserName of the user which the contact belong to.  
  
At any time you can head over to the rank system with the link in the bottom of the screen,  
and then head back to the chat app by clicking on "Go to chat" in the menu on the top left corner.  
