package dev.`110416`.wikipedia4s
import scala.concurrent.duration.*


trait BuildRequest[T <: HasExpectResponseType] {
    def build(t: T)(using
        client: org.openapitools.client.api.DefaultApi,
        ctx: APIContext
    ): APIRequest[t.ResponseType]
}


given BuildRequest[Query.Search] with
    def build(
        q: Query.Search
    )(using client: org.openapitools.client.api.DefaultApi, ctx: APIContext) = {
        import org.openapitools.client.core.JsonSupport.*

        client
            .wApiPhpGet[org.openapitools.client.model.SearchResponse](
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
given BuildRequest[Query.GeoSearch] with
  def build(q:Query.GeoSearch)(using client: org.openapitools.client.api.DefaultApi, ctx: APIContext) = {
        import org.openapitools.client.core.JsonSupport.*

        client
            .wApiPhpGet[org.openapitools.client.model.GeoSearchResponse](
              ctx.USER_AGENT,
              "json",
              "query",
              Map(
                "gsradius" -> q.radius.toString,
                "list" -> "geosearch",
                "gscoord"-> s"${q.location._1}|${q.location._2}",
                "gslimit" -> q.limit.toString
              )
            )
            .readTimeout(5.seconds)
        }