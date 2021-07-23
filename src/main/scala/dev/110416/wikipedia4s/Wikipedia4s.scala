package dev.`110416`.wikipedia4s
import cats.data.EitherT
import cats.effect.IO
import cats.implicits.*
import dev.`110416`.wikipedia4s.errors.*
import io.circe.Json
import io.circe.parser._
import sttp.client3.DeserializationException
import sttp.client3.HttpError
import sttp.client3.Response
import sttp.client3.ResponseException
import sttp.client3.SttpBackend
import sttp.client3.SttpClientException
import sttp.client3.asynchttpclient.cats.AsyncHttpClientCatsBackend
import sttp.client3.basicRequest
import sttp.model.Uri
import sttp.model.Uri.UriContext

import scala.concurrent.duration.*
import sttp.model.Method

trait Wikipedia4s(using ctx: APIContext) {
    val client =  org.openapitools.client.api
            .DefaultApi(ctx.uri("http")(ctx.language))

    def search(
        query: String,
        limit: Int = 10
    ): IO[Either[WikiError, org.openapitools.client.model.SearchResponse]] = {
        searchRequest(query, limit).map(parseSearchResponse)
    }

    /// pretty print response in console

    def execute(command: Command): IO[Unit] = {
        command match {
            case Command.Search(query, limit)  => ???
            case Command.Suggest(query, limit) => ???
            case Command.Help                  => ???
        }
    }

    private def parseSearchResponse(
        response: SearchRequestResponse
    ): Either[WikiError, org.openapitools.client.model.SearchResponse] = {
        response.body.leftMap(handleCommonError) match {
            case Right(searchResponse: org.openapitools.client.model.SearchResponse) =>
                Right(searchResponse)
            case Right(searchResponse: org.openapitools.client.model.ErrorResponse) =>
                Left(WikiError.ApplicationError(searchResponse.error.info))
            case Left(err) => Left(err)
        }
    }

    private def handleCommonError(err: ResponseException[String, io.circe.Error]): WikiError = {
        err match {
            case HttpError(body, statusCode) if statusCode.isClientError =>
                WikiError.InvalidRequestError(body)
            case HttpError(_, _)                     => WikiError.ServerError
            case DeserializationException(body, err) => WikiError.ParseError(body)
        }
    }

    type SearchRequestResponse = Response[Either[
      ResponseException[String, io.circe.Error],
      org.openapitools.client.model.ErrorResponse | org.openapitools.client.model.SearchResponse
    ]]

    private def searchRequest(query: String, limit: Int = 10): IO[SearchRequestResponse] = {
        import org.openapitools.client.core.JsonSupport.*
        val req = client.wApiPhpGet[org.openapitools.client.model.SearchResponse](
              ctx.USER_AGENT,
              "json",
              "query",
              Map("srsearch" -> query, "list" -> "search", "srprop" -> "", "srlimit" -> "10")
            )
            .readTimeout(5.seconds)
        AsyncHttpClientCatsBackend[IO]().flatMap { backend =>
            {
                for {
                    response <- req.send(backend)
                } yield response
            }
        }
    }
}
