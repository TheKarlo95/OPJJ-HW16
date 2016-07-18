package hr.fer.zemris.java.hw16.trazilica;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import hr.fer.zemris.java.hw16.vector.Vector;

/**
 * {@code TFVector} is class that represents the TF vector for some text.
 * 
 * @author Karlo Vrbić
 * @version 1.0
 */
public class TFVector {

    /** The path to text file to which this TF vector refers to. */
    private Path path;
    /** Map of words and number of their occurrences. */
    private Map<String, Long> words;

    /**
     * Constructs a new {@code TFVector} with specified {@code path} of the text
     * file.
     * 
     * @param path
     *            the path to the text file
     * @throws NullPointerException
     *             if parameter {@code path} is a {@code null} reference
     * @throws IllegalArgumentException
     *             if parameter {@code path} isn't a path to some file
     */
    public TFVector(Path path) {
        this.path = Objects.requireNonNull(path, "Cannot instantiate TFVector with null reference as a path.");
        if (!Files.isRegularFile(path))
            throw new IllegalArgumentException("Cannot instantiate TFVector with non-file path.");

        List<String> wordList = readAllWords(path);

        this.words = new HashMap<>(wordList.size());
        wordList.forEach(word -> {
            Long count = this.words.get(word);
            this.words.put(word, count != null ? ++count : 1);
        });
    }

    /**
     * Constructs a new {@code TFVector} with specified {@code words} array.
     * 
     * @param words
     *            the array of all words of some text
     * @throws NullPointerException
     *             if parameter {@code words} is a {@code null} reference
     */
    public TFVector(String[] words) {
        Objects.requireNonNull(words, "Cannot instantiate TFVector with null reference as words.");
        this.words = new HashMap<>(words.length);
        for (int i = 0; i < words.length; i++) {
            Long count = this.words.get(words[i]);
            this.words.put(words[i], count != null ? ++count : 1);
        }
    }

    /**
     * Returns the path to the text file to which this TF vector refers to.
     * 
     * @return the path to the text file to which this TF vector refers to
     */
    public Path getPath() {
        return path;
    }

    /**
     * Returns the number of occurrences of the specified {@code word} in a text
     * to which this TF vector refers to.
     * 
     * @param word
     *            the word
     * @return the number of occurrences of the specified {@code word} in a text
     */
    public long getWordCount(String word) {
        Long count = words.get(word);
        return count != null ? count : 0L;
    }

    /**
     * Returns {@code true} if this map contains a mapping for the specified
     * key.
     *
     * @param word
     *            the word whose presence is to be tested
     * @return {@code true} if this map contains a mapping for the specified
     *         key; {@code false} otherwise
     */
    public boolean containsWord(String word) {
        if (word == null)
            return false;
        return words.containsKey(word);
    }

    /**
     * Returns the TF vector for the specified dictionary.
     * 
     * @param dictionary
     *            the list of all words
     * @return the TF vector
     */
    public Vector getTFVector(Set<String> dictionary) {
        Vector vector = new Vector(dictionary.size());
        int i = 0;
        for (String word : dictionary) {
            vector.set(i, (double) getWordCount(word));
            i++;
        }

        return vector;
    }

    /**
     * Reads all text from the specified {@code path}, splits it into words and
     * returns it as a list.
     * 
     * @param path
     *            the path to text file
     * @return list of all words from the file specified by {@code path}
     */
    private static List<String> readAllWords(Path path) {
        Objects.requireNonNull(path, "Cannot read words from a null reference as a path to a text file.");
        if (!Files.isRegularFile(path))
            throw new IllegalArgumentException("The path you provided isn't a file. You provided: " + path + ".");

        try {
            return Files.readAllLines(path).stream()
                    .map(s -> Arrays.asList(s.split("[^\\p{L}]+")))
                    .flatMap(List::stream)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
