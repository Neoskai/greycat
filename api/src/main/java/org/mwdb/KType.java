package org.mwdb;

/**
 * Defines the constants used in mwDB.
 */
public class KType {

    /**
     * Primitive Types
     */
    public static final int BOOL = 1;
    public static final int STRING = 2;
    public static final int LONG = 3;
    public static final int INT = 4;
    public static final int DOUBLE = 5;

    /**
     * Primitive Arrays
     * SHOULD NOT BE USED OUTSIDE THE CORE IMPLEMENTATION.
     */
    public static final int DOUBLE_ARRAY = 6;
    public static final int LONG_ARRAY = 7;
    public static final int INT_ARRAY = 8;

    /**
     * Primitive Maps
     * SHOULD NOT BE USED OUTSIDE THE CORE IMPLEMENTATION.
     */
    public static final int LONG_LONG_MAP = 9;
    public static final int LONG_LONG_ARRAY_MAP = 10;
    public static final int STRING_LONG_MAP = 11;

}
