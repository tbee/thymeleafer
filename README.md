# Thymeleafer

## Starting
This repo contains a script to run Thymeleaf from the command line. https://www.thymeleaf.org/

This is a regular Maven project, so running *mvn package* will create a fatjar and that can be run like:

*java -jar thymeleafer-...-with-dependencies.jar ...*

But if this repository is checked out, it might be easier to use jbang:  https://www.jbang.dev/

*jbang .\src\main\java\Thymeleafer.java*

This will download all required dependencies.
If jbang is installed, then on Linux/OSX just set the executable flag and run the java file directly.

*.\src\main\java\Thymeleafer.java*

It is also possible to run this script directly from github:

*jbang thymeleafer@tbee*

 ## Usage

Running the command without any arguments will give the usage information.

Usage: thymeleafer [-e=`<encoding>`] [-m=`<templateMode>`] [-o=`<outputFile>`]
[-v=`<valuesFile>`] `<templateFilename>`
* `<template file>`   The template file
* -e, --encoding=`<encoding>` The encoding used in the template file
* -m, --mode=`<templateMode>` The template mode to be used
* -o, --output=`<outputFile>` The file where to write the output
* -v, --values=`<valuesFile>` The values (properties) file to be used in the template