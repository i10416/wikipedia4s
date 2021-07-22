package dev.`110416`.model

case class Page(title: Option[String], pageId: Option[String]) {
    override def toString(): String = s"<WikipediaPage ${title.getOrElse(pageId.getOrElse(""))}>"
}
