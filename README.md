BotA-Patch-Server
=================

BotA Patch Server provides a easy-to-use patch-environment for games.

## How does it work?
The BotA (Battle of the Ancients, a game that is currently developed by me) Patch Server uses three main contexts for sharing data.

| Context  | Description |
| -------- |-----------------------------------------------------------------------------------------------|
| `/hash`  | Returns a plain text listing all files and their proper MD5-checksums seperated with a dollar-sign |
| `/files` | Represents the /files directory on the server |
| `/package`| Returns the zip file for complete new game installations |
