package dev.`110416`.wikipedia4s

import io.circe.Decoder
import io.circe.syntax._
import io.circe.generic.semiauto.deriveDecoder

trait APIProtocol {

    type Location = (Float | BigDecimal, Float | BigDecimal)

    extension (l: Location) {
        def format: String = s"${l._1}|${l._2}"
    }
    val sharedParams = Map("action" -> "query", "format" -> "json")

    implicit val wikiEerrorDecoder: Decoder[WikiResponse.WikipediaError] =
        deriveDecoder[WikiResponse.WikipediaError]
    implicit val searchResponseDecoder: Decoder[WikiResponse.SearchResponse] =
        deriveDecoder[WikiResponse.SearchResponse]
    implicit val resultDecoder: Decoder[SearchResult] = deriveDecoder[SearchResult]

    enum WikiResponse {
        case SearchResponse(query: SearchQuery)
        case WikipediaError(error: ErrorInfo)
    }
    case class ErrorInfo(info: String)
    case class SearchQuery(searchinfo: SearchInfo, search: Seq[SearchResult])
    case class SearchInfo(totalhits: Int)
    case class SearchResult(title: String, pageid: Int)

}
