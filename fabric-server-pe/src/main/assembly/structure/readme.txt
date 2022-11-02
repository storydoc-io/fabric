requirements

    this application needs a java JRE, version 11 or higher

jdbc drivers

    by default no JDBC drivers are included

    add a JDBC driver jar in the "lib" directory (subdir of the installation directory)  and restart

application settings:

    to change the settings open config/application.yaml

        server port  (default: 8080)

wokspace

    (meta)data is by default kept in the "workspace" directory

    this directory can be changed in run.sh or run.bat

running the application:

    On windows:

        run.bat

    On linux

        chmod u+x run.sh
        ./run.sh

