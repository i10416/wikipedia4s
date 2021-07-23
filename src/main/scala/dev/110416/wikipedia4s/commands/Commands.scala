package dev.`110416`.wikipedia4s

import org.openapitools.client.model.{SearchResponse,GeoSearchResponse}

trait HasExpectResponseType {
    type ResponseType
}


sealed abstract trait Query
object Query {
    final case class Search(query: String, limit: Int) extends Query with HasExpectResponseType {
        type ResponseType = SearchResponse
    }
    final case class GeoSearch(location:(Double,Double), radius: Int,limit:Int) extends Query with HasExpectResponseType {
        type ResponseType = GeoSearchResponse
    }
}


enum Command {
    case Search(query: String, limit: Int)
    case Suggest(query: String, limit: Int)
    case Help
}
