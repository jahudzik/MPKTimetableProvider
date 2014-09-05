package pl.jahu.mpk.entities;

import pl.jahu.mpk.validators.exceptions.NoDataProvidedException;

/**
 * MPK Timetable Parser
 * Created by jahudzik on 2014-09-05.
 */
public class LineNumber implements Comparable<LineNumber> {
    private Integer numeric = -1;
    private String literal;

    public LineNumber(String literal) throws NoDataProvidedException {
        if (literal == null || literal.equals("")) {
            throw new NoDataProvidedException(LineNumber.class.getCanonicalName() + " constructor: no line nubmer provided");
        }
        this.literal = literal;
        try {
            this.numeric = Integer.parseInt(literal);
        } catch (NumberFormatException e) {
            String strippedLiteral = literal.replaceAll("[\\D]", "");
            this.numeric = ("".equals(strippedLiteral)) ? -1 : Integer.parseInt(strippedLiteral);
        }
    }

    public int getNumeric() {
        return numeric.intValue();
    }

    public String getLiteral() {
        return literal;
    }

    @Override
    public int compareTo(LineNumber other) {
        if (numeric == -1 || other.numeric == -1) {
            return literal.compareTo(other.literal);
        }
        int numDiff = numeric.compareTo(other.numeric);
        return (numDiff == 0) ? literal.compareTo(other.literal) : numDiff;
    }

}
