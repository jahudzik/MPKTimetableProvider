package pl.jahu.mpk.entities;

/**
 * MPK Timetable Parser
 * Created by jahudzik on 2014-09-05.
 */
public class LineNumber implements Comparable<LineNumber> {
    private Integer numeric = -1;
    private String literal;
    private boolean numericOnly;

    public LineNumber(int numeric)  {
        if (numeric < 0 || numeric > 9999) {
            throw new IllegalArgumentException("Incorrect line number: " + Integer.toString(numeric));
        }
        this.numeric = numeric;
        this.literal = Integer.toString(numeric);
        this.numericOnly = true;
    }

    public LineNumber(String literal)  {
        if (literal == null || literal.equals("") || literal.length() > 4) {
            throw new IllegalArgumentException("Incorrect line number: '" + literal + "'");
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
        return (compareTo((LineNumber) obj) == 0);
    }

    @Override
    public String toString() {
        return literal;
    }

}
