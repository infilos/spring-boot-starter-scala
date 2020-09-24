# spring-boot-starter-scala

A spring-boot starter for scala language.

## Integrated features

### Scala Jackson

Now you can use `Json4s.underMapper` to resolve spring underlying `ObjectMapper`.

### Scala Future

Import `com.infilos.spring.api.Converters._`, then you can return a scala `Future` to deffered controller.

### OpenAPI based Swagger

Default enabled swagger-ui with scala-jackson support.

### Shutdown hook

You can register your custom shutdown: `LifecycleConfigure.registerOnShutdown(runnable)`.

### Spring context

You can resolve spring application context from static scope: `ContextConfigure.context`.

### Predefined response

You can return a standard REST response with `Respond.succed` or `Respond.failed`.

### Logback configuration

Provide a default logback configuration with console and size based rolling-file support.

### Http request-response tracing log

Default enabled http tracing log, you can overwrite these configuration:

```
logging.level.org.zalando.logbook=TRACE
logbook.format.style=http
```

See [https://github.com/zalando/logbook](https://github.com/zalando/logbook) for more details.

## Spring-boot version

Version is defined as `2.3.4-0`, `2.3.4` is the spring-boot release version, `-0` means this tookit's build version.

## Contributions

### Version

1. Increase build version: `bash version.sh -b`
2. Change spring-boot release version: `bash version.sh -s 2.3.4`

### Release

- Snapshot: `mvn clean deploy`
- Release: `mvn clean package source:jar gpg:sign install:install deploy:deploy`

