# NOTE: SWT_GTK3=0 is needed else XULRunner v24 and up will fail; See https://bugs.eclipse.org/bugs/show_bug.cgi?id=423870
export SWT_GTK3=0 && java -Xmx256m -jar xowa_linux_64.jar --app_mode http_server
