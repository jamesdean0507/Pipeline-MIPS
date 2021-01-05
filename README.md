# CS3339-Final-Project
MIPS Pipeline Simulator
Supports Instructions: (ADDI is not implemented, instead I implemented NOR)
J, BEQ,
ADD, SUB
SW, LW
SLL, SRL
MUL,
AND, OR, NOR
NOP

To run the program open the project on Visual Studio Code, Net Beans, or intellij. 
This will require a Java Development Kit which can be downloaded on the Oracle website,
any varsion since JDK 6 will suffice. 

Once your atmosphere is set up opwn the Driver.java file in the src folder and above the main()
function press run. This will trigger the GUI display. From there press the Choose button
at the top of the window and select the folder labeled example, then silect your input file (.txt)
If you would like to test your own input file, copy and paste the .txt file into the Example folder 
and select it after pressing the choose button when running the program. After the input file 
has been selected you will be brought back to the original user interface while no changes 
have been made. Press the button next to Choose, labeled "Load". This will update the table,
displaying the instructions from the input file. To run the entire program and then check results,
press the "Run" button followed by the "Step" button. However if you would like to iterate 
through the program line by line, press "Step" without pushing "Run" first and for each time 
you press step it will move to the next instruction. The natural display is in decimal however,
if you press the check box labeled "Hex" it will change the orientation of the values from 
decimal to Hexadecimal at any point while running the program. To start from the beginning of 
the instructions with a fresh program counter and cleared memory, press the "Reset" button 
at any point in time. To exit the program please push "Stop", this will not close the window,
but it will cease opperations at which point you can 'X' out at the top left of the window.
