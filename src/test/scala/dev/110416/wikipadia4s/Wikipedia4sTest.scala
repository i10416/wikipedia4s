package dev.`110416`.wikipedia4s
import org.scalatest.matchers.should.Matchers
import org.scalatest.flatspec.AnyFlatSpec
import scala.concurrent.duration._
import sttp.client3.Response
import cats.effect.IO
class Test extends AnyFlatSpec with Matchers:
    import cats.effect.unsafe.implicits.global
    implicit val ctx: APIContext = APIContext("en")
    val wiki = new Wikipedia4s[IO] {}
    // Just for example. Does not work because catnap uses cat-effect 2.x, which is not compatible with cat-effect3
    // val wiki = new Wikipedia4s[Task]{}
  
    wiki.query(Query.MetaInfo(SiteInfoProps.values.toSeq)).unsafeRunSync() match {
        case Right(result) => println(result)
        case Left(fail)    => println(fail)
    }
