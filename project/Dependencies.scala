import sbt._

object Dependencies {
    val circeVersion = "0.14.1"
    val scalaTestVersion = "3.2.9"
    def deps = Seq(
      "com.softwaremill.sttp.client3" %% "core" % "3.3.11",
      "com.softwaremill.sttp.client3" %% "async-http-client-backend-cats" % "3.3.11",
      "org.scala-lang.modules" %% "scala-collection-compat" % "2.4.4",
      "org.typelevel" %% "cats-core" % "2.6.1",
      "org.typelevel" %% "cats-effect" % "3.1.1",
      "io.circe" %% "circe-core" % circeVersion,
      "io.circe" %% "circe-generic" % circeVersion,
      "io.circe" %% "circe-parser" % circeVersion,
      "org.scalatest" %% "scalatest" % scalaTestVersion % Test
    )
}
