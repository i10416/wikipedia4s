## Wikipedia(Media Wiki) Client for Scala 3 (and Functional Programming Libraries)


```scala
implicit val ctx: APIContext = APIContext("en")
val wiki = new Wikipedia4s {}
wiki.search("wikipedia", 10).unsafeRunSync() match {
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

On compilation, a client implementation and models are generated from protocol/openapi.yaml under the protocol directory.

### Supporting APIs

- wip