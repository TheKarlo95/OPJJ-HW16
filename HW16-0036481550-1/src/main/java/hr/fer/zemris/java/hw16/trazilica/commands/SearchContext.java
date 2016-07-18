package hr.fer.zemris.java.hw16.trazilica.commands;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import hr.fer.zemris.java.hw16.trazilica.TFVector;
import hr.fer.zemris.java.hw16.vector.Vector;

/**
 * {@code Context} is a class that holds information about the current state of
 * the search engine(dictionary, current results, vectors, ...)
 * 
 * @author Karlo Vrbić
 * @version 1.0
 */
public class SearchContext {

    /** Path of the file containing stop words. */
    private static final Path STOP_WORDS_PATH = Paths.get("hrvatski_stoprijeci.txt");
    /** List of stop words. */
    private static List<String> STOP_WORDS;

    static {
        try {
            STOP_WORDS = Files.readAllLines(STOP_WORDS_PATH).stream()
                    .map(s -> s.trim().toLowerCase())
                    .sorted()
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /** List of all words. */
    private Set<String> dictionary;
    /** List of TF vectors. */
    private List<TFVector> tfVectors;
    /** Map of TF-IDF vectors. */
    private Map<Path, Vector> vectors;
    /** List of search resutls. */
    List<SearchResult> results;

    /**
     * Constructs a new {@code Context} with specified {@code path} of the
     * directory with text files.
     * 
     * @param path
     *            the path to the directory with text files
     * @throws NullPointerException
     *             if parameter {@code path} is a {@code null} reference
     * @throws IllegalArgumentException
     *             if parameter {@code path} isn't a path to some directory
     */
    public SearchContext(Path path) {
        Objects.requireNonNull(path, "Cannot instantiate SearchContext with null reference as a path.");
        if (!Files.isDirectory(path))
            throw new IllegalArgumentException("Cannot instantiate SearchContext with non-directory path.");

        results = new ArrayList<>();
        try {
            dictionary = Files.walk(path)
                    .filter(p -> Files.isRegularFile(p) && !p.equals(path))
                    .map(p -> readAllLines(p))
                    .flatMap(List::stream)
                    .map(line -> Arrays.asList(line.trim().toLowerCase().split("[^\\p{L}]+")))
                    .flatMap(List::stream)
                    .collect(Collectors.toSet());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        dictionary.removeAll(STOP_WORDS);

        try {
            tfVectors = Files.walk(path)
                    .filter(p -> Files.isRegularFile(p) && !p.equals(path))
                    .map(p -> new TFVector(p))
                    .collect(Collectors.toList());

            vectors = new HashMap<>();
            for (TFVector vector : tfVectors) {
                vectors.put(vector.getPath(), getTFIDFVector(vector.getTFVector(dictionary)));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns the number of words in {@code dictionary}.
     * 
     * @return the number of words in {@code dictionary}
     */
    public int dictionarySize() {
        return this.dictionary.size();
    }

    /**
     * Returns the list of all words.
     * 
     * @return the list of all words
     */
    public Set<String> getDictionary() {
        return dictionary;
    }

    /**
     * Returns the list of Ds.
     * 
     * @return the list of TF vectors
     */
    public List<TFVector> getTFVectors() {
        return tfVectors;
    }

    /**
     * Returns the list of TF-IDF vectors.
     * 
     * @return the list of TF-IDF vectors
     */
    public Map<Path, Vector> getTFIDFVectors() {
        return vectors;
    }

    /**
     * Returns the TF-IDF vector for the specified {@code tfVector}.
     * 
     * @param tfVector
     *            the TF vector
     * @return the TF-IDF vector
     */
    public Vector getTFIDFVector(Vector tfVector) {
        Vector vector = new Vector(dictionary.size());
        int i = 0;
        for (String word : dictionary) {
            vector.set(i, tfVector.get(i) != 0 ? tfVector.get(i) * getIDFValue(word) : 0.0);
            i++;
        }

        return vector;
    }

    /**
     * Returns the list of all results.
     * 
     * @return the list of all results
     */
    public List<SearchResult> getResults() {
        return results;
    }

    /**
     * Returns the search result at the specified position in search results
     * list.
     *
     * @param index
     *            index of the search result to return
     * @return the search result at the specified position in search results
     *         list
     * @throws IndexOutOfBoundsException
     *             if the index is out of range (
     *             {@code index &lt; 0 || index &gt;= size()})
     */
    public SearchResult getResult(int index) {
        return results.get(index);
    }

    /**
     * Appends the specified search result to the end of search results list.
     *
     * @param result
     *            the search result to be appended to the search results list
     * @throws NullPointerException
     *             if the specified element is {@code null}
     */
    public void addResult(SearchResult result) {
        results.add(result);
    }

    /**
     * Removes all of the elements from the search results list. The search
     * results list will be empty after this call returns.
     */
    public void clearResults() {
        results.clear();
    }

    /**
     * Returns the IDF value for the specified {@code word}.
     * 
     * @param word
     *            the word we calculate IDF value for
     * @return the IDF value
     * @throws NullPointerException
     *             if parameter {@code word} isn't a path to some directory
     */
    private double getIDFValue(String word) {
        Objects.requireNonNull(word, "Cannot calculate IDF value with null reference as a word.");
        int numOfFilesWhereWordOccurrs = tfVectors.stream()
                .filter(v -> v.containsWord(word))
                .collect(Collectors.toList()).size();

        return Math.log(((double) tfVectors.size()) / numOfFilesWhereWordOccurrs);
    }

    /**
     * Read all lines from a file. Bytes from the file are decoded into
     * characters using the {@link StandardCharsets#UTF_8 UTF-8} {@link Charset
     * charset}.
     *
     * @param path
     *            the path to the file
     * @return the lines from the file as a {@code List}; whether the {@code
     *          List} is modifiable or not is implementation dependent and
     *         therefore not specified
     * @throws RuntimeException
     *             if an I/O error occurs reading from the file or a malformed
     *             or unmappable byte sequence is read
     * @throws SecurityException
     *             In the case of the default provider, and a security manager
     *             is installed, the {@link SecurityManager#checkRead(String)
     *             checkRead} method is invoked to check read access to the
     *             file.
     */
    private static List<String> readAllLines(Path path) {
        try {
            return Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
