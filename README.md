# ABAP development tools plug-in for abapGit
[![Build Status](https://travis-ci.com/abapGit/ADT_Frontend.svg?branch=master)](https://travis-ci.com/abapGit/ADT_Frontend)

The ABAP development tools plug-in for abapGit is a Eclipse plug-in for the control of abapGit functionality via ABAP development tools.

## Requirements
* Eclipse with [ABAP Development Tools Plugin](https://tools.hana.ondemand.com/#abap)
* SAP Cloud Platform ABAP Environment aka Steampunk System
* On-Premise ABAP System with
    * [abapGit ADT REST](https://github.com/abapGit/ADT_Backend) repository or
    * [odense-z](https://github.com/abapGit/odense-z) (experimental) repository installed

## Download and Installation
### Eclipse update site
The latest released version is available via a [Eclipse Update Site](https://eclipse.abapgit.org/updatesite/).
If you add the update site to your Eclipse installation you'll receice always the latest version.

**Adding the Update Site to your Eclipse installation**:

In Eclipse, choose in the menu bar `Help` > `Install New Software` and add the URL https://eclipse.abapgit.org/updatesite/

## Limitations
Not all abapGit functionality is available with this plugin!

## How to obtain support
Report Bugs via GitHub's bug tracking system.

A bug is a **demonstrable problem** that is caused by the code in the repository. Good bug reports are extremely helpful - thank you!

Guidelines for bug reports:

1. **Use the GitHub issue search** &mdash; check if the issue has already been
   reported.

2. **Check if the issue has been fixed** &mdash; try to reproduce it using the
   latest `master` or development branch in the repository.

3. **Demonstrate the problem** &mdash; provide clear steps that can be reproduced.

A good bug report should not leave others needing to chase you up for more information. Please try to be as detailed as possible in your report. What is your environment? What steps will reproduce the issue? What would you expect to be the outcome? All these details will help to fix any potential bugs.

## Contributing & Development Guidelines
See [DEV-GUIDELINES](DEV-GUIDELINES.md)