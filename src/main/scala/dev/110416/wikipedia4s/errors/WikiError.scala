package dev.`110416`.wikipedia4s.errors

enum WikiError {
    case ThresholdExceed
    case ServerError
    case ApplicationError(info:String)
    case ParseError(message: String)
    case InvalidRequestError(message: String)
}
