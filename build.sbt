import Dependencies._
val scala3Version = "3.0.0"
val scala213 = "2.13.6"

inThisBuild(
  Seq(
    homepage := Some(url("https://github.com/ItoYo16u/wikipedia4s")),
    organization := "dev.110416",
    description := "Scala Wikipedia API Client",
    scalacOptions ++= Seq(
      "-deprecation"
    ),
    
    licenses := Seq(
      "Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")
    ),
    versionScheme := Some("early-semver"),
    crossScalaVersions++= Seq(scala3Version,scala213),
    //scalacOptions ++= Seq("-Xmax-inlines", "50")
  )
)


lazy val root = project
    .in(file("."))
    .settings(
      name := "wikipedia4s",
      version := "0.1.0-SNAPSHOT",
      scalaVersion := scala3Version,
      libraryDependencies ++= Dependencies.deps
    )

lazy val docs = project
    .in(file(".generated_docs"))
    .dependsOn(root)
    .settings(
      mdocIn := file("docs"),
      scalaVersion := scala3Version,
      mdocOut := file(".generated_docs"),
      mdocVariables := Map[String, String]()
    )
    .enablePlugins(MdocPlugin)
