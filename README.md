# LabTest2Covid19
MADS4006 Advance Android Devt - Lab test 2

PROBLEM DESCRIPTION

You have been hired by the Ministry of Health to create a COVID-19 Tracking app that tracks the number of COVID cases through the various Candian provinces and territories. The app has two actors :
A regular user - This user is allowed to view all COVID-19 information per province.
An administrative clerk- This user is allowed to add and edit COVID-19 case information per province.

Please note, all database functionality MUST be done with Firebase Firestore.

A list of the features you must implement include:
Creating and setting up a Firestore database schema
Implementing user account registration and login
Allowing the administrative clerk (“the clerk”) to add, view, and edit COVID-19 case data
Allowing a regular user to view a summary of COVID-10 cases by province.



Feature 1 (Database Collection and Document configuration):		( 3 marks)

You must  login into your Firebase Firestore console and manually create a users’ collection and two default user documents. See below (Figure 1.1 - Figure 1.4)

Figure 1.1   - Collection and Document Dashboard


Figure 1.2

After clicking “start collection”, the below page will be presented. Name the collection users and then proceed to create two user documents as shown in Figure 1.3

Figure 1.3

You are to create two documents. One document to represent a regular user and the other to represent a clerk. This will be determined programmatically by the type key. A regular user will have the value reg, whereas the a clerk user would have the value clerk stored in the type key.



Figure 1.4







Feature 2 (Authentication and Registration):        			( 10 marks)

After Feature 1 has been completed, you are required to implement your Authentication and Registration functionality as shown in Figure 1.4 and Figure 1.5 


Figure 1.4


To login, your app has to search the users collection and check if a document with the entered  password and username pair exists in the users collection.  Once verified, the app will determine if to navigate the user to the Clerk dashboard or to Regular user dashboard. This will be solely based on the type key as mentioned in the above.

Figure 1.5


Ensure all fields are validated and not empty. A document should be inserted into the users’ collection once the user passes the validation. This is done by entering all required data and then hitting the registration button. Upon a successful registration the user should be navigated to the login screen for he/she to log in.  Note, the registration form can only register a regular user.

Please note, you are not to use the Firebase built in Authentication feature.



Feature 3 (Clerk Functionality):        					( 15 marks)

When a clerk successfully logs in, they will be presented with a screen as shown in Figure 1.6

Figure 1.6


The clerk will have the ability to add a new case for a particular Canadian Province and/or view all the entered cases which they can then later update.

When a clerk clicks the Add Case button they will be navigated to the Add case Screen, whereas, if they click on the View Cases button they will be navigated to the view cases screen. 

Figure 1.7 (Adding a case)



When the clerk is navigated to the Add Case screen (Figure 1.7), they must fill out all the information on the form and then hit the Add Case button. Note, the Provinces field is actually a spinner, which provides a list of all the Candian provinces and territories (Google what they are).

Once the user enters all the information, a Case document will be added to a Case collection.

Figure 1.8 (Viewing a case)



When the user comes to the View Case screen, the screen will pull all the Cases in the database and then display them by Province in a list view (Figure 1.8). 

If the user clicks on a particular case they will be navigated to the Edit Case Screen to update the information for the clicked case.

Figure 1.9 (Editing  a case)



The app must populate all fields with the pertinent data based on the Case that was clicked in the List View.

After the user update the figures and clicks  on the Update button, they will be navigated back to the list view 



Feature 4 (Regular User functionality):        				( 7 marks)

Figure 2.0


When a regular user logs in, they will be presented with the Case List Screen (Figure 2.0). By default, the app should list all the cases with all their pertinent information that is stored in the database in a list view, as shown in the above. However, the user has the ability to select which province information that they only want to view. They can do so by selecting the province in the spinner above.




END OF ASSESSMENT

