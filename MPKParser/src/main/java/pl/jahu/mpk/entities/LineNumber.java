package pl.jahu.mpk.entities;

/**
 * MPK Timetable Parser
 * Created by jahudzik on 2014-09-05.
 */
public final class LineNumber implements Comparable<LineNumber> {

    private final Integer numeric;
    private final String value;

    public LineNumber(int numericValue)  {
        if (numericValue < 0 || numericValue > 9999) {
            throw new IllegalArgumentException("Incorrect line number: " + Integer.toString(numericValue));
        }
        this.numeric = numericValue;
        this.value = Integer.toString(numericValue);
    }

    public LineNumber(String value)  {
        if (value == null || value.equals("") || value.length() > 4) {
            throw new IllegalArgumentException("Incorrect line number: '" + value + "'");
        }
        this.value = value;

        if (value.matches("\\d+")) {
            this.numeric = Integer.parseInt(value);
        } else {
            // remove all non-digits
            String strippedLiteral = value.replaceAll("[\\D]", "");
            this.numeric = ("".equals(strippedLiteral)) ? -1 : Integer.parseInt(strippedLiteral);
        }
    }

    public int getNumeric() {
        return numeric;
    }

    public String getValue() {
        return value;
    }

    @Override
    public int compareTo(LineNumber other) {
        boolean startWithNumberMe = Character.isDigit(value.charAt(0));
        boolean startWithNumverOther = Character.isDigit(other.value.charAt(0));

        if (startWithNumberMe && startWithNumverOther) {
            // both start with numbers, but may have string suffixes - compare numbers, if equal compare strings
            int numDiff = numeric.compareTo(other.numeric);
            return (numDiff == 0) ? value.compareTo(other.value) : numDiff;
        } else {
            if (!startWithNumberMe && !startWithNumverOther) {
                // both start with literals - compare strings
                return value.compareTo(other.value);
            } else {
                // one starts with number, second with letter - number is smaller than literal value
                return (startWithNumberMe) ? -1 : 1;
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        return (obj == this) || (obj instanceof LineNumber && value.equals(((LineNumber) obj).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return value;
    }

}
