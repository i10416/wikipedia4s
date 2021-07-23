package dev.`110416`.wikipedia4s
import org.scalatest.matchers.should.Matchers
import org.scalatest.flatspec.AnyFlatSpec
import scala.concurrent.duration._
import sttp.client3.Response
class Test extends AnyFlatSpec with Matchers:
    import cats.effect.unsafe.implicits.global

    implicit val ctx: APIContext = APIContext("en")
    val wiki = new Wikipedia4s {}

    wiki.query(Query.Suggest("test", 5)).unsafeRunSync() match {
        case Right(result) => println(result)
        case Left(fail)    => println(fail)
    }
    wiki.query(
      Query.GeoSearch((37.789, -122.4), 500, 10, Some("wikipedia"), Some(Seq("coordinates")))
    ).unsafeRunSync() match {
        case Right(result) => println(result)
        case Left(fail)    => println(fail)
    }
