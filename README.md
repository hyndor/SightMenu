# SightMenu
That menu api allows you to easily implement your own custom menus in human-readable way.


**Examples:**
  *You can check separated module [**example**](https://github.com/hyndor/SightMenu/tree/master/example/src/main/java/ru/hyndo/signmenu/example) to get basiс understanding of api.*
#

**Features:**
  * Builder pattern everywhere for creating a menu
  * Api uses java's functional interfaces for listening button clicks
  * Easy pagination
  * Menu Templates
  * Null safety with optional and javax annotation @Nullable/@Nonnull
  * And... Some more features that I don't remember
 
**Building:**
  1. Download [Apache Maven](https://maven.apache.org/download.cgi)
  2. Unpack it
  3. Optionally add the bin folder to your PATH variable to invoke Maven with mvn without specifying the complete path to the bin folder for every command
  4. Download this project with Git (git clone <URL/git@github:...>) or as zip
  5. Move to the top folder of the project conting the pom.xml
  6. Run ```mvn clean install```
  7. The final version is inside the menu-api/target folder
  8. Shade it in your plugin
  
  
**Dependencies:**
  * Java 8
  * Bukkit api 1.12.2 (It may work on lower version but I haven't tested)
