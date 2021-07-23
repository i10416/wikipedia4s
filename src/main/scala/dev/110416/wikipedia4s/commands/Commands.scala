package dev.`110416`.wikipedia4s

enum Command {
  case Search(query:String,limit:Int)
  case Suggest(query:String,limit:Int)
  case Help
}