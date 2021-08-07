package dev.`110416`.wikipedia4s
import cats.implicits.*
import dev.`110416`.wikipedia4s.errors.*
import sttp.client3.DeserializationException
import sttp.client3.HttpError
import sttp.client3.Identity
import sttp.client3.RequestT
import sttp.client3.Response
import sttp.client3.ResponseException
import sttp.client3.asynchttpclient.cats.AsyncHttpClientCatsBackend
import sttp.client3.basicRequest

import scala.concurrent.duration.*
import cats.effect.kernel.Async

type APIRequest[T] = RequestT[Identity, Either[
  ResponseException[String, io.circe.Error],
  org.openapitools.client.model.ErrorResponse | T
], Any]

type APIResponse[T] = Response[Either[
  ResponseException[String, io.circe.Error],
  org.openapitools.client.model.ErrorResponse | T
]]
trait Wikipedia4s[F[_]:Async](using ctx: APIContext) {
    val client: org.openapitools.client.api.DefaultApi = org.openapitools.client.api
        .DefaultApi(ctx.uri("http")(ctx.language))

    def query[T <: HasResponseType : BuildRequest](
        q: T
    ): F[Either[WikiError, q.ResponseType]] = {
        implicit val c = client
        AsyncHttpClientCatsBackend[F]().flatMap { backend =>
            {
                for {
                    response <- summon[BuildRequest[T]].build(q).send(backend)
                } yield parseResponse(response)
            }
        }
    }

    private def parseResponse[T](
        response: APIResponse[T]
    ): Either[WikiError, T] = {
        response.body.leftMap(handleCommonError) match {
            case Right(response: T) =>
                Right(response)
            case Right(response: org.openapitools.client.model.ErrorResponse) =>
                Left(WikiError.ApplicationError(response.error.info))
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
}
