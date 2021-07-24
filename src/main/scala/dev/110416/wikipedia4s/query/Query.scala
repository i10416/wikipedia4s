package dev.`110416`.wikipedia4s

import org.openapitools.client.model.{ GeoSearchResponse, SearchResponse }
import org.openapitools.client.model.SuggestResponse

trait HasResponseType {
    type ResponseType
}

sealed abstract trait Query
object Query {
    final case class Search(query: String, limit: Int) extends Query with HasResponseType {
        type ResponseType = SearchResponse
    }
    final case class Suggest(query: String, limit: Int) extends Query with HasResponseType {
        type ResponseType = SuggestResponse
    }
    final case class GeoSearch(
        location: (Double, Double),
        radius: Int,
        limit: Int,
        titles: Option[String] = None,
        props: Option[Seq[String]] = None
    ) extends Query
        with HasResponseType {
        type ResponseType = GeoSearchResponse
    }
}
