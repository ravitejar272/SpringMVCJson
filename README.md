# SpringMVCJson
Using Spring MVC and JSON, do word count in given paragraphs and report it in curl command

Spring MVC Json Example
===============================
This Spring MVC JSON application is for solving the Java programming task assigned to me  .
We are given a text file and the aim is to count the words and report the count in certain curl commands.


Technologies used :
====================
 
Spring 3.2.2.RELEASE
Jackson 1.9.13
Maven 3
JDK 1.6
Tomcat  7.0.65 
Eclipse  
Spring Security 3.2.0
Given input data(given as part of the programming task) contained in text.txt

 
2. To build the project

   cd workspace/Inteliment
   mvn clean
   mvn package

   This produces a war file named Inteliment.war in Inteliment/target directory

3. To run the application
   1.Copy Inteliment.war from Inteliment/target to webapps directory of Tomcat. (That is, apache-tomcat-7.0.65/webapps   in my case)
   2.Startup Tomcat by invoking sh startup.sh command in the Tomcat bin directory
   3.Go to the Tomcat manager by http://localhost:8080 and ensure that this application with name=Inteliment has been deployed successfully, that is it shows up in the list and has been started up.    
   4. Invoke curl command from the command prompt

 
4. Test Results

$ curl http://localhost:8080/Inteliment/counter-api/top/25 -H"Authorization: Basic b3B0dXM6Y2FuZGlkYXRlcw==" -H"Accept: text/csv"
17|vel
17|eget
16|sed
15|in
14|et
13|eu
13|ut
12|sit
12|nulla
12|metus
12|id
12|amet
12|ac
11|ipsum
11|duis
11|at
11|vitae
11|nec
10|nunc
10|non
10|dolor
10|aliquam
9|leo
9|consectetur
9|a
 



curl http://localhost:8080/Inteliment/counter-api/search -H"Authorization: Basic b3B0dXM6Y2FuZGlkYXRlcw==" -d'{"searchText":["Duis","Sed","Augue","Donec","Pellentesque","123"]}'   -H"Content-Type: application/json" -X POST
{"counts":[{"duis":11},{"sed":16},{"augue":7},{"donec":8},{"pellentesque":6},{"123":0}]}


Assumption : the count of words is case insensitive, ie., it counts Duis and duis occurrences each occurring in the file as 2 counts.



5. Assumptions
  1. If the file contents are modified, then the new file called text.txt needs to be deposited into the location src/main/resources (where it currently exists) and then the server restart is needed to produce correct output
  2. The file's words cannot contain special characters such as apostrophe etc since the split regex currently does not cater for special chars. In other words, should the file need to contain such special characters, the program needs to be modified to cater for that.
  3.In /top url, if the number of top items is alphanumeric, the default of 1 is used, ie., only the topmost frequency word is displayed.

 
