import java.util.Objects;

enum LengthUnit {

    FEET(1.0),
    INCHES(1.0 / 12),
    YARDS(3.0),
    CENTIMETERS(0.0328084);

    private final double conversionFactor;

    LengthUnit(double conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    public double toBaseUnit(double value) {
        return value * conversionFactor;
    }

    public double fromBaseUnit(double baseValue) {
        return baseValue / conversionFactor;
    }
}


class QuantityLength {

    private final double value;
    private final LengthUnit unit;

    public QuantityLength(double value, LengthUnit unit) {

        if (unit == null)
            throw new IllegalArgumentException("Unit cannot be null");

        if (!Double.isFinite(value))
            throw new IllegalArgumentException("Invalid value");

        this.value = value;
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public LengthUnit getUnit() {
        return unit;
    }


    // UC6 Addition
    public QuantityLength add(QuantityLength other) {

        if (other == null)
            throw new IllegalArgumentException("Other quantity cannot be null");

        return add(other, this.unit);
    }


    // UC7 Addition with Target Unit
    public QuantityLength add(QuantityLength other, LengthUnit targetUnit) {

        if (other == null)
            throw new IllegalArgumentException("Other quantity cannot be null");

        if (targetUnit == null)
            throw new IllegalArgumentException("Target unit cannot be null");

        double thisBase = unit.toBaseUnit(value);
        double otherBase = other.unit.toBaseUnit(other.value);

        double sumBase = thisBase + otherBase;

        double result = targetUnit.fromBaseUnit(sumBase);

        return new QuantityLength(result, targetUnit);
    }


    @Override
    public boolean equals(Object obj) {

        if (this == obj) return true;

        if (!(obj instanceof QuantityLength)) return false;

        QuantityLength other = (QuantityLength) obj;

        double thisBase = unit.toBaseUnit(value);
        double otherBase = other.unit.toBaseUnit(other.value);

        double epsilon = 0.0001;

        return Math.abs(thisBase - otherBase) < epsilon;
    }


    @Override
    public int hashCode() {
        return Objects.hash(unit.toBaseUnit(value));
    }


    @Override
    public String toString() {
        return "Quantity(" + value + ", " + unit + ")";
    }
}



public class main {

    public static void main(String[] args) {

        System.out.println("UC7 Addition with Target Unit\n");

        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(12.0, LengthUnit.INCHES);

        System.out.println(q1.add(q2, LengthUnit.FEET));
        System.out.println(q1.add(q2, LengthUnit.INCHES));
        System.out.println(q1.add(q2, LengthUnit.YARDS));


        QuantityLength q3 = new QuantityLength(1.0, LengthUnit.YARDS);
        QuantityLength q4 = new QuantityLength(3.0, LengthUnit.FEET);

        System.out.println(q3.add(q4, LengthUnit.YARDS));


        QuantityLength q5 = new QuantityLength(36.0, LengthUnit.INCHES);
        QuantityLength q6 = new QuantityLength(1.0, LengthUnit.YARDS);

        System.out.println(q5.add(q6, LengthUnit.FEET));


        QuantityLength q7 = new QuantityLength(2.54, LengthUnit.CENTIMETERS);
        QuantityLength q8 = new QuantityLength(1.0, LengthUnit.INCHES);

        System.out.println(q7.add(q8, LengthUnit.CENTIMETERS));


        QuantityLength q9 = new QuantityLength(5.0, LengthUnit.FEET);
        QuantityLength q10 = new QuantityLength(0.0, LengthUnit.INCHES);

        System.out.println(q9.add(q10, LengthUnit.YARDS));


        QuantityLength q11 = new QuantityLength(5.0, LengthUnit.FEET);
        QuantityLength q12 = new QuantityLength(-2.0, LengthUnit.FEET);

        System.out.println(q11.add(q12, LengthUnit.INCHES));
    }
}