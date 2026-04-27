### Tool
For all - ChatGPT (OpenAI)

#### 1. Windows packaging command
AI was used to help construct the following PowerShell command for building the packaged Windows application:

.\gradlew jar; if (Test-Path .\dist\PalindromeAnalyzer) { Remove-Item .\dist\PalindromeAnalyzer -Recurse -Force }; jpackage --type app-image --name PalindromeAnalyzer --input build\libs --main-jar desktop-palindrome-checker-1.0-SNAPSHOT.jar --main-class com.aska.palindrom.app.Main --dest dist; Copy-Item .\README.md .\dist\PalindromeAnalyzer\README.md -Force

Purpose of use:
To create a single deterministic build command that:

builds the JAR
removes the previous packaged folder
creates a new Windows app-image
copies the README file into the packaged output

Prompt summary:
Asked for help generating a one-line PowerShell command to rebuild the JAR, package the app with jpackage, and copy the README file into the output folder.

#### 2. README documentation

AI was used to help draft and refine the README sections Build instructions.


Purpose of use:
To improve clarity, structure, and wording of the project documentation.

Prompt summary:
Asked for clearer step-by-step build instructions and for a detailed explanation of the packaging command, including what each part of the command does.

Output usage:
The AI-generated text was adapted and included in the final README, especially in the section describing the packaging flow and the Command breakdown.

#### 3. AI usage disclosure file

AI was also used to help draft this AI_USAGE description file.

Purpose of use:
To document how AI assistance was used in the project in a clear and transparent way.

Prompt summary:
Asked for help writing a structured AI usage note that lists the tool name, purpose, and the parts where AI-generated assistance was used.

Output usage:
The generated text was reviewed and adapted before being included in the project documentation.


#### 4. Unicode-aware regex for normalization
AI was used to help adjust a Java regular expression so that Unicode letters and digits, including Cyrillic characters, were preserved during normalization.

Example prompt:
How in Java regex can I replace `[^a-zA-Z0-9]` so that Unicode letters and digits remain, including Cyrillic?

Purpose of use:
To improve normalization logic from ASCII-only matching to Unicode-aware matching.

Output usage:
The suggestion was reviewed and adapted into the normalization logic.

#### 5. Encoding issue in tests
AI was used to help diagnose a character-encoding issue in tests where `café` appeared as `cafГ©`.

Example prompt:
Why in Java tests is the string `café` read as `cafГ©`? What can cause this and how can it be fixed?

Purpose of use:
To identify a likely source-encoding or Gradle compilation encoding problem.

Output usage:
The explanation was used to update the build configuration and understand the UTF-8 requirement for source files and tests.

#### 6. Dictionary source search
AI was used to help identify a free-licensed English word list suitable for offline meaningfulness checking.

Example prompt:
Find me a file with a free license and a list of English words.

Purpose of use:
To select an offline dictionary source for the meaningfulness feature.

Output usage:
The suggested source was reviewed, and a local dictionary file was added to the project resources.

#### 7. Meaningfulness documentation
AI was used to help draft the `MEANINGFULNESS.md` document.

Example prompt:
Help me write a `MEANINGFULNESS.md` file for my project. I added a meaningfulness checker that uses two independent signals: a dictionary-based check and a heuristic check. The final score is weighted: 80% dictionary-based and 20% heuristic. Explain how token-level analysis works, how the final score is calculated, and what limitations this approach has. Also include an explanation of why a real dictionary word such as `nightly` may still score below 100%, because it loses points in the consonant-run heuristic, and explain why heuristic checks are still applied even when a word is found in the dictionary.

Purpose of use:
To document the meaningfulness signals, scoring model, limitations, and design choices.

Output usage:
The AI-generated text was reviewed, shortened, and adapted before inclusion in the final `MEANINGFULNESS.md`.

#### 8. README updates after feature changes
AI was used to help update README content after new features were added.

Example prompt:
Help me rewrite and expand my README after recent project changes. I added normalization behavior, meaningfulness analysis, history view, CSV and JSON export, drag-and-drop file input, and persistent history loading. I need the README to clearly explain how normalization works, what the meaningfulness checker does, where the dictionary file is stored. 

Purpose of use:
To keep the project documentation aligned with implemented functionality such as normalization behavior, meaningfulness analysis, history, export, and drag-and-drop input.

Output usage:
The generated text was reviewed and edited before being included in the final README.
