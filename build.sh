#!/bin/bash

# Function to display usage
usage() {
    echo "Usage: $0 [java-8|java-21]"
    exit 1
}

# Check for profile argument
if [ "$#" -ne 1 ]; then
    usage
fi

PROFILE=$1

if [[ "$PROFILE" != "java-8" && "$PROFILE" != "java-21" ]]; then
    usage
fi

# Clean and build the project with the selected profile
mvn clean install -P$PROFILE

if [ "$PROFILE" == "java-21" ]; then
    # Run jpackage only for Java 21
    jpackage \
        --input "target/" \
        --name "MPC" \
        --main-jar "xruisu-mpc-0.9.0-BETA-21.0.5-shaded.jar" \
        --main-class "xruisu.project.mpc.app.Run" \
        --type exe \
        --dest "mpc-installer" \
        --icon "target/classes/assets/images/MPC/mpc-icon.ico" \
        --app-version "0.9.0" \
        --win-shortcut \
        --win-menu \
        --description "Data Solutions Made Easier" \
        --vendor "Louis Harris, xRuisu"

    echo "Build and packaging complete. Executable is in the mpc-installer directory."
else
    echo "Build complete. No packaging done for Java 8."
fi