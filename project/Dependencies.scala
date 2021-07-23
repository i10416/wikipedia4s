import sbt._

object Dependencies {
    val circeVersion = "0.14.1"
    val scalaTestVersion = "3.2.9"
    val sttpVersion = "3.3.11"
    def deps = Seq(
      "com.softwaremill.sttp.client3" %% "async-http-client-backend-cats" % "3.3.11",
      "org.typelevel" %% "cats-core" % "2.6.1",
      "org.typelevel" %% "cats-effect" % "3.1.1",
      "org.scala-lang.modules" %% "scala-collection-compat" % "2.4.4"
    )
    def sharedDependencies = Seq(
      "com.softwaremill.sttp.client3" %% "core" % sttpVersion,
      "com.softwaremill.sttp.client3" %% "circe" % sttpVersion,
       "io.circe" %% "circe-core" % circeVersion,
      "io.circe" %% "circe-generic" % circeVersion,
      "io.circe" %% "circe-parser" % circeVersion,
      "org.scalatest" %% "scalatest" % scalaTestVersion % Test
    )
}
