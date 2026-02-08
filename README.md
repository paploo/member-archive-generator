# Member Archive Generator

Generator of the Member Video Archive Page for Shinto Shrine of Shusse Inari in America.

## Initial Setup

1. Install JDK 21 or higher. ([Temurin](https://adoptium.net/temurin/releases) is recommended).
2. Navigate to the project directory: `cd member-archive-generator`
3. Build the project: `./gradlew clean build test`

## Downloading the CSV File of Videos

The CSV file is maintained in the `Member Video Archive` tab of the `Livestream and Archive Video Links` google doc,
accessible only by shrine volunteers.

To download this file:
1. Navigate to the google doc `Livestream and Archive Video Links`,
2. Click on the `Member Video Archive` tab.
3. Select from the menu: `File > Download > Download as CSV`.

This should result in a file named `Livestream and Archive Video Links  - Member Video Archive.csv` in your selected
downloads directory.

## Program Usage

It's easiest to develop and run the code from within the [Intellij IDE](https://www.jetbrains.com/idea/), however
it can be run via the command line using the `./gradlew run` command.

### Help Output

To get the full list of options and their default values, run the command with the `--help` option:
```shell
./gradlew run --args="--help"
```

### Previewing

Before generating the full WordPress templateed form, it can be helful to visually preview the list of videos it will
produce. This can be done by using the following switches:
- `--stdout` writes the output to the screen (terminal) instead of a file
- `--format Simple` commands it to write the output as a simple human-readble formatted table of the sections and rows.
- `--input '/path/to/Livestream and Archive Video Links  - Member Video Archive.csv` to select the file, if it is not at the default path.

Thus, the full command is 
```shell
./gradlew run --args="--stdout --format Simple --input '/path/to/Livestream and Archive Video Links  - Member Video Archive.csv'"
```

### WordPress Template Output

To run the program with the downloaded CSV file, use the following options:
- `--format WordPress` to get the word press output
- `--output '/path/to/output/file.html'` to select the output file, if the default path is 
- `--input '/path/to/Livestream and Archive Video Links  - Member Video Archive.csv` to select the file, if it is not at the default path.

```shell
./gradlew run --args="--format WordPress --output  --input '/path/to/Livestream and Archive Video Links  - Member Video Archive.csv'"
```

## Using the WordPress Output File

To use the WordPress Output File, you must:
1. Navigate to the `Member Archives 神道稲荷会メンバー用ビデオ` page in the editor.
2. From the hamburger menu in the top-right, select `Code Editor`, revealing the underlying page HTML.
3. Open the output HTML file in a text editor, and copy the **entire** contents of the file to the clipboard.
4. In the WordPress Code Editor, paste the **entire** contents of the file into the main code box (DO NOT touch the page title box).
5. Click `Exit code editor` in the top-right.
6. From the `View` icon in the top right (loks like a stylized laptop), select `Preview in new tab`.
7. Inspect the page in the new tab (e.g. check that all years are present and populated with the expected videos,
   and that vidoes are correctly linked to YouTube). 
8. Close the preview tab.
8. To publish: In the page editor tab, click the blue "Save" button in the top-right. **THIS WILL IMMEDIATELY PUBLISH THE PAGE**

## Development

### Tools

The core tools used are:
- Language: [kotlin](https://kotlinlang.org/)
- Build Tool: [Gradle](https://gradle.org/)
- IDE: [IntelliJ IDEA](https://www.jetbrains.com/idea/)

Kotlin is developed by JetBrains, maker of [IntelliJ IDEA](https://www.jetbrains.com/idea/); as such you'll find
running and developing this code easiest on the free edition of [IntelliJ IDEA](https://www.jetbrains.com/idea/).