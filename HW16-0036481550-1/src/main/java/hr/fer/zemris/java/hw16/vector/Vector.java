package hr.fer.zemris.java.hw16.vector;

import java.util.Collection;
import java.util.Objects;
import java.util.function.BinaryOperator;

/**
 * {@code Vector} class represents vectors and has some basic methods for
 * mathematical operations.
 * <p>
 * This class is immutable.
 * 
 * @author Karlo Vrbić
 * @version 1.0
 * @see Vector
 */
public class Vector extends java.util.Vector<Double> {

    /** Serial version UID. */
    private static final long serialVersionUID = 4580560070352994777L;

    /**
     * Constructs an empty {@code Vector} with an initial capacity of ten.
     */
    public Vector() {
        super();
        fillWithZeroes();
    }

    /**
     * Constructs an empty {@code Vector} with the specified initial capacity.
     *
     * @param initialCapacity
     *            the initial capacity of the {@code Vector}
     * @throws IllegalArgumentException
     *             if the specified initial capacity is negative
     */
    public Vector(int initialCapacity) {
        super(initialCapacity);
        fillWithZeroes();
    }

    /**
     * Constructs a {@code Vector} containing the elements of the specified
     * collection, in the order they are returned by the collection's iterator.
     *
     * @param c
     *            the collection whose elements are to be placed into this
     *            {@code Vector}
     * @throws NullPointerException
     *             if the specified collection is null
     */
    public Vector(Collection<? extends Double> c) {
        super(c);
        fillWithZeroes();
    }

    /**
     * Adds vector {@code that} to {@code this} vector and returns the resulting
     * vector.
     * 
     * @param that
     *            the vector to be added
     * @return {@code this + that}
     * @throws NullPointerException
     *             if vector {@code that} is a {@code null} reference
     * @throws VectorArithmeticException
     *             if size, returned by method {@link #size()}, differs between
     *             vector {@code this} and {@code that}
     */
    public Vector add(Vector that) {
        return performOperation(this, that, (n1, n2) -> n1 + n2, "addition");
    }

    /**
     * Adds number {@code num} to {@code this} vector and returns the resulting
     * vector.
     * 
     * @param num
     *            the number to be added
     * @return {@code this + num}
     */
    public Vector add(double num) {
        Vector result = new Vector(this.size());
        for (int i = 0, size = this.size(); i < size; i++) {
            result.set(i, this.get(i) + num);
        }

        return result;
    }

    /**
     * Subtracts vector {@code that} to {@code this} vector and returns the the
     * resulting vector.
     * 
     * @param that
     *            the vector to be subtracted
     * @return {@code this - that}
     * @throws NullPointerException
     *             if vector {@code that} is a {@code null} reference
     * @throws VectorArithmeticException
     *             if size, returned by method {@link #size()}, differs between
     *             vector {@code this} and {@code that}
     */
    public Vector sub(Vector that) {
        return performOperation(this, that, (n1, n2) -> n1 - n2, "subtraction");
    }

    /**
     * Subtracts number {@code num} to {@code this} vector and returns the
     * resulting vector.
     * 
     * @param num
     *            the number to be added
     * @return {@code this - num}
     */
    public Vector sub(double num) {
        return add(-num);
    }

    /**
     * Multiplies vector {@code that} to {@code this} vector and returns the
     * resulting vector.
     * 
     * @param that
     *            the vector to be subtracted
     * @return {@code this * that}
     * @throws NullPointerException
     *             if vector {@code that} is a {@code null} reference
     * @throws VectorArithmeticException
     *             if size, returned by method {@link #size()}, differs between
     *             vector {@code this} and {@code that}
     */
    public double mul(Vector that) {
        if (this.size() != that.size())
            throw new VectorArithmeticException(
                    "Can't perform multiplication on two vectors of different size.");

        Vector mulVector = performOperation(this, that, (n1, n2) -> n1 * n2, "multiplication");
        double result = 0.0;

        for (double num : mulVector) {
            result += num;
        }

        return result;
    }

