package hr.fer.zemris.java.hw16.trazilica.commands;

import java.nio.file.Path;
import java.util.Objects;

/**
 * {@code SearchResult} is a class that represent one result of the search.
 * 
 * @author Karlo Vrbić
 * @version 1.0
 * @see Comparable
 */
public class SearchResult implements Comparable<SearchResult> {

    /** Path to a file that is result of a search. */
    private Path path;
    /**
     * Value from 0 to 1 that represents similarity of this result to the text
     * in the {@code path}. Greater the number greater is the similarity.
     */
    private double similarity;

    /**
     * Constructs a new {@code SearchResult} with specified {@code path} and
     * {@code millisPassed}.
     * 
     * @param path
     *            the path to a file that is result of a search
     * @param similarity
     *            the similarity coefficient
     * @throws NullPointerException
     *             if parameters {@code path} or {@code millisPassed} are a
     *             {@code null} reference
     * @throws IllegalArgumentException
     *             if parameter {@code millisPassed} is a negative number
     */
    public SearchResult(Path path, double similarity) {
        Objects.requireNonNull(path, "Cannot instantiate SearchResult with null reference as a path.");
        Objects.requireNonNull(similarity, "Cannot instantiate SearchResult with null reference as a similarity.");

        if (similarity < 0)
            throw new IllegalArgumentException("Similarity cannot be a negative number.");

        this.path = path.toAbsolutePath();
        this.similarity = similarity;
    }

    /**
     * Returns the path to a file that is result of a search.
     * 
     * @return the path to a file that is result of a search
     */
    public Path getPath() {
        return path;
    }

    /**
     * Returns the similarity coefficient.
     * 
     * @return the similarity coefficient
     */
    public double getSimilarity() {
        return similarity;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((path == null) ? 0 : path.hashCode());
        long temp;
        temp = Double.doubleToLongBits(similarity);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SearchResult other = (SearchResult) obj;
        if (path == null) {
            if (other.path != null)
                return false;
        } else if (!path.equals(other.path))
            return false;
        if (Double.doubleToLongBits(similarity) != Double.doubleToLongBits(other.similarity))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "SearchResult [path=" + path + ", similarity=" + similarity + "]";
    }

    @Override
    public int compareTo(SearchResult that) {
        int compare = Double.compare(this.similarity, that.similarity);

        return compare != 0 ? compare : this.path.compareTo(that.path);
    }

}