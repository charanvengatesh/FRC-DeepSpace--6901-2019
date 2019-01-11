# FRC DeepSpace Team 6901 2019
Repository for FRC team 6901 during the 2019 Season

## Configuring Materials
* To get the visual studio IDE to run/write programs:
  * https://wpilib.screenstepslive.com/s/currentCS/m/java/l/1027503-installing-c-and-java-development-tools-for-frc
* To download the National instruments update suite **REQUIRED IN ORDER TO RUN PROGRAMS**:
  * https://wpilib.screenstepslive.com/s/currentCS/m/java/l/1027504-installing-the-frc-update-suite-all-languages 
* To build and deploy robot code for testing:
  * https://wpilib.screenstepslive.com/s/currentCS/m/java/l/1027063-building-and-deploying-to-a-roborio

## When running the program...
1. Connect the ethernet wire to robot
2. Deploy robot code
3. __**MAKE SURE TO SELECT TEST MODE **__
4. When you are ready to run, select the enabled button

## Some basic git commands:

* Setting your Git username for all repositories (remove --global tag to set it for a single repository):
```
git config --global user.name "username"
```
* Setting your Git email for all repositories:
```
git config --global user.email "email@example.com"
```
* Go to a directory:
```
cd file
```
* Initialize local directory:
```
git init
```
* Add the files in your new repository (This stages them for first commit):
```
git add .
```
* Commit the files that you added **MAKE SURE YOUR FILES ARE FULLY FUNCTIONAL**:
```
git commit -m "Message about what changes you made"
```
* Connect to the remote repository URL:
```
git remote add origin remote repository URL
```
* Pull changes from the remote repository **MAKE SURE TO DO THIS BEFORE PUSHING TO AVOID ERRORS**:
```
git pull
```
* Push the changes to the remote repository: 
```
git push origin master
```
* Make new branch 
```
git branch branchname
```
* Move to new branch 
```
git checkout branchname
```

## TODO: 
* Make the README more professional after everyone learns how to use the programs.
* Learn how to setup Vision and OpenCV.
* Learn how to integrate sensors.
* Learn how to use the PID controller system.

## Resources:

* https://wpilib.screenstepslive.com/s/currentCS/m/java (provides setup information and some documentation)
* http://first.wpi.edu/FRC/roborio/release/docs/java/ (official documentation)
* https://github.com/Knights-Robotics/FRC-Programming (Last year's github)
* https://frc-pdr.readthedocs.io/en/latest/ (more documentation)
* https://www.chiefdelphi.com/c/first (forum)
* https://opencv.org/ (Open CV documentation)