    /**
     * Multiplies number {@code num} to {@code this} vector and returns the
     * resulting vector.
     * 
     * @param num
     *            the number to be added
     * @return {@code this * num}
     */
    public Vector mul(double num) {
        Vector result = new Vector(this.size());
        for (int i = 0, size = this.size(); i < size; i++) {
            result.set(i, this.get(i) * num);
        }

        return result;
    }

    /**
     * Divides number {@code num} to {@code this} vector and returns the
     * resulting vector.
     * 
     * @param num
     *            the number to be added
     * @return {@code this / num}
     */
    public Vector div(double num) {
        return mul(1.0 / num);
    }

    /**
     * Calculates the cross product between {@code this} and {@code that} vector
     * and returns the resulting vector.
     * 
     * @param that
     *            the vector to calculate cross product with
     * @return {@code this × that}
     * @throws NullPointerException
     *             if vector {@code that} is a {@code null} reference
     * @throws VectorArithmeticException
     *             if size, returned by method {@link #size()}, differs between
     *             vector {@code this} and {@code that}
     */
    public Vector cross(Vector that) {
        Objects.requireNonNull("Can't perform cross product on null reference as a vector.");

        if ((size() != 3) || (that.size() != 3))
            throw new VectorArithmeticException(
                    "Can't perform cross product on two vectors of size that doesn't equal to 3.");

        Vector result = new Vector(3);

        result.set(1, this.get(2) * that.get(3) - this.get(3) * that.get(2));
        result.set(2, this.get(3) * that.get(1) - this.get(1) * that.get(3));
        result.set(3, this.get(1) * that.get(2) - this.get(2) * that.get(1));

        return result;
    }

    /**
     * Negates every element of {@code this} vector and returns the resulting
     * vector.
     * 
     * @return {@code -this}
     */
    public Vector negate() {
        Vector result = new Vector(this.size());
        for (int i = 0, size = this.size(); i < size; i++) {
            result.set(i, -this.get(i));
        }

        return result;
    }

    /**
     * Calculates the magnitude of {@code this} vector and returns it.
     * 
     * @return the magnitude of {@code this} vector
     */
    public double magnitude() {
        double result = 0;
        for (double e : toArray(new Double[1])) {
            result += e * e;
        }
        return Math.sqrt(result);
    }

    /**
     * Performs operation specified with {@code operation parameter} with
     * {@code v1} and {@code v2} vector and returns changed {@code v1} vector.
     * 
     * @param v1
     *            the first vector; result will be stored in it
     * @param v2
     *            the second vector
     * @param operation
     *            the operation to be performed on the vectors
     * @param operationName
     *            the name of the operation; used in exception messages
     * @return {@code this / that}
     * @throws NullPointerException
     *             if {@code v1}, {@code v2}, {@code operation} or
     *             {@code operationName} parameters are a {@code null} reference
     * @throws VectorArithmeticException
     *             if size, returned by method {@link #size()}, differs between
     *             vector {@code this} and {@code that}
     */
    private static Vector performOperation(
            Vector v1,
            Vector v2,
            BinaryOperator<Double> operation,
            String operationName) {
        Objects.requireNonNull(v1, "Cannot perform " + operationName + " on null reference as a vector.");
        Objects.requireNonNull(v2, "Cannot perform " + operationName + " on null reference as a vector.");
        Objects.requireNonNull(operation, "Cannot perform operation with operation as a null reference.");
        Objects.requireNonNull(operationName, "Cannot perform operation with operation name as a null reference.");

        if (v1.size() != v2.size())
            throw new VectorArithmeticException(
                    "Can't perform " + operationName + " on two vectors of different size.");

        Vector result = new Vector(v1.size());
        for (int i = 0, size = v1.size(); i < size; i++) {
            result.set(i, operation.apply(v1.get(i), v2.get(i)));
        }

        return result;
    }

    /**
     * Fills this {@code Vector} with zeros.
     */
    private void fillWithZeroes() {
        for (int i = 0, size = capacity(); i < size; i++) {
            add(i, 0.0);
        }
    }
}
