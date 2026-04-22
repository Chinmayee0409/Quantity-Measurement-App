import java.util.Objects;

/* =========================
   LENGTH UNIT ENUM
   ========================= */

enum LengthUnit {

    FEET(1.0),
    INCHES(1.0 / 12.0),
    YARDS(3.0),
    CENTIMETERS(1.0 / 30.48);

    private final double conversionFactor;

    LengthUnit(double conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    public double convertToFeet(double value) {
        return value * conversionFactor;
    }
}


/* =========================
   QUANTITY LENGTH CLASS
   ========================= */

class QuantityLength {

    private static final double EPSILON = 0.0001;

    private final double value;
    private final LengthUnit unit;

    public QuantityLength(double value, LengthUnit unit) {

        if (unit == null)
            throw new IllegalArgumentException("Unit cannot be null");

        if (Double.isNaN(value) || Double.isInfinite(value))
            throw new IllegalArgumentException("Invalid value");

        this.value = value;
        this.unit = unit;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;

        if (!(obj instanceof QuantityLength))
            return false;

        QuantityLength other = (QuantityLength) obj;

        double baseValue1 = unit.convertToFeet(value);
        double baseValue2 = other.unit.convertToFeet(other.value);

        return Math.abs(baseValue1 - baseValue2) < EPSILON;
    }

    @Override
    public int hashCode() {
        return Objects.hash(unit.convertToFeet(value));
    }

    @Override
    public String toString() {
        return "Quantity(" + value + ", " + unit + ")";
    }
}


/* =========================
   MAIN CLASS
   ========================= */

public class main {

    public static void main(String[] args) {

        System.out.println("===== UC4 Extended Unit Support =====");

        // Yard to Feet
        System.out.println(
                new QuantityLength(1.0, LengthUnit.YARDS)
                        .equals(new QuantityLength(3.0, LengthUnit.FEET))
        );

        // Yard to Inches
        System.out.println(
                new QuantityLength(1.0, LengthUnit.YARDS)
                        .equals(new QuantityLength(36.0, LengthUnit.INCHES))
        );

        // Yard to Yard
        System.out.println(
                new QuantityLength(2.0, LengthUnit.YARDS)
                        .equals(new QuantityLength(2.0, LengthUnit.YARDS))
        );

        // CM to CM
        System.out.println(
                new QuantityLength(2.0, LengthUnit.CENTIMETERS)
                        .equals(new QuantityLength(2.0, LengthUnit.CENTIMETERS))
        );

        // CM to Inches
        System.out.println(
                new QuantityLength(1.0, LengthUnit.CENTIMETERS)
                        .equals(new QuantityLength(0.393701, LengthUnit.INCHES))
        );

        // Complex comparison
        System.out.println(
                new QuantityLength(2.0, LengthUnit.YARDS)
                        .equals(new QuantityLength(72.0, LengthUnit.INCHES))
        );
    }
}