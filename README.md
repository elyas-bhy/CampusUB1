# CampusUB1

CampusUB1 is an Android application designed to facilitate access to relevant information for students, professors and researchers of the University of Bordeaux 1 campus.


### Setting up your development environment.
If you are planning to work on the CampusUB1 application, follow the steps below:

1. Download and install the [Android ADT Bundle](http://developer.android.com/sdk/index.html) for your operating system, or set up the [Android SDK Eclipse plugin](http://developer.android.com/sdk/index.html#ExistingIDE) in  your existing Eclipse.
2. Using the Android SDK Manager, install Android 4.0 (API 14), and make sure to include Google APIs as well as Google Play Services. 
3. Fork the *CampusUB1* repo at https://github.com/elyas-bhy/CampusUB1. For detailed instructions visit [http://help.github.com/fork-a-repo/](http://help.github.com/fork-a-repo/)
4. Check out the **develop** branch.
5. Import the *CampusUB1* source (CampusUB1/source folder) into a new Android project in Eclipse. Use the Google API level 14 (Android 4.0) as the target of your project.
6. In Eclipse/ADT, right-click on your project -> *Build Path* -> *Configure Build Path* -> in the Libraries tab, select *Add External JARs*, and select the LDAP SDK jar located in the libs/ folder.
7. In Eclipse/ADT, click on *Window* -> *Android SDK manager* -> search for the *Google Play Services* package in the extras category and click on install package.
8. You now need to import the library projects. In Eclipse/ADT, click on *File* -> *Import* -> *Existing Android Code into workspace* -> browse to the **google-play-services_lib** folder located in the libs/ folder and click on finish.
9. Repeat step 7 with **slidingmenu_lib**.
10. In Eclipse/ADT,in the Package Explorer, right click on the CampusUB1 project -> *Properties* -> On the *Android* tab go to the *Library* section and click on *Add* and then select both projects you imported in the previous steps and click on *OK* and *Apply*.
11. Follow the instructions indicated in the [Getting a Google Maps API debug key](https://github.com/elyas-bhy/CampusUB1/wiki/Getting-a-Google-Maps-API-debug-key) wiki page to properly setup Google Maps.
12. You should now be able to compile the project.


Before you start coding, please study [this branching model] (http://nvie.com/posts/a-successful-git-branching-model/) **carefully**. It will save everyone in the team a lot of time and effort incorporating your changes if your contributions follow that model. In particular:
 * **ALWAYS pull from the develop branch, do not work out of the release branch**
 * **ALWAYS create a new branch when you start working on a new feature**
 * **ALWAYS make your pull requests against the develop branch**


### Setting up the test project.
A test project is included in this project, and can be found under the source/tests directory.
To set up this project, follow the steps below:

1. In Eclipse/ADT, import Android project from existing code.
2. Make sure the main project is referenced in the test project's build path, and that it is the only dependency (as the other libraries are already referenced in the main project).
3. In the test project, go to *Properties* -> *Java Build Path* -> *Libraries* and add **robotium-solo-4.0.jar** as an external JAR.
4. You should now be able to compile the project.
