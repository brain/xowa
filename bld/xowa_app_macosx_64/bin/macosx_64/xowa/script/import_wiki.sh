#!/bin/bash
#The XOWA_DIR variable allows calling the shell script from elsewhere on the file-system
#
#For example, if you're in /home/user/dir and xowa is in /home/user/xowa/
#user@machine:~/dir$ sh /home/lnxusr/xowa/xowa_macosx_64.sh
#>> runs xowa
#
#Without the XOWA_DIR variable convention, the shell script would fail.
#user@machine:~/dir$ sh /home/lnxusr/xowa/xowa_macosx_64.sh
#>> Error: Unable to access jarfile /xowa_macosx_64.jar
#
#If this script fails to run, then you can try removing the XOWA_DIR variable and hard-coding your directory
XOWA_DIR=$(dirname $0)
java -Xmx256m -jar $XOWA_DIR/xowa_macosx_64.jar --cmd_file xowa_build.gfs --app_mode cmd
