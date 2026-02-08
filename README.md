# Member Archive Generator

Generator of the Member Video Archive Page for Shinto Shrine of Shusse Inari in America.

https://github.com/paploo/member-archive-generator

## Table of Contents

- [For Users](#for-users)
  - [Initial Setup](#initial-setup)
  - [Downloading the CSV File of Videos](#downloading-the-csv-file-of-videos)
  - [Program Usage](#program-usage)
    - [Running the Code](#running-the-code)
    - [Help Output](#help-output)
    - [Previewing](#previewing)
    - [WordPress Template Output](#wordpress-template-output)
  - [Using the WordPress Output File](#using-the-wordpress-output-file)
- [For Developers](#for-developers)
  - [Developer Setup](#developer-setup)
  - [Tools](#tools)
  - [Contributing Code Changes](#contributing-code-changes)
  - [Running the Code](#running-the-code-1)
  - [Architecture](#architecture)

## For Users

### Initial Setup

For those just running this code from a distribution artifact:
1. Install JDK 21 or higher. ([Temurin](https://adoptium.net/temurin/releases) is recommended)
2. Unzip the `member-archive-generator-1.0-SNAPSHOT.zip` distribution into a location of your choice.
3. From the command line, navigate to the `bin` directory of the unzipped distribution directory.
4. Run the code using the `member-archive-generator` executable, e.g. `./member-archive-generator --help`
Note that Windows users should use the `member-archive-generator.bat` executable instead of the `member-archive-generator`
executable.

Developers should see the [Developer Setup](#developer-setup) section for instructions on how to set-up their development
environment and run code from there.

### Downloading the CSV File of Videos

The CSV file is maintained in the `Member Video Archive` tab of the `Livestream and Archive Video Links` google doc,
accessible only by shrine volunteers.

To download this file:
1. Navigate to the google doc `Livestream and Archive Video Links`,
2. Click on the `Member Video Archive` tab.
3. Select from the menu: `File > Download > Download as CSV`.

This should result in a file named `Livestream and Archive Video Links  - Member Video Archive.csv` in your selected
downloads directory.

### Program Usage

#### Running the Code

For the following examples, we assume you are running from the distribution binary (see [Initial Setup](#initial-setup)),
and that you are running from a UNIX distribution.

If you would like to run/develop against the source code, see the [Development](#development) section.

#### Help Output

To get the full list of options and their default values, run the command with the `--help` option:
```shell
./member-archive-generator --help
```

#### Previewing

Before generating the full WordPress templateed form, it can be helful to visually preview the list of videos it will
produce. This can be done by using the following switches:
- `--stdout` writes the output to the screen (terminal) instead of a file
- `--format Simple` commands it to write the output as a simple human-readble formatted table of the sections and rows.
- `--input '/path/to/Livestream and Archive Video Links  - Member Video Archive.csv` to select the file, if it is not at the default path.

Thus, the full command is 
```shell
./member-archive-generator --stdout --format Simple --input '/path/to/Livestream and Archive Video Links  - Member Video Archive.csv'
```

#### WordPress Template Output

To update the actual page, we need to generate the WordPress output. By default, this output is tailored for giving
the entire block of HTML to be copy/pasted into the WordPress editor in the `Code Editor` mode. This:
1. Greatly reduces the risk of errors since we don't have to try to fine the right place to copy/paste individual table rows.
2. Makes it easy to change the entire page by updating the templates in this code. 

To run the program with the downloaded CSV file, use the following options:
- `--format WordPress` to get the word press output
- `--output '/path/to/output/file.html'` to select the output file, if the default path is 
- `--input '/path/to/Livestream and Archive Video Links  - Member Video Archive.csv` to select the file, if it is not at the default path.

```shell
./member-archive-generator --format WordPress --output  --input '/path/to/Livestream and Archive Video Links  - Member Video Archive.csv'
```

### Using the WordPress Output File

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
9. To publish: In the page editor tab, click the blue "Save" button in the top-right. **THIS WILL IMMEDIATELY PUBLISH THE PAGE**

## For Developers

### Developer Setup

Developers should set-up and verify their development environment, by successfully completing the following steps:

1. Install JDK 21 or higher. ([Temurin](https://adoptium.net/temurin/releases) is recommended)
2. Ensure `git` is installed on your system (https://github.com/git-guides/install-git)
3. Clone the code hosted at https://github.com/paploo/member-archive-generator
4. Navigate to the project directory: `cd member-archive-generator`
5. Build the project from the command line: `./gradlew clean build test`
6. Check the code runs on the command line: `./gradlew run --args="--help"`
7. Install the [Intellij IDE](https://www.jetbrains.com/idea/) and run the code using the `Mainkt (Help)` run configuration.

### Tools

The core tools used are:
- Language: [kotlin](https://kotlinlang.org/)
- Build Tool: [Gradle](https://gradle.org/)
- IDE: [IntelliJ IDEA](https://www.jetbrains.com/idea/)

Kotlin is developed by JetBrains, maker of [IntelliJ IDEA](https://www.jetbrains.com/idea/); as such you'll find
running and developing this code easiest on the free edition of [IntelliJ IDEA](https://www.jetbrains.com/idea/).

### Contributing Code Changes

To help in development of this code you must:
1. Have a github account, and 
2. Either be added as a collaborator or make your own fork the repository.

Users of this code are expected to make pull requests for any changes to this code base, so that the changes may become
available to all member page maintainers.

### Running the Code

There are three modes in which you can run this code:
1. Run the code from within the [IntelliJ IDEA](https://www.jetbrains.com/idea/),
2. Run via the [gradle build](https://gradle.org/) tool using `./gradlew run`, and
3. Build into a distribution using `./gradlew installDist` and run in the CLI against the product in `./build/install/member-archive-generator/bin`.

#### IntelliJ

When developing, it is typically easiest to run the code from within the [IntelliJ IDEA](https://www.jetbrains.com/idea/).

A few default run configurations on `MainKt` are included with the committed IntelliJ project code.

⚠️If you want to make changes to a run configuration, it is best to duplicate a shared one and create your own custom run configuration.

#### Gradle

The gralde build tool allows running the code via the `run` command. Gradle is typically invoked via the gradle wrapper,
`./gradlew` on linux or `./gradlew.bat` on Windows.

The tricky part of running via the build tool is passing of program arguments. These need to be passed in a quoted string
using the `--args` argument, instead of passing in normally.

Thus, instead of the normal instructions the commands take the following form:
```shell
./graldew run --args="--help"
```
```shell
./gradlew run --args="--stdout --format Simple --input '/path/to/Livestream and Archive Video Links  - Member Video Archive.csv'"
```
```shell
./gradlew run --args="--format WordPress --output  --input '/path/to/Livestream and Archive Video Links  - Member Video Archive.csv'"
```

#### IntallDist

A distribution can be built using `./gradlew installDist` and run in the CLI against the relevant binary in the
product in `./build/install/member-archive-generator/bin` directory.

This gives "classic" command line access to the code, but for active users working against the checked-out code, this
can be more painful than the other methods.

The main benefit of the distribution is that a `zip` archive of the stand-alone tool is build in the
`./build/distributions` directory, and this distribution version can be given to other people for use. 

### Architecture

#### Overview

At a high level, the application is split into the following parts:
1. The `app` namespace, which manages the entry point into the application and bridging the gap to the internal layers.
2. A pipeline that executes the logic in the app.

#### Pipeline

The pipeline is composed of a few steps:
1. A reader to read a set of `Video` objects from a source (e.g. CSV file),
2. A video filter to select which videos are published (e.g. using `isActive`),
3. A grouper to group videos into sets (e.g. by year),
4. A group filter to select which groups are published (e.g. by year selection), and
5. A writer to write the output (e.g. to terminal or to file).