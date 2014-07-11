BotA Patch Server
=================

BotA Patch Server provides a easy-to-use patch-environment for games.

## How does it work?
The BotA (Battle of the Ancients, a game that is currently developed by me) Patch Server uses three main contexts for sharing data.

| Context  | Description |
| -------- |-----------------------------------------------------------------------------------------------|
| `/hash`  | Returns a plain text listing all files and their proper MD5-checksums seperated with a dollar-sign |
| `/files` | Represents the /files directory on the server |
| `/package`| Returns the zip file for complete new game installations |

## Using the release archives

*A step by step introduction for using the release jars.*

### Deploying a patch

1. Create a server root folder
2. Copy the JAR-files into the folder
3. Copy the ZIP-file with the game files into the folder
4. Rename the pasted ZIP-file for example to *release.zip*
4. Open a terminal (cmd, bash or something)
5. Enter `java -jar PatchDeployer.jar release.zip`
6. Close the terminal if everything was successful

When you want to use the server without the ZIP-file, you can just copy all your game files into the files folder, open a terminal and run the PatchDeployer.jar without any command line argument.

### Running the server

1. After the deploying the patch you have to open a new terminal
2. Type in `java -jar PatchServer.jar`
3. Now you should see some log messages
4. Open your webbrowser and enter the URL `localhost/hash`
5. When everything works fine, you should see a list of files and their attached hashes (only when the release.zip isn't empty)
6. You can customize the server by editing the config.properties file

You successfully set up the BotA Patch Server!
