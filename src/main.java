import java.util.Objects;

/* =========================
   LENGTH UNIT (UC1–UC8)
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

    public double convertToBaseUnit(double value) {
        return value * conversionFactor;
    }

    public double convertFromBaseUnit(double baseValue) {
        return baseValue / conversionFactor;
    }
}


/* =========================
   LENGTH QUANTITY
   ========================= */

class QuantityLength {

    private static final double EPSILON = 0.01;

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

    public QuantityLength convertTo(LengthUnit targetUnit) {

        double base = unit.convertToBaseUnit(value);
        double converted = targetUnit.convertFromBaseUnit(base);

        return new QuantityLength(round(converted), targetUnit);
    }

    public QuantityLength add(QuantityLength other, LengthUnit targetUnit) {

        double base1 = unit.convertToBaseUnit(value);
        double base2 = other.unit.convertToBaseUnit(other.value);

        double sum = base1 + base2;

        double result = targetUnit.convertFromBaseUnit(sum);

        return new QuantityLength(round(result), targetUnit);
    }

    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) return true;

        if (!(obj instanceof QuantityLength)) return false;

        QuantityLength other = (QuantityLength) obj;

        double base1 = unit.convertToBaseUnit(value);
        double base2 = other.unit.convertToBaseUnit(other.value);

        return Math.abs(base1 - base2) < EPSILON;
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


/* =========================
   WEIGHT UNIT (UC9)
   ========================= */

enum WeightUnit {

    KILOGRAM(1.0),
    GRAM(0.001),
    POUND(0.453592);

    private final double conversionFactor;

    WeightUnit(double conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    public double convertToBaseUnit(double value) {
        return value * conversionFactor;
    }

    public double convertFromBaseUnit(double baseValue) {
        return baseValue / conversionFactor;
    }
}


/* =========================
   WEIGHT QUANTITY
   ========================= */

class QuantityWeight {

    private static final double EPSILON = 0.01;

    private final double value;
    private final WeightUnit unit;

    public QuantityWeight(double value, WeightUnit unit) {

        if (unit == null)
            throw new IllegalArgumentException("Unit cannot be null");

        if (Double.isNaN(value) || Double.isInfinite(value))
            throw new IllegalArgumentException("Invalid value");

        this.value = value;
        this.unit = unit;
    }

    public QuantityWeight convertTo(WeightUnit targetUnit) {

        double base = unit.convertToBaseUnit(value);

        double converted = targetUnit.convertFromBaseUnit(base);

        return new QuantityWeight(round(converted), targetUnit);
    }

    public QuantityWeight add(QuantityWeight other) {
        return add(other, this.unit);
    }

    public QuantityWeight add(QuantityWeight other, WeightUnit targetUnit) {

        double base1 = unit.convertToBaseUnit(value);
        double base2 = other.unit.convertToBaseUnit(other.value);

        double sum = base1 + base2;

        double result = targetUnit.convertFromBaseUnit(sum);

        return new QuantityWeight(round(result), targetUnit);
    }

    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) return true;

        if (!(obj instanceof QuantityWeight)) return false;

        QuantityWeight other = (QuantityWeight) obj;

        double base1 = unit.convertToBaseUnit(value);
        double base2 = other.unit.convertToBaseUnit(other.value);

        return Math.abs(base1 - base2) < EPSILON;
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


/* =========================
   MAIN CLASS
   ========================= */

public class main {

    public static void main(String[] args) {

        System.out.println("===== UC9 Quantity Measurement App =====");

        /* ======================
           LENGTH TESTS
           ====================== */

        System.out.println("---- Length Tests ----");

        System.out.println(
                new QuantityLength(1.0, LengthUnit.FEET)
                        .convertTo(LengthUnit.INCHES)
        );

        System.out.println(
                new QuantityLength(1.0, LengthUnit.FEET)
                        .add(new QuantityLength(12.0, LengthUnit.INCHES), LengthUnit.FEET)
        );

        System.out.println(
                new QuantityLength(36.0, LengthUnit.INCHES)
                        .equals(new QuantityLength(1.0, LengthUnit.YARDS))
        );


        /* ======================
           WEIGHT TESTS
           ====================== */

        System.out.println("\n---- Weight Tests ----");

        // Equality
        System.out.println(
                new QuantityWeight(1.0, WeightUnit.KILOGRAM)
                        .equals(new QuantityWeight(1000.0, WeightUnit.GRAM))
        );

        // Conversion
        System.out.println(
                new QuantityWeight(1.0, WeightUnit.KILOGRAM)
                        .convertTo(WeightUnit.GRAM)
        );

        // Pound to KG
        System.out.println(
                new QuantityWeight(2.0, WeightUnit.POUND)
                        .convertTo(WeightUnit.KILOGRAM)
        );

        // Addition
        System.out.println(
                new QuantityWeight(1.0, WeightUnit.KILOGRAM)
                        .add(new QuantityWeight(1000.0, WeightUnit.GRAM))
        );

        // Addition with target
        System.out.println(
                new QuantityWeight(1.0, WeightUnit.KILOGRAM)
                        .add(new QuantityWeight(1000.0, WeightUnit.GRAM),
                                WeightUnit.GRAM)
        );

        // Pound addition
        System.out.println(
                new QuantityWeight(1.0, WeightUnit.POUND)
                        .add(new QuantityWeight(453.592, WeightUnit.GRAM),
                                WeightUnit.POUND)
        );
    }
}