package hr.fer.zemris.java.hw16.trazilica;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

import hr.fer.zemris.java.hw16.trazilica.commands.SearchContext;
import hr.fer.zemris.java.hw16.trazilica.commands.SearchResult;
import hr.fer.zemris.java.hw16.vector.Vector;

/**
 * {@code Konzola} is a main class of the search engine program.
 * <p>
 * This is a console program that has 4 command:
 * <ul>
 * <li>query <i>arguments</i>
 * <li>type <i>indexOfResult</i>
 * <li>results
 * <li>exit
 * </ul>
 * <p>
 * Query command takes arguments and searches the text document for specified
 * arguments and outputs the results. <br>
 * Type command takes index of the result and outputs the content of the file at
 * the specified result index. <br>
 * Results command outputs the list of all results. <br>
 * Exit command exists the program.
 * </ul>
 * 
 * @author Karlo Vrbić
 * @version 1.0
 */
public class Konzola {

    /**
     * Starting point of a program.
     * 
     * @param args
     *            Command-line argument
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            error("You need to provide 1 argument. You provided: " + args.length + ".", 1);
        }

        Path dir = getDirectoryPath(args[0]);

        SearchContext context = new SearchContext(dir);

        System.out.printf("%nVeličina riječnika je %d riječi.%n%n", context.dictionarySize());

        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            l: while (true) {
                System.out.print("Enter command > ");
                String cmdAndArgs = null;

                cmdAndArgs = br.readLine();

                String[] split = cmdAndArgs.split("\\s+");
                String cmd = split[0].trim().toLowerCase();
                String[] cmdArgs = Arrays.copyOfRange(split, 1, split.length);

                try {

                    switch (cmd) {
                        case "query":
                            doQuery(context, cmdArgs);
                            break;
                        case "type":
                            doType(context, cmdArgs);
                            break;
                        case "results":
                            doResults(context, cmdArgs);
                            break;
                        case "exit":
                            break l;
                        default:
                            throw new IllegalArgumentException("Unknown command.");
                    }
                } catch (Exception e) {
                    System.out.printf(e.getMessage() + "%n%n");
                }
            }
        } catch (IOException e) {

        }
    }

    /**
     * Calculates the similarity between arguments in {@code cmdArgs} array and
     * text files in the {@code context}, outputs those results to standard
     * output and stores the results in the {@code context}.
     * 
     * @param context
     *            the context of the search engine
     * @param cmdArgs
     *            the command arguments
     * @throws IllegalArgumentException
     *             if there isn't at least one element in the {@code cmdArgs}
     *             array
     */
    private static void doQuery(SearchContext context, String[] cmdArgs) {
        if (cmdArgs.length < 1)
            throw new IllegalArgumentException("You need to provide at least one argument for command 'query'.");

        TFVector tfVector = new TFVector(cmdArgs);
        Vector tfidf = context.getTFIDFVector(tfVector.getTFVector(context.getDictionary()));

        List<SearchResult> values = new ArrayList<>();

        for (Entry<Path, Vector> entry : context.getTFIDFVectors().entrySet()) {
            Path path = entry.getKey();
            Vector vector = entry.getValue();

            double value = tfidf.mul(vector) / (tfidf.magnitude() * vector.magnitude());

            values.add(new SearchResult(path, value));
        }

        values.sort((v1, v2) -> v2.compareTo(v1));

        context.clearResults();
        for (int i = 0, max = Math.min(10, values.size()); i < max; i++) {
            SearchResult result = values.get(i);
            if (result.getSimilarity() == 0.0)
                break;

            context.addResult(result);
            System.out.printf("[%2d] (%.4f) %s%n", i, result.getSimilarity(), result.getPath().toString());
        }
        System.out.println();
    }

    /**
     * Gets the result with index specified in the {@code cmdArgs} array and
     * outputs the content of that file.
     * 
     * @param context
     *            the context of the search engine
     * @param cmdArgs
     *            the command arguments
     * @throws IllegalArgumentException
     *             if {@link #doQuery(SearchContext, String[])} wasn't called
     *             prior to this method call;<br>
     *             if there aren't exactly one element in the {@code cmdArgs}
     *             array;<br>
     *             if argument from {@code cmdArgs} array doesn't contain a
     *             parsable integer;<br>
     *             if there is no is no search result with specified index
     */
    private static void doType(SearchContext context, String[] cmdArgs) {
        if (context.getResults().isEmpty())
            throw new IllegalArgumentException("You need to run a 'query' command to get some results first.");
        if (cmdArgs.length != 1)
            throw new IllegalArgumentException("You need to provide one argument for command 'type'.");

        int index = -1;

        try {
            index = Integer.parseInt(cmdArgs[0]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("You need to provide one integer number as a argument.", e);
        }

        Path path = null;
        try {
            path = context.getResult(index).getPath();
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("There is no search result with index " + index + ".", e);
        }

        System.out.println("----------------------------------------------------------------");
        System.out.printf("Document: %s%n", path.toString());
        System.out.println("----------------------------------------------------------------");
        try {
            System.out.println(new String(Files.readAllBytes(path), StandardCharsets.UTF_8));
        } catch (IOException ignore) {
        }
        System.out.println("----------------------------------------------------------------");
    }

    /**
     * Gets the list of all results and outputs it through standard output.
     * 
     * @param context
     *            the context of the search engine
     * @param cmdArgs
     *            the command arguments
     * @throws IllegalArgumentException
     *             if {@link #doQuery(SearchContext, String[])} wasn't called
     *             prior to this method call;<br>
     *             if there aren't exactly zero elements in the {@code cmdArgs}
     *             array
     */
    private static void doResults(SearchContext context, String[] cmdArgs) {
        if (context.getResults().isEmpty())
            throw new IllegalArgumentException("You need to run a 'query' command to get some results first.");
        if (cmdArgs.length != 0)
            throw new IllegalArgumentException("You need to provide zero arguments for command 'results'.");

        int i = 0;
        for (SearchResult result : context.getResults()) {
            System.out.printf("[%2d] (%.4f) %s%n", i++, result.getSimilarity(), result.getPath().toString());
        }
        System.out.println();
    }

    /**
     * Converts a path string to a {@link Path}.
     * <p>
     * If the path string cannot be converted to a Path program exits with
     * status code 2.<br>
     * If the path isn't a path to directory program exits with status code 3.
     * 
     * @param arg
     *            the path string
     * @return the resulting directory Path
     */
    private static Path getDirectoryPath(String arg) {
        Path path = null;

        try {
            path = Paths.get(arg);
        } catch (InvalidPathException e) {
            error("The path you provided cannot be resolved as a path. You provided: " + arg + ".", 2);
        }

        if (!Files.isDirectory(path)) {
            error("The path you provided isn't a directory or doesn't exists. You provided: " + arg + ".", 3);
        }

        return path;
    }

    /**
     * Prints a {@code msg} to the standard error output stream and exists the
     * program with specified {@code status}.
     * 
     * @param msg
     *            the message
     * @param status
     *            the status code
     */
    private static void error(String msg, int status) {
        System.err.println(msg);
        System.exit(status);
    }

}
