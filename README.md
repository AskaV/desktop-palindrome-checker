# Desktop Palindrome Checker

## Short description
Desktop Palindrome Checker is a simple desktop application that checks whether a string is a palindrome.

## Design
The application is divided into UI and core logic layers.
The UI layer includes `MainFrame`, `EditorPanel`, and `ResultPanel`. 
These classes are responsible for displaying the interface, showing results, and handling user interaction.

The core logic layer includes `PalindromeChecker` and `PalindromeGenerator`. 
`PalindromeChecker` performs palindrome validation, while `PalindromeGenerator` creates deterministic palindrome strings for testing. 
`MainScreenController` acts as a bridge between the UI and the core logic by processing user actions and passing data between them.

## Prerequisites

### For running the packaged application
- Windows OS


### For building the project and running tests
- Windows OS
- JDK 21


## Build instructions
### Build the runnable JAR
1. Open PowerShell or Command Prompt in the project root directory.
2. Run the following command:

`gradlew.bat clean jar`

3. After the build is finished, the runnable JAR file will be available in the `build/libs/` folder.

### Build the packaged Windows application
1. Open PowerShell in the project root directory.
2. Run the following command:

`.\gradlew jar; if (Test-Path .\dist\PalindromeAnalyzer) { Remove-Item .\dist\PalindromeAnalyzer -Recurse -Force }; jpackage --type app-image --name PalindromeAnalyzer --input build\libs --main-jar desktop-palindrome-checker-1.0-SNAPSHOT.jar --main-class com.aska.palindrom.app.Main --dest dist; Copy-Item .\README.md .\dist\PalindromeAnalyzer\README.md -Force`

3. After the packaging process is complete, the Windows artifact will be available in the `dist/PalindromeAnalyzer/` folder.

### Command breakdown

The following section explains the single-line command used above to build the packaged Windows application.

- `.\gradlew jar`  
  Builds the project JAR file.

- `if (Test-Path .\dist\PalindromeAnalyzer) { Remove-Item .\dist\PalindromeAnalyzer -Recurse -Force }`  
  Removes the previous packaged application folder if it already exists, so `jpackage` can create a new one.

- `jpackage --type app-image --name PalindromeAnalyzer --input build\libs --main-jar desktop-palindrome-checker-1.0-SNAPSHOT.jar --main-class com.aska.palindrom.app.Main --dest dist`  
  Creates the packaged Windows application.

  Parameter details:
    - `--type app-image` creates an application folder with the executable and required runtime files
    - `--name PalindromeAnalyzer` sets the name of the generated application folder and executable
    - `--input build\libs` tells `jpackage` where to find the built JAR
    - `--main-jar desktop-palindrome-checker-1.0-SNAPSHOT.jar` specifies the main JAR file
    - `--main-class com.aska.palindrom.app.Main` specifies the application entry point
    - `--dest dist` places the packaged result into the `dist` folder

- `Copy-Item .\README.md .\dist\PalindromeAnalyzer\README.md -Force`  
  Copies the README file into the packaged application folder.


## Run instructions
The packaged Windows application is located in: `dist/PalindromeAnalyzer/`

1. Copy the entire `PalindromeAnalyzer` folder to the target Windows machine.
2. Open the `PalindromeAnalyzer` folder.
3. Run `PalindromeAnalyzer.exe` by double-clicking it.

Important:
Do not move `PalindromeAnalyzer.exe` out of its folder, because the application depends on the files packaged together with it.


## Run tests instructions
Automated tests are run from the project root directory, not from the packaged application folder.

Run all tests with:
`.\gradlew.bat test`

The test report is generated in:
`build/reports/tests/test/index.html`



