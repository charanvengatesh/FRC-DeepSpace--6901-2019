# FRC DeepSpace Team 6901 2019
Repository for FRC team 6901 during the 2019 Deep Space Season.
**NOTE:** please *DON'T* change any code unless you have gotten the robot to successfully run the test code.
## Table of contents:
1. [Configuring Materials](https://github.com/Knights-Robotics/FRC-DeepSpace--6901-2019/blob/master/README.md#configuring-materials)
2. [Opening the Program](https://github.com/Knights-Robotics/FRC-DeepSpace--6901-2019/blob/master/README.md#opening-the-program) 
3. [Running the program](https://github.com/Knights-Robotics/FRC-DeepSpace--6901-2019/blob/master/README.md#when-running-the-program)
4. [Git commands](https://github.com/Knights-Robotics/FRC-DeepSpace--6901-2019/blob/master/README.md#some-basic-git-commands)
5. [To-do list](https://github.com/Knights-Robotics/FRC-DeepSpace--6901-2019/blob/master/README.md#todo)
6. [Resources](https://github.com/Knights-Robotics/FRC-DeepSpace--6901-2019/blob/master/README.md#resources)

## Configuring Materials
* [Get the visual studio IDE to write and run robot programs](https://wpilib.screenstepslive.com/s/currentCS/m/java/l/1027503-installing-c-and-java-development-tools-for-frc)
* [Download the National instruments update suite(**REQUIRED IN ORDER TO RUN PROGRAMS**)](https://wpilib.screenstepslive.com/s/currentCS/m/java/l/1027504-installing-the-frc-update-suite-all-languages) 
* [Building and deploying robot code for testing](https://wpilib.screenstepslive.com/s/currentCS/m/java/l/1027063-building-and-deploying-to-a-roborio)
* [Using git to change the remote repository](https://git-scm.com/downloads)
## Opening the program
1. Clone/download this repository
2. Extract the zip file (**REMEMBER IT'S LOCATION**)
2. Open the *FRC 2019 Visual Studio*
3. Go to:
```
File (Top left corner) > Open Folder   
```
4. When the file explorer menu pops up, *double click* on the folder that you extracted the Zip file's contents to.
  * *CheckPoint:* You should now see a single folder.
5. **CLICK ONCE** on the folder (*should have the same name as this repository*) and press select folder.
6. Visual Studio should now be able to display the robot code.
## When running the program...
1. Connect the ethernet wire to robot
2. See if the driver station lights are green for everything except the joystick (unless you have one hooked up).
2. Deploy robot code
3. **MAKE SURE TO SELECT TEST MODE**
4. When you are ready to run, select the enabled button

## Some basic git commands

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
* Make a new branch 
```
git branch branchname
```
* Move to the new branch 
```
git checkout branchname
```

## TODO
* Make the README more professional after everyone learns how to use the programs.
* Learn how to set up Vision and OpenCV.
* Learn how to integrate sensors.
* Learn how to use the PID controller system.

## Resources

* [Information and strategies for programming](https://wpilib.screenstepslive.com/s/currentCS/m/java)
* [Official WPILIB documentation](http://first.wpi.edu/FRC/roborio/release/docs/java/)
* [Last Year's Github](https://github.com/Knights-Robotics/FRC-Programming)
* [More documentation/tips (may have less content)](https://frc-pdr.readthedocs.io/en/latest/)
* [FRC Forum](https://www.chiefdelphi.com/c/first)
* [Open CV Documentation (probably won't need it)](https://opencv.org/)
* [GIT documentation](https://git-scm.com/doc)
* [GIT tutorial](https://www.atlassian.com/git/tutorials/syncing)
* **TEXT ONLY PLEASE:** 469-678-0333
* **EMAIL:** mateus.sakata@gmail.com 
