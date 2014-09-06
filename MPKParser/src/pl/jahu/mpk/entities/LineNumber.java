package pl.jahu.mpk.entities;

import pl.jahu.mpk.validators.exceptions.UnsupportedLineNumberException;

/**
 * MPK Timetable Parser
 * Created by jahudzik on 2014-09-05.
 */
public class LineNumber implements Comparable<LineNumber> {
    private Integer numeric = -1;
    private String literal;
    private boolean numericOnly;

    public LineNumber(int numeric) throws UnsupportedLineNumberException {
        if (numeric < 0) {
            throw new UnsupportedLineNumberException(Integer.toString(numeric));
        }
        this.numeric = numeric;
        this.literal = Integer.toString(numeric);
        this.numericOnly = true;
    }

    public LineNumber(String literal) throws UnsupportedLineNumberException {
        if (literal == null || literal.equals("")) {
            throw new UnsupportedLineNumberException(literal);
        }
        this.literal = literal;
        try {
            this.numeric = Integer.parseInt(literal);
            this.numericOnly = true;
        } catch (NumberFormatException e) {
            String strippedLiteral = literal.replaceAll("[\\D]", "");
            this.numeric = ("".equals(strippedLiteral)) ? -1 : Integer.parseInt(strippedLiteral);
            this.numericOnly = false;
        }
    }

    public int getNumeric() {
        return numeric;
    }

    public String getLiteral() {
        return literal;
    }

    public boolean isNumericOnly() {
        return numericOnly;
    }

    @Override
    public int compareTo(LineNumber other) {
        boolean startWithNumberMe = Character.isDigit(literal.charAt(0));
        boolean startWithNumverOther = Character.isDigit(other.literal.charAt(0));

        if (startWithNumberMe && startWithNumverOther) {
            // both start with numbers, but may have string suffixes - compare numbers, if equal compare strings
            int numDiff = numeric.compareTo(other.numeric);
            return (numDiff == 0) ? literal.compareTo(other.literal) : numDiff;
        } else {
            if (!startWithNumberMe && !startWithNumverOther) {
                // both start with literals - compare strings
                return literal.compareTo(other.literal);
            } else {
                // one starts with number, second with literal - number is smaller than literal
                return (startWithNumberMe) ? -1 : 1;
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof LineNumber) {
            return (compareTo((LineNumber) obj) == 0);
        }
        if (obj instanceof Integer) {
            return (numericOnly && numeric == ((Integer) obj).intValue());
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return literal;
    }

}
