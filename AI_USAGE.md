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
