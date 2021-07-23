package dev.`110416`.wikipedia4s
import org.scalatest.matchers.should.Matchers
import org.scalatest.flatspec.AnyFlatSpec
import scala.concurrent.duration._
import sttp.client3.Response
class Test extends AnyFlatSpec with Matchers:
    import cats.effect.unsafe.implicits.global

    implicit val ctx: APIContext = APIContext("en")
    val wiki = new Wikipedia4s {}

    wiki.search("wikipedia", 10).unsafeRunSync() match {
        case Right(result) => println(result)
        case Left(fail)    => println(fail)
    }

