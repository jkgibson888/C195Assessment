Title: Customer Appointment Generator
Author: Joshua Gibson
Contact: jgib194@wgu.edu
Application Version: 1.1 on 7/4/2022
IDE: IntelliJ IDEA Community Edition 2021.3.2
JDK: jdk-17.0.2
JavaFX: openjfx-17.0.2_windows-x64_bin-sdk
MySQL Connector driver version: mysql-connector-java-8.0.28

Program Operation

     Upon initiating the program the user is prompted with a login screen.
If the user inputs the administrator passwords (currently admin for both user and passwords)
the program moves the user to a screen that provides the ability to add new users.  The new
users name and associated password is placed in their corresponding text fields and then the 
add button is created, the new user is added to the database allowing them to interact with
the program.

    Every login attempt whether successful or not is recorded in a separate file.When a normal
user inputs their name and password the program then moves to a new screen that immediately
prompts the user if they have any appointments coming up shortly and the
details.  If no appointments are comming up shortly a warning acknowledging this is prompted
requiring the user to select the OK button to acknowledge that there are no upcoming appointments
before the user can continue.

After this the user is prompted with a few different options. This is the Main Menu  There are a total of four different
options on this prompt screen.  Two are images (obtained from an open source website) titled
1. Customer, 2. Appointments, and two buttons titled 3. Reports, and 4. Logout.

1. Customer

When the customer picture button is selected the user is brought to a screen showing a table of
customers currently being serviced as well as for fields of text and buttons.  If a customer from
the table is selected those fields are populated with the customers information.  Two options are 
then available to the user.  Firstly they can change any fields, be it one or all and then select
the modify button to change the information for the customer.  Secondly a customer can be deleted 
by selecting the customer in the table and then clicking the delete button.

If a customer is selected and the user wishes to remove that selection clicking the clear fields
button will do this.  If no customer from the current customer table is selected then all fields
are left blank.  This provides the user the opportunity to add information for a new customer that
is being serviced by the company.  Once all information is placed in the appropriate field, the
user can then press the add button to incorporate the new customer into the database.

If a customer is selected in the table then the only options are to modify and delete the user.
Also, adding a new user is only available if all fields have been cleared and no customer is
selected in the customer table.

Finally, there is a return button to return to the Main Menu.

2. Appointments

When this option is selected the user is brought to a screen displaying a table of appointments
and fields that pertain to appointments. The user has the option to filter the list between all
appointments, appointments this month, and appointments this week via radio buttons above the menu.

If an appointment is selected within the table the fields are populated with the information
from the saved appointment.  Information can then be changed in the fields and then saved to the
database by clicking the Modify button.  A selected appointment can also be deleted by clicking
the delete button.

If no appointment is selected then all fields are blank, allowing the user to input information
for a new appointment.  Once the new appointment information has been specified, clicking the add
button will create a new appointment in the database.

If an appointment has been selected but no changes are desired and instead a new appointment 
would like to be created the clear button clears the selection from the table and clears all 
fields.

3. Reports

Selecting Reports moves the user to a screen with generated reports in 3 categories. At the top,
the user can see a generated table of the number of appointments by type and month.

In the middle a user can click on the Select Contact button and then select a specific contact to
see how many appointments are associated with each contact.

At the bottom is a table that generates showing how many appointments each user in the system has
created.

4. Logout

Selecting this logs the user out of the system and returns them to the login screen.

Report

	The third report I created was a table showing the total number of appointments created by
each user that can be used as a metric for productivity.

Lambda

	First lambda can be found on line 154 of ReportsFormController. Originally, I had a for loop
to sort the list, but using a lambda expression as a comparator reduced the extra coding down to a
single line.

	Second lambda expression can be found in line 195 of CustomerFormController. It was placed there
to demonstrate how a programmer defined lambda expression can be created.


 