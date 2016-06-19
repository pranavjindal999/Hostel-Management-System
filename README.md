# Hostel-Management-System
Hostel Management System made for our Punjabi University hostel. Specific to requirements.

A desktop application aimed to manage hostel and all the related clerical work. The application was made using Java SE, Java FX and MySQL for Database. This was a personal project made during summer training tenure.



Objective
Hostel Management System is an application software which aims at the computerization of hostel management letting the complete process of allotment and its management be dependent on computer.
As the working of any hostel is almost same, I’ve chosen our very own “Banda Singh Bahadur Boys Hostel” to present the software and its design. 
Without computers, everything goes on registers causing a lot of paper work with very less efficiency, which is where this software can dramatically improve the overall management.
My purpose in developing this application was to provide a very simple interface to the requirement that will be easy to navigate and operate, proper record keeping and reporting, and an application that can be implemented on desktop or client/server architecture i.e. providing with the following features:
* Highly user-friendly
* Cross-platform
* Easy-to-use
* Tested system to track unnoticed error
* Data entry restricted to valid domains (to avoid errors)



User Modules
As described in previous section, there are mainly two types of users, the first one is the head clerk, and the other are the mess accountants.

Hostel Accountant
This user account is meant for the head clerk who maintains the complete database of students, maintains the room allotments and the payment of bills.
The privileges allowed to the head clerk/hostel accountant are:
* Allot a new room
* Empty an allotted room
* Swap rooms between students
* Shift student to empty room
* Make an adjustment
* View or Update Details of student
* Receive payments of Mess Bill
* Change Password
* View Activity Log
* Search through complete database

Mess Accountants
There are mess accountants for each block of the hostel. These users are meant for the mess accountants who keep the record of mess charges for each room number.
The activities the mess accountant can do:
* Search through complete database
* Add amount to account
* Subtract amount from account
* Change Password
* View Activity Log

These users have been explained in detail further in the report, about their privileges activities and over all functions. 
Privileges of Hostel Accountant

Allotment of New Room
Clerk can allot a new seat to a student using this action button clicking on which opens up the form to allot new room.
The form expects user to select the block and room number to allot through a combo box which shows the list of empty rooms only.
The room can be allotted only when the valid details are filled. For example:
•	Mobile number needs to be a 10 digit number.
•	Date should be a valid one.
•	Roll No., Vehicle No. and Fee receipt number should be unique.

Emptying of Rooms
This allows admin to empty some particular seat, or the entire block or the entire hostel.
The form expects user to select radio button of the action to be performed.
Further selecting the Block or Room No. as per the radio button selected.
The room can be emptied only when there is no mess bill pending for the room.

Swapping of Rooms
This allows admin to swap seats between students letting the complex process of information interchange be done by computer itself.
The form expects user to select the two students and simply press the swap button to do rest of the job.
The pending mess bill also goes along with the students.

Shifting to Empty Room
This allows admin to shift a student to an empty seat in the hostel.
The form expects user to select the student to shift and the available empty room to which student is to be shifted and pressing the shift button shifts the student to the room.
The pending mess bill also goes along with the student.

Making an adjustment
This allows admin to make some adjustment in some room. Only one student can be adjusted in one single room.
The form expects user to select the room into which the adjustment is to be made, and the seat allotted to becomes the room number suffixed with letter “E”.
The rest of the adjustment process is same as the allotment.

Viewing and Updating Student Details
This allows admin to update details of student.
The form expects user to select the student from the left table which shows the students according to search box.
As soon as user selects the student, the pane slides to right and clicking on update button will allow updating details.

Receiving the Mess Bill Payment
This allows admin to receive payments of mess bill of any student.
The student can be searched and click to open details page which has an option to pay pending bill clicking on which will open up the dialog box for the same.
Simply enter the amount, change will be adjusted next time.

Searching Through Database
The application is provided with an all in one Search Engine for the complete database of hostel which lets you search students as you type.
You can search by Name of student, Roll Number, Room Number, City, State, and Vehicle Number. The results will be searched out as you type.

Two Keywords lets you shortlist students:
“Adjust” shows the students who are adjusted.
“Pending” shows the students who have some bill pending.

Changing Password
This dialog box lets you change the password of the user.
The form expects user to fill up the old password, and the new one twice.
Few conditions have been applied for the new password to be accepted:
•	Maximum Password Length is 20.
•	Minimum Password Length is 5.
•	Password is Case-Sensitive.

Privileges of Mess Accountant
Adding and Subtracting Amount
The mess accountant can simply add amount in the add column to any room, and similarly subtract some amount.
The form expects user to select the student and press enter in subsequent column which changes the cell to textbox and hence letting the user to add/subtract amount.

Searching
The mess accountant can search through the database using the search box at the top of the form. The search can only be done by room number.

Changing Password
Same as previous.

Screenshots:


![](https://raw.githubusercontent.com/pranavjindal999/Hostel-Management-System/master/screenshots/adjustment.png)
![](https://raw.githubusercontent.com/pranavjindal999/Hostel-Management-System/master/screenshots/details%20view.png)
![](https://raw.githubusercontent.com/pranavjindal999/Hostel-Management-System/master/screenshots/edit%20view.png)
![](https://raw.githubusercontent.com/pranavjindal999/Hostel-Management-System/master/screenshots/empty%20room.png)
![](https://raw.githubusercontent.com/pranavjindal999/Hostel-Management-System/master/screenshots/main.png)
![](https://raw.githubusercontent.com/pranavjindal999/Hostel-Management-System/master/screenshots/mess.png)
![](https://raw.githubusercontent.com/pranavjindal999/Hostel-Management-System/master/screenshots/new%20allotement.png)
![](![](https://raw.githubusercontent.com/pranavjindal999/Hostel-Management-System/master/screenshots/shift%20to%20empty%20room.png))
![](https://raw.githubusercontent.com/pranavjindal999/Hostel-Management-System/master/screenshots/swap.png)
![](https://raw.githubusercontent.com/pranavjindal999/Hostel-Management-System/master/screenshots/activity%20log.png)
![](https://raw.githubusercontent.com/pranavjindal999/Hostel-Management-System/master/screenshots/bills.png)
![](https://raw.githubusercontent.com/pranavjindal999/Hostel-Management-System/master/screenshots/password.png)
