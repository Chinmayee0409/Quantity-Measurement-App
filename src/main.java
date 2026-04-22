import java.util.Objects;

public class main {

    enum LengthUnit {

        FEET(1.0),
        INCHES(1.0 / 12.0),
        YARDS(3.0),
        CENTIMETERS(0.0328084);

        private final double conversionFactor;

        LengthUnit(double conversionFactor) {
            this.conversionFactor = conversionFactor;
        }

        public double getConversionFactor() {
            return conversionFactor;
        }
    }

    static class QuantityLength {

        private final double value;
        private final LengthUnit unit;

        public QuantityLength(double value, LengthUnit unit) {
            if (!Double.isFinite(value)) {
                throw new IllegalArgumentException("Invalid value");
            }

            this.value = value;
            this.unit = Objects.requireNonNull(unit, "Unit cannot be null");
        }

        public double getValue() {
            return value;
        }

        public LengthUnit getUnit() {
            return unit;
        }

        public QuantityLength convertTo(LengthUnit targetUnit) {
            double convertedValue = convert(this.value, this.unit, targetUnit);
            return new QuantityLength(convertedValue, targetUnit);
        }

        public static double convert(double value, LengthUnit source, LengthUnit target) {

            if (!Double.isFinite(value)) {
                throw new IllegalArgumentException("Invalid value");
            }

            if (source == null || target == null) {
                throw new IllegalArgumentException("Units cannot be null");
            }

            double baseValue = value * source.getConversionFactor();
            return baseValue / target.getConversionFactor();
        }

        private double toBaseUnit() {
            return value * unit.getConversionFactor();
        }

        @Override
        public boolean equals(Object obj) {

            if (this == obj) return true;

            if (!(obj instanceof QuantityLength)) return false;

            QuantityLength other = (QuantityLength) obj;

            double epsilon = 0.0001;

            return Math.abs(this.toBaseUnit() - other.toBaseUnit()) < epsilon;
        }

        @Override
        public int hashCode() {
            return Double.hashCode(toBaseUnit());
        }

        @Override
        public String toString() {
            return value + " " + unit;
        }
    }

    public static void demonstrateLengthConversion(double value,
                                                   LengthUnit from,
                                                   LengthUnit to) {

        double result = QuantityLength.convert(value, from, to);

        System.out.println(value + " " + from + " = " + result + " " + to);
    }

    public static void demonstrateLengthConversion(QuantityLength length,
                                                   LengthUnit to) {

        QuantityLength converted = length.convertTo(to);

        System.out.println(length + " = " + converted);
    }

    public static void demonstrateLengthEquality(QuantityLength a,
                                                 QuantityLength b) {

        System.out.println(a + " equals " + b + " : " + a.equals(b));
    }

    public static void main(String[] args) {

        demonstrateLengthConversion(1.0, LengthUnit.FEET, LengthUnit.INCHES);

        demonstrateLengthConversion(3.0, LengthUnit.YARDS, LengthUnit.FEET);

        demonstrateLengthConversion(36.0, LengthUnit.INCHES, LengthUnit.YARDS);

        demonstrateLengthConversion(1.0, LengthUnit.CENTIMETERS, LengthUnit.INCHES);

        QuantityLength length = new QuantityLength(3.0, LengthUnit.YARDS);

        demonstrateLengthConversion(length, LengthUnit.INCHES);

        QuantityLength l1 = new QuantityLength(12.0, LengthUnit.INCHES);

        QuantityLength l2 = new QuantityLength(1.0, LengthUnit.FEET);

        demonstrateLengthEquality(l1, l2);
    }
}