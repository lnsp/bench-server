Bench [![Build Status](https://travis-ci.org/Mooxmirror/Bench.svg)](https://travis-ci.org/Mooxmirror/Bench)
=================

Bench provides a easy-to-use patch-environment for games and apps.

## How does it work?
Bench uses three main contexts for sharing data.

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

## Building the project

1. Clone the repository using `git clone https://github.com/mooxmirror/Bench.git`
2. Run `maven package`
3. If successful, you get a file named `bench-x.x.x-jar-with-dependencies.jar`
4. Copy this file to the folder, where you want to run the server
5. To use the patch deployer, enter `java -cp bench-x.x.x-jar-with-dependencies.jar io.mooxmirror.bench.deployer.PatchDeployer [archive file]`
6. To use the patch server, enter `java -cp bench-x.x.x-jar-with-dependencies.jar io.mooxmirror.bench.system.HttpPatchServer`
7. Continue with the steps you find above

### Example configuration file

Here is a example configuration file (config.properties) with fully annotated settings:

```
# Bench configuration file
# Enable /hash context
hash.context=true
# Enabled /package context
package.context=true
# Enable /files context
files.context=true
# Port for the listener
server.port=80
# Cache lifetime in milliseconds (600000ms = 600s = 10m)
cache.lifetime=600000
```
You successfully set up a new Bench server!
