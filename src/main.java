import java.util.Objects;

enum LengthUnit {

    FEET(1.0),
    INCHES(1.0 / 12.0),
    YARDS(3.0),
    CENTIMETERS(1.0 / 30.48);

    private final double conversionFactor;

    LengthUnit(double conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    public double getConversionFactor() {
        return conversionFactor;
    }

    // Convert to base unit (Feet)
    public double convertToBaseUnit(double value) {
        return value * conversionFactor;
    }

    // Convert from base unit (Feet)
    public double convertFromBaseUnit(double baseValue) {
        return baseValue / conversionFactor;
    }
}


class Quantity {

    private static final double EPSILON = 0.01;

    private final double value;
    private final LengthUnit unit;

    public Quantity(double value, LengthUnit unit) {
        if (unit == null)
            throw new IllegalArgumentException("Unit cannot be null");

        if (Double.isNaN(value) || Double.isInfinite(value))
            throw new IllegalArgumentException("Invalid value");

        this.value = value;
        this.unit = unit;
    }

    public Quantity convertTo(LengthUnit targetUnit) {

        double baseValue = unit.convertToBaseUnit(value);

        double convertedValue = targetUnit.convertFromBaseUnit(baseValue);

        return new Quantity(round(convertedValue), targetUnit);
    }

    public Quantity add(Quantity other, LengthUnit targetUnit) {

        double baseValue1 = this.unit.convertToBaseUnit(this.value);

        double baseValue2 = other.unit.convertToBaseUnit(other.value);

        double sum = baseValue1 + baseValue2;

        double result = targetUnit.convertFromBaseUnit(sum);

        return new Quantity(round(result), targetUnit);
    }

    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;

        if (!(obj instanceof Quantity))
            return false;

        Quantity other = (Quantity) obj;

        double baseValue1 = this.unit.convertToBaseUnit(this.value);

        double baseValue2 = other.unit.convertToBaseUnit(other.value);

        return Math.abs(baseValue1 - baseValue2) < EPSILON;
    }

    @Override
    public int hashCode() {
        return Objects.hash(unit.convertToBaseUnit(value));
    }

    @Override
    public String toString() {
        return "Quantity(" + value + ", " + unit + ")";
    }
}


public class main {

    public static void main(String[] args) {

        System.out.println("=== UC8 Refactored Quantity Measurement App ===");

        // Conversion
        System.out.println(
                new Quantity(1.0, LengthUnit.FEET)
                        .convertTo(LengthUnit.INCHES)
        );

        // Addition
        System.out.println(
                new Quantity(1.0, LengthUnit.FEET)
                        .add(new Quantity(12.0, LengthUnit.INCHES), LengthUnit.FEET)
        );

        // Equality
        System.out.println(
                new Quantity(36.0, LengthUnit.INCHES)
                        .equals(new Quantity(1.0, LengthUnit.YARDS))
        );

        // Addition Yards
        System.out.println(
                new Quantity(1.0, LengthUnit.YARDS)
                        .add(new Quantity(3.0, LengthUnit.FEET), LengthUnit.YARDS)
        );

        // CM to Inches
        System.out.println(
                new Quantity(2.54, LengthUnit.CENTIMETERS)
                        .convertTo(LengthUnit.INCHES)
        );

        // Zero Addition
        System.out.println(
                new Quantity(5.0, LengthUnit.FEET)
                        .add(new Quantity(0.0, LengthUnit.INCHES), LengthUnit.FEET)
        );

        // Unit Direct Conversion
        System.out.println(
                LengthUnit.FEET.convertToBaseUnit(12.0)
        );

        System.out.println(
                LengthUnit.INCHES.convertToBaseUnit(12.0)
        );
    }
}