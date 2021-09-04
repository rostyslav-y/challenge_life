# CircleManager Challenge project

Basic project to demonstrate a variant of solution for CircleManager Challenge. The project is focused on **
CircleManager**
implementation and entities directly related to it.

## About the solution

The **CircleManager** is done as a standalone java module/library with an idea to build it as a jar package and attach
it to the mobile project. In a real project this step can be automated as a part of build process.

## Design

The core class in the solution is **InMemoryCircleManagerImpl** implementation of **CircleManager**. It provides most of
functionality related to instantiating of **Circle** and **Member** entities, and building relations between these core
entities.
**Circle** and **Member** entities constructors are inaccessible for user, as these must be created by calling
regarding **CircleManager** methods. Such approach helps menage new instances and avoid API misusing. Requested function
#7 - count duplicates and #8 - remove duplicates, were implemented as collections extension functions. Because they are
not related to Managing, but more like utility functions, so they they are placed in a separate *
CircleCollectionExtensions.kt* file. The main challenge was to make **InMemoryCircleManagerImpl** thread-safe w/o
unnecessary blocking. I have some doubts on the way how *InMemoryCircleManagerImpl#findCirclesWithExactMembersCount* is
implemented as it has membersCountToCircles monitor blocks. I guess there must be better way to do it, but I don't see
so far.

## How to build

To build **domain** module jar file with AndroidStudio use *gradle :domain:build* command. After build process is
finished and tests are passed, copy the /domain/build/libs/domain.jar file to your project and add as a dependency in
the build script. In case of android project, dependency must be added to build.gradle of **app** module via _
implementation fileTree(dir: 'libs', include: ['*.jar'])_ in dependencies block. After these steps the library classes
must be accessible in app module code.

## TBD

Things that wasn't finished due to time limit or additional clarifications needed

1. Some sample Android app that displays how implemented code can be used in a mobile app and interact with layers of
   app. I'd probably focus on MVVM as architecture framework and RxJava as data-flow control + job dispatching
   framework.
2. TODO in the code, e.g. add *updateMemberLastKnownLocation* to have actual geo data in the **CircleManager**. It's not
   required, but it looks like a missing part in the current implementation.
3. Think more about making API more flexible. User may wat to extend entities or have other signatures for methods.
4. More UnitTests

