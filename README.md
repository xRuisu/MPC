
<p align="center"><i>The development of MPC was inspired by FedEx Express, providing the ability to generate more accurate scan rates and detailed information for individuals, teams, or anyone interested.</i></p>

## MPC | Getting Started

### Requirements

#### Windows/Mac/Linux: for Java 21 (21.0.5)

- `Windows`: 10 or newer
- `Mac`: Latest or -1 version
- `Linux`: Linux distributions supporting `GLIBC 2.17` or later
- `Java`: 9 or later
- _`Java 21` supports backward compatibility to `Java 8`_

#### Windows/Mac/Linux: Java SE (JRE) 8 or higher for Java 8 (1.8.0_432)

- `OS Compatibility`: Java 8 runs on various operating systems including Windows (XP and later), macOS, Linux, and Solaris.
- `System Requirements`: Minimal hardware required; a few hundred MBs of disk space and at least 128 MB of RAM, compatible with older hardware.

#### MPC File/Permissions

- File Type: `.jar`, `.exe`
- Permissions: `read`, `write`

```
MPC is just an advanced calculator, it doesn't require any special permissions such as sending/receiving or connecting to the internet.
```

**Why MPC requires Read/Write:**

- `Write`: MPC generates reports by creating custom `.mpc` files.
- `Read`: MPC reads the newly generated `.mpc` files.

#### UAC Permissions

```
Due to being an independent developer (Unknown Publisher), the program may not be installed due to UAC (User Account Control).
However, this can be bypassed with administrative rights. If you're concerned about downloading/installing MPC
due to its uncertified status, consider testing the application in an enclosed environment where possible
malicious applications can be safely evaluated, or simply reject and deny the download/installation.
```

**Note:**
_Depending on system permissions, you may still try MPC by:_

- Running (double-clicking) the `launch` file to run it through the JVM, or
- Using Command Prompt: `java -jar filename.jar`

## Installation

Download the latest version from the releases section on GitHub.  
Simply run the installer and follow the instructions to install MPC on your system.

- MPC Installer (*Recommended*) or .jar
- Install the Application: Double-click `MPC-x.x.x Installer.exe`  
- Note: The `.exe` does <ins>not require</ins> Java to be installed.

#### Java 8 (.jar)

- Method 1: Double-click the `.jar` file.
- Method 2: Double-click the `launch` file.
- Method 3: Command prompt enter `java -jar filename.jar`

### Releases

- https://github.com/xRuisu/MPC/releases

## Troubleshooting

#### When Data Doesn't Populate:

- Data must be entered for an `employee` and their `scans` and cannot be blank or zero.
- The same condition applies to the `Total Contribution` variable.

#### Crashes and Performance Issues:

- Review the `log` generated in the `MPC folder` for any errors that may help.
- If you find any issues or notable bugs, please `create an issue` so that it may be reviewed.

#### MPC Folder

Reports generated using MPC can be found on your `Desktop`, located in the `MPC` folder.

## Versions  

For compatibility, the versions can be found as part of the generated `.jar` files.  
MPC for `Java 8`: version `xruisu-mpc-0.9.0-BETA-1.8.0_432-shaded.jar` is compatible with `Java 8+`.  
MPC for `Java 9` and above: version `xruisu-mpc-0.9.0-BETA-21.0.5-shaded.jar` is compatible with `Java 9+`.  

## Features

- Real-Time Calculations: Updates as you input or change data.
- Customizable Data Labels: Most labels for the data provided can be changed via properties.
- Report Viewer/Properties: Viewer and properties for viewing and editing report data.
- Custom Report Headers: Add custom headers to your report.
- Report IDs: Each report has its own ID for easy navigation or future reference.
- Date & Time: Provided for every report generated.
- Single Report Generation: Use properties to set volume/duration and input the required data.
- User Handicaps: Limit an employee's scans using handicaps found in properties.
- Friendly UI/UX: Easy to understand buttons/fields and simple directions.

## Benefits

- Handles the math and calculations for you.
- Allows for simple customizations.
- Makes estimated predictions based on values you provide.
- Generates various types of reports: `Individual`, `Small Groups`, `Large Groups`.
- Sharable `.mpc`, `.pdf`, `print` files for viewing reports, (version dependent).
- Every report includes an ID/Date and time for easy tracking and navigation.

## Cons

- Incompatible Files: Versions built with different Java versions are not compatible with each other.
- Independent developer status may lead to being flagged as malicious without a valid `code-signing certificate`.
- As a *self-taught developer*, some flaws, improper handling, tests, or configurations may be present.

### Assets and Icons

#### Downloaded Icons

- All icons used in MPC that were downloaded are either:
  - `Public Domain`, or
  - Licensed under `CC0 1.0 Universal (Creative Commons Zero)`.
 
#### Downloads
```
Report Viewer Icon: https://www.svgrepo.com/svg/221562/reader-ereader
Properties Icon: https://www.svgrepo.com/svg/378348/package-settings
Printer Icon: https://www.svgrepo.com/svg/474950/print
Folder Icon: https://www.svgrepo.com/svg/478388/open-folder-free
```
- These licenses ensure the icons are free to `use`, `modify`, and `redistribute` without restrictions.

#### MPC Application Icon

- The primary icon representing MPC was created by `Louis Harris | xRuisu` using Photoshop.
- It is an original design and is licensed under the terms of the `GNU General Public License v3.0` as part of this project.

#### Licensing and Attribution

- No attribution is required for the `Public Domain` or `CC0 1.0 licensed` icons, but they are acknowledged here as open resources.

## Project Structure

- `app/`: Entry point and application-specific logic.
- `controllers/`: Handles interfaces and application flow.
- `data/`: Data models and structures.
- `logic/`: Core business logic and calculations.
- `utility/`: General-purpose helper classes and tools.

## Building the Application

To build the application, run the provided script from the root of the project.  
You may need to configure your systems `JAVA_HOME` or `PATH` Environment Variables.

- Project uses `Azul Zulu JDK` version `21.0.5` & `1.8.0_432` which can be downloaded here:

```
https://www.azul.com/downloads/?package=jdk#zulu
```

To run the script, enter the following in the console:

Build for `Java 21.0.5`

```
./build.sh java-21
```

Build for `Java 1.8.0_432`

```
./build.sh java-8
```

#### Metrics Report Disclaimer

The calculations and metrics presented in this report are based on the data provided and the methodologies implemented within the application. While every effort has been made to ensure accuracy, these results may not fully reflect all variables or nuances of actual performance due to potential data discrepancies or limitations in the calculation process. Users are advised to review the results critically and consult additional resources if precise accuracy is required.

## Licensing and Attribution

- This project is licensed under the `GNU General Public License v3.0`.
- Please credit `Louis Harris | xRuisu` as the original creator.

### Preview  
