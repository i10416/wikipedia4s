package dev.`110416`.wikipedia4s
import scala.concurrent.duration.*
import org.openapitools.client.api.DefaultApi
import org.openapitools.client.core.JsonSupport.*
import sttp.client3.asynchttpclient.AsyncHttpClientBackend

trait BuildRequest[T <: HasResponseType] {
    def build(t: T)(using
        client: DefaultApi,
        ctx: APIContext
    ): APIRequest[t.ResponseType]
}

given BuildRequest[Query.Search] with
    def build(
        q: Query.Search
    )(using client: DefaultApi, ctx: APIContext) = {

        client
            .wApiPhpGet[q.ResponseType](
              ctx.USER_AGENT,
              "json",
              "query",
              Map(
                "srsearch" -> q.query,
                "list" -> "search",
                "srprop" -> "",
                "srlimit" -> q.limit.toString
              )
            )
            .readTimeout(5.seconds)

    }

given BuildRequest[Query.Suggest] with
    def build(
        q: Query.Suggest
    )(using client: DefaultApi, ctx: APIContext) = {

        client
            .wApiPhpGet[q.ResponseType](
              ctx.USER_AGENT,
              "json",
              "query",
              Map(
                "srsearch" -> q.query,
                "list" -> "search",
                "srprop" -> "suggestion",
                "srlimit" -> q.limit.toString
              )
            )
            .readTimeout(5.seconds)

    }
given BuildRequest[Query.GeoSearch] with
    def build(q: Query.GeoSearch)(using client: DefaultApi, ctx: APIContext) = {

        client
            .wApiPhpGet[q.ResponseType](
              ctx.USER_AGENT,
              "json",
              "query",
              Map(
                "gsradius" -> q.radius.toString,
                "list" -> "geosearch",
                "gscoord" -> s"${q.location._1}|${q.location._2}",
                "gslimit" -> q.limit.toString,
                "titles" -> q.titles.getOrElse("")
              )
            )
            .readTimeout(5.seconds)
    }

given BuildRequest[Query.MetaInfo] with
    def build(
        q: Query.MetaInfo
    )(using client: DefaultApi, ctx: APIContext) = {

        client
            .wApiPhpGet[q.ResponseType](
              ctx.USER_AGENT,
              "json",
              "query",
              Map(
                "meta" -> "siteinfo",
                "formatversion" -> "2",
                "siprop" -> q.options.map(_.toString.toLowerCase).mkString("|")
              )
            )
            .readTimeout(5.seconds)

    }
