
package searchengine;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;
import java.net.BindException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
class WebServerTest {
    WebServer server = null;

    @BeforeAll
    void setUp() {
        try {
            var rnd = new Random();
            while (server == null) {
                try {
                    server = new WebServer(rnd.nextInt(60000) + 1024, "data/test-file.txt");
                } catch (BindException e) {
                    // port in use. Try again
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AfterAll
    void tearDown() {
        server.getHttpServer().stop(0);
        server = null;
    }

    @Test
    void lookupWebServer_oneResult() {
        String baseURL = getBaseURL();
        assertEquals("[{\"url\": \"http://page1.com\", \"title\": \"title1\"}]",
            httpGet(baseURL + "word2"));
        assertEquals("[{\"url\": \"http://page2.com\", \"title\": \"title2\"}]", 
            httpGet(baseURL + "word3"));
    }

    @Test 
    void lookupWebServer_noResults() {
        String baseURL = getBaseURL();
        assertEquals("[]", 
            httpGet(baseURL + "word4"));
    }

    @Test 
    void lookupWebServer_multipleResultsIgnoringOrder() {
        String baseURL = getBaseURL();
        List<String> expected = Arrays.asList("{\"url\": \"http://page1.com\", \"title\": \"title1\"}, {\"url\": \"http://page2.com\", \"title\": \"title2\"}".split("(?<=}),\\s(?=\\{)"));
        List<String> actual = Arrays.asList(httpGet(baseURL + "word1").replace("[", "").replace("]", "").split("(?<=}),\\s(?=\\{)"));
        assertTrue(expected.size() == actual.size());
        assertTrue(expected.containsAll(actual));
        assertTrue(actual.containsAll(expected));
    }

    @Test 
    void lookupWebServer_complexQuery_multipleResultsIgnoringOrder() {
        String baseURL = getBaseURL();
        List<String> expected = Arrays.asList("{\"url\": \"http://page1.com\", \"title\": \"title1\"}, {\"url\": \"http://page2.com\", \"title\": \"title2\"}".split("(?<=}),\\s(?=\\{)"));
        List<String> actual = Arrays.asList(httpGet(baseURL + "word2%20OR%20word3").replace("[", "").replace("]", "").split("(?<=}),\\s(?=\\{)"));
        assertTrue(expected.size() == actual.size());
        assertTrue(expected.containsAll(actual));
        assertTrue(actual.containsAll(expected));
    }

    @Test
    void formatOutputJson_returnsCorrectOutput() {
        // The input for the result to be tested
        String[][] testSearchOutputArr = {{"*PAGE:http://page1.com", "title1", "word1", "word2"}, {"*PAGE:http://page2.com", "title2", "word1", "word3"}};
        List<List<String>> testSearchOutput = new ArrayList<>();
        for (String[] arr : testSearchOutputArr) {
            List<String> arrList = new ArrayList<>();
            Collections.addAll(arrList, arr);
            testSearchOutput.add(arrList);
        }
        Set<WebPage> testWebPages = new LinkedHashSet<>();
        testWebPages.add(new WebPage(testSearchOutput.get(0)));
        testWebPages.add(new WebPage(testSearchOutput.get(1)));

        // The expected value to compare with
        String[] expectedFormat = {"{\"url\": \"http://page1.com\", \"title\": \"title1\"}", "{\"url\": \"http://page2.com\", \"title\": \"title2\"}"};

        // Setting up a WebServer with a different source file
        try {
            server = new WebServer(8080, "data/test-file-noTitles.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // The test
        assertArrayEquals(expectedFormat, server.formatOutputJson(testWebPages).toArray());
    }

    private String httpGet(String url) {
        var uri = URI.create(url);
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder().uri(uri).GET().build();
        try {
            return client.send(request, BodyHandlers.ofString()).body();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getBaseURL() {
        return String.format("http://localhost:%d/search?q=", server.getHttpServer().getAddress().getPort());
    }
}
