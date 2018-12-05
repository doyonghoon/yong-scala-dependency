# Scala Plugin: Add Scala Dependency

> Inserts the queried dependency into the `build.sbt` for you.

The plugin is accessible by a menu in ToolsMenu. Once a dialog popped up, you can search and select a dependency to insert into your `build.sbt`.
It internally uses a maven search api to load a list of dependencies.

## Usage Example

![ToolsMenu](https://github.com/doyonghoon/yong-scala-dependency/blob/not-google-searchable/toolmenu.png?raw=true)

Make sure that you have `build.sbt` file in your current project. This plugin is only visible to the project including `build.sbt` file.


![Dialog](https://github.com/doyonghoon/yong-scala-dependency/blob/not-google-searchable/dialog.png?raw=true)

You can search and select the dependency which you want to insert into your `build.sbt`

Then, it will modify the `build.sbt` file so that you will see the file updated

```
import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.example",
      scalaVersion := "2.12.7",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "depentest",
    libraryDependencies += scalaTest % Test,
    libraryDependencies += com.typesafe.akka % akka % 2.2.0-RC2
  )
```

Or, you will see the following depending on the current structure of `libraryDependencies`

```
name := "AkkaHttpMicroservice"

version := "0.0.1"

scalaVersion := "2.11.8"

resolvers += "akka" at "http://repo.akka.io/snapshots"

libraryDependencies ++= Seq(
	"com.typesafe.akka" % "akka" % "2.2.0-RC2",
	"org.scalatest" %% "scalatest" % "2.2.4"
)
```

## Author

Yong Hoon Do, yhdo@ucsd.edu
