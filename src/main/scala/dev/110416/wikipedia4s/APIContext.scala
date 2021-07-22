package dev.`110416`.wikipedia4s
import sttp.model.Uri
import sttp.model.Uri.PathSegment

case class APIContext(var language: String = "en") {
    val uri: String => Map[String, String] => Uri =
        (locale) =>
            (params) =>
                Uri(
                  scheme = "http",
                  host = s"$locale.wikipedia.org",
                  path = Seq("w", "api.php"),
                  fragment = None
                ).addParams(params)
    val RATE_LIMIT: Boolean = false
    val RATE_LIMIT_MIN_WAIT: Option[Int] = None
    val RATE_LIMIT_LAST_CALL: Option[Int] = None
    val USER_AGENT = "wikipedia4s (https://github.com/ItoYo16u/wikipedia4s)"
}
