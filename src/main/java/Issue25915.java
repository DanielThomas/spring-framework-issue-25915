import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.reactive.function.client.ClientResponse;

class Issue25915 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Issue25915.class);

    public static void main(String[] args) throws IOException {
        LOGGER.info("Reading unix SSE");
        long currentTimeMillis = System.currentTimeMillis();
        ParameterizedTypeReference<ServerSentEvent<String>> stringSSE = new ParameterizedTypeReference<ServerSentEvent<String>>() {
        };
        String unixBody = classpathResourceString("bootstrap-event-stream.txt");
        ClientResponse.create(HttpStatus.OK)
                .body(unixBody)
                .build()
                .bodyToFlux(stringSSE)
                .subscribe();
        long unixDuration = System.currentTimeMillis() - currentTimeMillis;
        LOGGER.info("Unix line ending duration {}ms", unixDuration);

        LOGGER.info("Reading DOS SSE");
        currentTimeMillis = System.currentTimeMillis();
        String dosBody = classpathResourceString("bootstrap-event-stream-dos.txt");
        ClientResponse.create(HttpStatus.OK)
                .body(dosBody)
                .build()
                .bodyToFlux(stringSSE)
                .subscribe();
        long dosDuration = System.currentTimeMillis() - currentTimeMillis;
        LOGGER.info("Dos line ending duration {}ms", dosDuration);
    }

    public static String classpathResourceString(String path) throws IOException {
        InputStream inputStream = new ClassPathResource(path)
                .getInputStream();
        return FileCopyUtils.copyToString(new InputStreamReader(inputStream));
    }
}