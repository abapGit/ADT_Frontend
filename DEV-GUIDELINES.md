# Development Guidelines

## Prerequsites
* Java 8
* [Eclipse for RCP and RAP Developers](https://www.eclipse.org/downloads/packages/release/2020-09/r/eclipse-ide-rcp-and-rap-developers)
* Latest [ADT Plugins](https://tools.hana.ondemand.com/#abap) installed in Eclipse for RCP and RAP Developers
* Latest [ADT_Backend](https://github.com/abapGit/ADT_Backend) or [odense-z](https://github.com/abapGit/odense-z) for Z-Namespace or [Project Odense](https://github.com/SAP/project-odense) for SAP Namespace installed via [abapGit](https://github.com/abapGit/abapGit) ([documentation](https://docs.abapgit.org/guide-online-install.html)) on your ABAP system

## Import
* Clone git repository on disk
* Import via Eclipse:
  * `File` -> `Import` -> `Existing Projects into Workspace`
  * Select root directory: `<path to cloned repo on disk>`
  * Select All
  * Finish

## Quality Tools
The below-mentioned tools are used to verify the quality of the source code during submitting a Pull Request:

* [Spotbugs](https://spotbugs.github.io/)
  * Run locally via maven: `mvn clean verify`

## Guides
[SAP NetWeaver How-To Guide: SDK for the ABAP Development Tools](https://www.sap.com/documents/2013/04/12289ce1-527c-0010-82c7-eda71af511fa.html)

## Troubleshooting
Error `Unrecognized option: --add-modules=ALL-SYSTEM` [Stack Overflow](https://stackoverflow.com/questions/46807468/eclipse-wont-open-in-linux-mint-and-java-wont-open-in-different-directories)

## Create new release
New releases will only be accepted by project maintainers.

### Versioning
* We stick to the concepts of [Semantic Versioning](https://semver.org/)
* Create a new version
  * `mvn tycho-versions:set-version -DnewVersion=<New Version>`
* Create a "raise version" Pull Request with the updated files.
* After it was merged, create a new git tag with the same version.

### Build update site content
* Run `mvn clean verify`
* Open a new Pull Request on https://github.com/abapGit/eclipse.abapgit.org
* Upload binaries and create a release tag