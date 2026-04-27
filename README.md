# Desktop Palindrome Checker

## Short description
Desktop Palindrome Checker is a desktop application that checks whether a string is a palindrome and estimates whether the input looks meaningful in English.

## Design
The application is divided into UI and core logic layers.

The UI layer includes `MainFrame`, `EditorPanel`, `ResultPanel`, `MeaningfulnessPanel`, and `HistoryPanel`.
These classes are responsible for displaying the interface, showing results, history, and handling user interaction.

The core logic layer includes `PalindromeChecker`, `TextNormalizer`, and meaningfulness-related checkers.
`PalindromeChecker` performs palindrome validation.
`TextNormalizer` applies optional normalization rules before palindrome checking.
The meaningfulness logic combines dictionary-based analysis and heuristic analysis.
`MainScreenController` acts as a bridge between the UI and the core logic by processing user actions and passing data between them.

## Input limit
To keep the application responsive, the maximum supported input length is 1,000,000 characters.
If the input is longer, the palindrome check is skipped and an error message is shown.

## Normalization behavior
Normalization is optional and affects palindrome checking only.

Available normalization options:
- ignore case
- ignore spaces
- ignore punctuation
- Unicode normalization
- ignore diacritics

Behavior:
- If no normalization options are enabled, the original input is checked as-is.
- If at least one normalization option is enabled, the application builds a normalized version of the input and uses it for palindrome checking.
- The normalized text is shown in the `Normalized` tab only when normalization is actually applied.
- The meaningfulness check is based on the original input text, not on the normalized palindrome version.

Examples:
- `Madam, I'm Adam` becomes a palindrome when case, spaces, and punctuation are ignored.
- `café` becomes `cafe` when diacritics are ignored.

## Meaningfulness approach
The meaningfulness checker uses two independent signals:

1. Dictionary-based signal  
   The input is split into tokens. Each token is checked against a bundled offline English word list.  
   The dictionary score is the percentage of tokens found in the dictionary.

2. Heuristic signal  
   Each token is checked against simple rules that help detect random-looking character sequences.
   The current heuristic checks:
  - presence of a vowel
  - absence of a long consonant run
  - absence of a long repeated-character run
  - absence of rare suspicious letter patterns
  - reasonable token length

The final meaningfulness score is calculated as a weighted combination:
- dictionary-based score: 80%
- heuristic score: 20%

The application shows:
- meaningful / not meaningful
- final score
- summary explanation
- per-token breakdown in a table

## Meaningfulness resources
The meaningfulness checker uses an offline English word list bundled with the application.

Source:
- `github.com/dwyl/english-words` (`words.txt`)

Notes:
- the source provides a text file with English words
- license: Unlicense
- the file is stored locally in `src/main/resources/dictionary/english_words.txt`
- no network access is required

## History and export
The application keeps a history of checks and shows:
- timestamp
- input preview
- palindrome result
- meaningfulness result
- final score

History is stored locally in a JSON file and is loaded again when the application starts.

The user can:
- export history to CSV
- export history to JSON
- clear saved history

Double-clicking a history row opens the full original input text.

## File input
The application supports:
- drag and drop of `.txt` files into the input field
- paste from the clipboard into the input field

If a dropped file cannot be read or is not supported, the application shows an error message instead of crashing.

## Updating the word list

The dictionary file is stored at:
`src/main/resources/dictionary/english_words.txt`

To update it:
1. Replace the file with a new UTF-8 text file containing one word per line.
2. Keep the resource path unchanged, or update the code accordingly.
3. Rebuild the project.
4. Run the meaningfulness tests again.

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

`
.\gradlew jar; if (Test-Path .\dist\PalindromeAnalyzer) { Remove-Item .\dist\PalindromeAnalyzer -Recurse -Force }; jpackage --type app-image --name PalindromeAnalyzer --input build\libs --main-jar desktop-palindrome-checker-1.0-SNAPSHOT.jar --main-class com.aska.palindrom.app.Main --dest dist; Copy-Item .\README.md .\dist\PalindromeAnalyzer\README.md -Force
`

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