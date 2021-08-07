package dev.`110416`.wikipedia4s

case class APIContext(var language: String = "en") {
    val uri: String =>String=> String =
        (scheme)=>(locale) => s"$scheme://$locale.wikipedia.org"
    val RATE_LIMIT: Boolean = false
    val RATE_LIMIT_MIN_WAIT: Option[Int] = None
    val RATE_LIMIT_LAST_CALL: Option[Int] = None
    val USER_AGENT = "wikipedia4s (https://github.com/ItoYo16u/wikipedia4s)"
}
