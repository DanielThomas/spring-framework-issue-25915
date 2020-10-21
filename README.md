Sample project for https://github.com/spring-projects/spring-framework/issues/25915

Reproduce issue with `./gradlew run` or by running main class `Issue25915` from an IDE.

```
20:50:49.926 [main] INFO  Issue25915 - Reading unix SSE
20:51:09.555 [main] INFO  Issue25915 - Unix line ending duration 19627ms
20:51:09.556 [main] INFO  Issue25915 - Reading DOS SSE
20:51:09.603 [main] INFO  Issue25915 - Dos line ending duration 47ms
```
