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


lazy val protocol = project.in(file("protocol"))
  .enablePlugins(OpenApiGeneratorPlugin)
  .settings(
    scalaVersion := scala213,
    crossScalaVersions := Seq(scala213),
    openApiInputSpec := (baseDirectory.value /  "openapi.yaml").toString,
    openApiGeneratorName :="scala-sttp",
    openApiHttpUserAgent := "wikipedia4s",
    openApiValidateSpec := SettingDisabled,
    openApiTemplateDir	:= (baseDirectory.value / "template").toString,
    openApiGenerateModelTests := SettingEnabled,
    libraryDependencies ++= Seq(
     "com.softwaremill.sttp.client" %% "core" % "2.2.9",
      "com.softwaremill.sttp.client" %% "json4s" % "2.2.9",
      "org.json4s" %% "json4s-jackson" % "3.6.8"
    ),
    openApiIgnoreFileOverride := s"${baseDirectory.in(ThisBuild).value.getPath}/openapi-ignore-file",
    (compile in Compile) := ((compile in Compile) dependsOn openApiGenerate).value,
    openApiOutputDir := baseDirectory.value.name,
    cleanFiles += baseDirectory.value / "src"
  )
lazy val root = project
    .in(file("."))
    .settings(
      name := "wikipedia4s",
      version := "0.1.0-SNAPSHOT",
      scalaVersion := scala3Version,
      libraryDependencies ++= Dependencies.deps
    ).dependsOn(protocol)

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
