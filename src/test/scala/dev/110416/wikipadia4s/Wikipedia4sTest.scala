package dev.`110416`.wikipedia4s
import org.scalatest.matchers.should.Matchers
import org.scalatest.flatspec.AnyFlatSpec
import scala.concurrent.duration._
class Test extends AnyFlatSpec with Matchers:
    implicit val ctx: APIContext = APIContext("ja")
    ctx.language="en"
    val wiki = new Wikipedia4s {}
    import cats.effect.unsafe.implicits.global

    wiki.query("wikipedia", 10).unsafeRunSync() match {
        case Right(result) => println(result)
        case Left(fail)    => println(fail)
    }
