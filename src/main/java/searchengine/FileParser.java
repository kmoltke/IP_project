package searchengine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Loads in raw data from txt-file, and saves the lines of strings un-partitioned and partitioned to separate fields
 */

public class FileParser {
    // Fields
    private List<String> lines;
    private List<List<String>> pages;
    private String filename;

    // Constructors

    /**
     * Constructor for a FileParser, initializing its fields with un-partitioned data from a raw data file, and
     * partitioned data with list of strings containing only Strings related to a single webpage
     *
     * @param filename The source filename for a file with raw data on web-pages
     */
    public FileParser(String filename) {
        lines = new ArrayList<>();
        pages = new ArrayList<>();
        this.filename = filename;
        listLines();
        listPages();
    }

    // Methods

    /**
     * Read in all the raw data of Strings to its relevant field
     */
    private void listLines() {
        try {
            lines = Files.readAllLines(Paths.get(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Partition the listed Strings into lists pertaining to only a single webpage
     */
    private void listPages() {
        int lastIndex = lines.size();
        for (int i = lines.size() - 3; i >= 0; --i) {
            if (lines.get(i).startsWith("*PAGE")) {
                if (lines.get(i + 1).startsWith("*PAGE") || lines.get(i + 2).startsWith("*PAGE")) {
                    lastIndex = i;
                } else {
                    pages.add(lines.subList(i, lastIndex));
                    lastIndex = i;
                }
            }
        }
        Collections.reverse(pages);
    }

    /**
     * Getter method for returning the list of un-partitioned webpage data
     *
     * @return Returns the list of un-partitioned data of webpage info
     */
    public List<String> getLines() {
        return lines;
    }

    /**
     * Getter method for returning the list of partitioned webpage sections
     *
     * @return Returns the list of partitioned webpage sections
     */
    public List<List<String>> getPages() {
        return pages;
    }
}
