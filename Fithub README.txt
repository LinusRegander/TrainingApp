FitHub (Group 15) - README:
https://github.com/LinusRegander/TrainingApp.git

IMPORTANT Information:
Framework Structure:
- Our main package is called "common [training-common]". All frontend coding made by Group 15 is part of this package.
- Our database package is called "Database [JDBC] where you will find code related to backend.
- Every package/file not part of the main package above "Database [JBDC]" is not used by the group and can be IGNORED as they are part of the Codename One framework.

Running Emulator Guide/Tutorial:
1. Press the "Add/Edit Configurations" button between the hammer and play button in the top right-hand side of IntelliJ.
2. Press the + sign in the top left and select "Maven".
3. Give the configuration a name, this name does not matter.
4. Enter your file directory for the project in the "Working Directory:" area.
5. Enter "verify -e" in the "Command Line:" area.
6. Enter "idea-simulator" in the "Profiles:" area.
7. Press "Apply".
8. Press "Ok".

Emulator should now be runnable.
Before you start the emulator you should start the server, the server needs to be active in order for the emulator to run properly
Server can be found in the "RestAPI" module.

Main Package Structure:
- In "common [training-common]" you will find the src folder containing the main code for the application.
- In the src folder you will find 3 additional folder: css, guibuilder and java.
- The css folder contains the css file "theme.css" where the design compartment of the GUI can be found.
- The java folder contains the main code structured with the MVC model.
- When Run Simulator is executed it is the Training classes runApp() method that is executed. It is a class that was provided by the framework.

⣿⣿⡿⠃⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠻⣿⣿⣿
⣿⡏⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠘⢿⣿
⡇⠀⠀⠀⠀⣤⣤⣀⣀⣠⣤⣤⣷⣦⣤⣤⣤⣤⣀⣀⣀⣀⠀⠀⠀⠀⠀⠀⠀⢸⣿
⡇⠀⠀⠀⠈⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣷⣶⣶⣄⠀⠀⢸⣿
⣷⠀⠀⠈⠨⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡿⠀⠀⢸⣿
⣿⡄⠀⠀⣈⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣷⠀⠀⢸⣿
⣿⡇⠀⠀⢰⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠁⠀⠀⢸⣿
⡇⠀⠀⠀⠀⠉⠛⠿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠟⠿⡿⢀⡀⠀⣔⣿
⣇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠉⠉⠛⣿⣿⣿⠿⠛⠛⠛⠋⠈⠀⠀⠀⠀⠘⡇⢠⣾⣿
⣇⠇⠀⣠⣄⠀⠀⠀⠀⣀⣀⣀⡀⢸⣿⣿⣇⣀⣀⢠⣀⢀⠀⠀⣀⣠⣶⡇⣾⣿⣿
⣿⡄⠀⢸⣿⣶⣤⣀⣀⣠⣽⣿⠁⣼⣿⣿⣿⣿⣿⣼⣬⣭⣶⣶⣿⣿⣿⠀⣿⣿⣿
⣿⡇⠀⠀⢹⣿⣿⣿⣿⣿⣿⡿⠀⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠁⠀⣿⣿⣿
⣿⣷⡄⠀⠈⢿⣿⣿⣿⣿⣿⠁⢬⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠿⠀⢠⣿⣿⣿
⣿⣿⣇⠀⠀⠘⠿⣿⣿⣿⣿⠀⠈⠛⠿⠟⠙⠛⣻⣿⣿⣿⣿⣿⣯⠀⠀⣼⣿⣿⣿
⣿⣿⣿⡄⠀⠀⠀⠙⢿⣿⣿⣖⣄⣀⠀⢰⣿⣿⢿⣿⢿⣿⣿⡿⠀⠀⠸⢿⣿⣿⣿
⠛⠋⠉⠀⠀⠀⠀⠀⢀⣄⡀⠈⢈⠙⠛⢟⣋⢁⢁⣠⣤⣼⡿⠀⠀⠀⠀⠀⠀⠈⠙
⠀⠀⠀⠀⠀⠀⠀⠀⠹⠟⢀⢀⠚⠚⠛⠙⠛⢛⣿⣿⡟⠛⠁⠀⡀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠨⢿⣷⣿⣾⣿⣿⣿⣿⠇⠂⠀⠀⢠⡇⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⢐⠀⠀⠀⠀⠈⠛⠯⠿⡹⠛⠟⠉⠀⠀⠀⢀⣾⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⢆⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣾⡟⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠸⣦⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣾⢿⠁⠀⠀⠀
