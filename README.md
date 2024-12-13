This is a Kotlin Multiplatform project targeting Android, iOS.

* `/composeApp` is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - `commonMain` is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s Core for the iOS part of your Kotlin app,
    `iosMain` would be the right folder for such calls.

* `/iosApp` contains iOS applications. Even if you’re sharing your UI with Compose Multiplatform, 
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.

**********Initial Commit Steps**********

The following steps were taking in Book landing screen (Load books and search books)

**Domain:**

- Started with domain model in Book package
- created utilities like generic error and some extensions in core domain package
  
**Presentation**
- The played with presentation. In order to achieve MVI first created State and ActionInterface
- Created VM , added mutable states and onActions and also observe the Actions as we know what to do with them
- Created UI Components and Book Screen
  
**Data**
- Created DTOs
- Created Http Client factory and extension in Core Data package
- Created Data Source in book data package and created its abstraction (Interface). We were still not able to use this abstraction in domain/ presentation as it was returning the DTO of data package, violation of clean
- Created repository and return data while utilizing mappers (mapper should also in data ad domain will have no reference to data).
- Now again created its abstraction in domain package and provide that to viewmodel as VM will refer to domain not data
- For sake of running app , at this point provided hard repository to VM

**********Second Commit Steps**********

Add Koin initialization to Android and iOS MainViewController.

**********Third Commit Steps**********

Created Navigation components and define route interface
Pass data from previous screen and Show text in next screen

**********Forth Commit Steps**********

Add ShareViewModel to Modules and implement onSelectBook in ShareViewModel.
They key point is here sometimes passing nav args, local db are much better option than this shared 
ViewModel, Here to paas lagge model as Nav graph is not good as large data may cause crash in navArgument. So we are not going to create a GodFather Viewmodel with all logic in it.
SharedViewmodel will come only with data that is required to shared e.g. state

**********Fifth Commit Steps**********
Create Detail screen , following same approach of creating state, action , VM, screen etc. 
Hit API call, the only new thing is Serializable which was used to handle responses of same key.
