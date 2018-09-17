# Development Guidelines

### Prerequsites
* Java 8
* [Eclipse for RCP and RAP Developers](https://www.eclipse.org/downloads/packages/release/oxygen/3a/eclipse-rcp-and-rap-developers) - currently only Eclipse Oxygen is supported
* Latest [ADT Plugins](https://tools.hana.ondemand.com/#abap) installed in DevIDE
* Latest [ADT_Backend](https://github.com/abapGit/ADT_Backend) installed on backend ABAP system

### Import
* Clone Repo on disk
* Import via Eclipse:
  * File -> Import -> Existing Projects into Workspace
  * Select root directory: `<path to cloned repo on disk>`
  * Select All
  * Finish

### Versioning
* Create a new version via cmd: `mvn tycho-versions:set-version -DnewVersion=<New Version>`
* Create a "raise version" PR with the updated files.
* After it was merged, a new git tag with the same version needs to be created.

### Build updatesite content
* Run `mvn clean verify`
* Open PR on https://github.com/abapGit/eclipse.abapgit.org
* Upload binaries to release tag

### Quality Tools
The below mentioned tools are used to verify the quality of the source code during submitting a PR:

* Codacy
* Spotbugs
  * Run locally via maven: `mvn clean verify

### Guides

[SAP NetWeaver How-To Guide: SDK for the ABAP Development Tools](https://www.sap.com/documents/2013/04/12289ce1-527c-0010-82c7-eda71af511fa.html)

### Troubleshooting

Error `Unrecognized option: --add-modules=ALL-SYSTEM` [stackoverflow](https://stackoverflow.com/questions/46807468/eclipse-wont-open-in-linux-mint-and-java-wont-open-in-different-directories)
