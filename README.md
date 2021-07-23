## Wikipedia(Media Wiki) Client for Scala 3 (and Functional Programming Libraries)


```scala
implicit val ctx: APIContext = APIContext("en")
val wiki = new Wikipedia4s {}
wiki.query(Query.Suggest("helloworld", 5)).unsafeRunSync() match {
    case Right(result) => println(result)
    case Left(fail)    => println(fail)
}

wiki.query(Query.Search("helloworld", 5)).unsafeRunSync() match {
    case Right(result) => println(result)
    case Left(fail)    => println(fail)
}

wiki.query(Query.GeoSearch((37.789, -122.4), 500, 10, Some("wikipedia"))).unsafeRunSync() match {
    case Right(result) => println(result)
    case Left(fail)    => println(fail)
}
```
### How to Develop

```shell
git clone path/to/repo
cd repo
sbt
```

Add openapi.yaml, update `src/main/scala/dev/110416/commands`  and  `src/main/scala/dev/110416/typeclass` typeclass.

On compilation, a client implementation and models are generated from protocol/openapi.yaml under the protocol directory.

### Supporting APIs

- Search
- GeoSearch
- Suggest