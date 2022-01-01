package searchengine;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Set;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

/**
 * The WebServer class is the orchestrator of the whole endeavour of setting up a server with a library of WebPages
 * that can be searched/queried for a relevant list of pages to one's query
 */
public class WebServer {
    // Fields
    static final int BACKLOG = 0;
    static final Charset CHARSET = StandardCharsets.UTF_8;
    private final SearchEngine SEARCHENGINE;
    private final HttpServer SERVER;

    // Constructors

    /**
     * Constructor of the WebServer, setting up the search-engine and server
     *
     * @param port         The port for setting up the server
     * @param dataFilename The filename of a source-file with raw data on webpages and their content
     * @throws IOException
     */
    public WebServer(int port, String dataFilename) throws IOException {
        SEARCHENGINE = new SearchEngine(dataFilename);
        SERVER = HttpServer.create(new InetSocketAddress(port), BACKLOG);
        createContext();
        SERVER.start();
        printServerInfo(port);
    }

    // Methods

    /**
     * The key method for orchestrating the processing of the input/output of the search query on the server.
     *
     * @param io The IO variable from the server
     */
    private void processSearchQuery(HttpExchange io) {
        String searchTerm = io.getRequestURI().getRawQuery().split("=")[1];
        Set<WebPage> searchOutput = SEARCHENGINE.search(searchTerm); 
        ArrayList<String> response = formatOutputJson(searchOutput);
        byte[] bytes = response.toString().getBytes(CHARSET);
        respond(io, 200, "application/json", bytes);
    }

    /**
     * Responsible for making a list of JSON-compliant webpages, the latter coming from the user's query.
     *
     * @param searchOutput The set of WebPages complying with the user's search query.
     * @return Returns a formatted list of the webpages for the server.
     */
    protected ArrayList<String> formatOutputJson(Set<WebPage> searchOutput) {
        ArrayList<String> response = new ArrayList<>();
        if (searchOutput == null) {
            return response;
        } else {
            for (WebPage webPage : searchOutput) {
                response.add(String.format("{\"url\": \"%s\", \"title\": \"%s\"}", webPage.getUrl(), webPage.getTitle()));
            }
            return response;
        }
    }

    /**
     * Helper method. From original code.
     *
     * @param port The port for setting up the server
     */
    private void printServerInfo(int port) {
        String msg = " WebServer running on http://localhost:" + port + " ";
        System.out.println("╭" + "─".repeat(msg.length()) + "╮");
        System.out.println("│" + msg + "│");
        System.out.println("╰" + "─".repeat(msg.length()) + "╯");
    }

    /**
     * Helper method. From original code.
     */
    private void createContext() {
        SERVER.createContext("/", io -> respond(io, 200, "text/html", getBytes("web/index.html")));
        SERVER.createContext("/search", io -> processSearchQuery(io));
        SERVER.createContext("/favicon.ico", io -> respond(io, 200, "image/x-icon", getBytes("web/favicon.ico")));
        SERVER.createContext("/code.js", io -> respond(io, 200, "application/javascript", getBytes("web/code.js")));
        SERVER.createContext("/style.css", io -> respond(io, 200, "text/css", getBytes("web/style.css")));
    }

    /**
     * Helper method. From original code.
     *
     * @param filename
     * @return
     */
    private byte[] getBytes(String filename) {
        try {
            return Files.readAllBytes(Paths.get(filename));
        } catch (IOException e) {
            e.printStackTrace();
            return new byte[0];
        }
    }

    /**
     * Helper method. From original code.
     *
     * @param io
     * @param code
     * @param mime
     * @param response
     */
    private void respond(HttpExchange io, int code, String mime, byte[] response) {
        try {
            io.getResponseHeaders().set("Content-Type", String.format("%s; charset=%s", mime, CHARSET.name()));
            io.sendResponseHeaders(200, response.length);
            io.getResponseBody().write(response);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            io.close();
        }
    }

    /**
     * Getter for the server.
     *
     * @return Returns the set-up server
     */
    public HttpServer getHttpServer() {
        return SERVER;
    }

    public static void main(final String... args) throws IOException {
        String dataFilename = Files.readString(Paths.get("config.txt")).strip();
        new WebServer(8080, dataFilename);
    }
}