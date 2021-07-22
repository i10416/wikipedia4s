package dev.`110416`.wikipedia4s
import cats.implicits.*
import cats.effect.IO
import cats.data.EitherT
import sttp.client3.SttpClientException
import sttp.client3.asynchttpclient.cats.AsyncHttpClientCatsBackend
import scala.concurrent.duration.*
import sttp.client3.SttpBackend
import sttp.client3.Response
import sttp.model.Uri.UriContext
import sttp.client3.basicRequest
import sttp.model.Uri
import io.circe.Json
import io.circe.parser._

import dev.`110416`.wikipedia4s.errors.*

trait Wikipedia4s(using ctx: APIContext) extends APIProtocol {

    def query(keyword: String, limit: Int): IO[Either[WikiError, Seq[SearchResult]]] = {
        val response = request(
          Map(
            "srsearch" -> keyword,
            "srlimit" -> limit.toString,
            "srprop" -> "",
            "list" -> "search"
          )
        )
        val r = for {
            json <- EitherT(response)
            result <- parseJson(json).toEitherT
        } yield result
        r.value
    }

    private def parseJson(json: Json): Either[WikiError, Seq[SearchResult]] = {
        println(json.show)
        json.hcursor
            .as[WikiResponse.SearchResponse]
            .recoverWith { case _ => json.hcursor.as[WikiResponse.WikipediaError] } match {
            case Right(WikiResponse.SearchResponse(SearchQuery(searchInfo, searchResults))) =>
                Right(searchResults)
            case Right(WikiResponse.WikipediaError(ErrorInfo(_))) => Left(WikiError.ServerError)
            case Left(f) => Left(WikiError.ParseError(f.getMessage))
        }
    }

    def request(params: Map[String, String]): IO[Either[WikiError, Json]] = {
        val req =
            basicRequest.get(ctx.uri(ctx.language)(sharedParams ++ params))
        AsyncHttpClientCatsBackend[IO]().flatMap { backend =>
            {
                for {
                    response <- req.send(backend)
                } yield for {
                    j <- parseResponse(response)
                } yield j

            }
        }
    }
    private def parseResponse(
        response: Response[Either[String, String]]
    ): Either[WikiError, Json] = {
        response match {
            case Response(Left(_), statusCode, statusText, _, _, requestmetainfo)
                if statusCode.isClientError =>
                Left(WikiError.InvalidRequestError(statusText))
            case Response(Left(_), _, _, _, _, _) =>
                Left(WikiError.ServerError)
            case Response(Right(body), _, _, _, _, _) =>
                parse(body).leftMap(f => WikiError.ParseError(f.message))
        }
    }
}
