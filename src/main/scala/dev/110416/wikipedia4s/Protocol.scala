package dev.`110416`.wikipedia4s

trait APIProtocol {

    type Location = (Float | BigDecimal, Float | BigDecimal)

    extension (l: Location) {
        def format: String = s"${l._1}|${l._2}"
    }
    val sharedParams = Map("action" -> "query", "format" -> "json")

}
