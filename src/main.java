public class QuantityMeasurementApp {

    static class Feet {
        private final double value;

        public Feet(double value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Feet feet = (Feet) obj;
            return Double.compare(feet.value, value) == 0;
        }
    }

    static class Inches {
        private final double value;

        public Inches(double value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Inches inches = (Inches) obj;
            return Double.compare(inches.value, value) == 0;
        }
    }

    public static boolean checkFeetEquality(double a, double b) {
        Feet f1 = new Feet(a);
        Feet f2 = new Feet(b);
        return f1.equals(f2);
    }

    public static boolean checkInchesEquality(double a, double b) {
        Inches i1 = new Inches(a);
        Inches i2 = new Inches(b);
        return i1.equals(i2);
    }

    public static void main(String[] args) {

        System.out.println("Feet Equality (1.0 , 1.0): " +
                checkFeetEquality(1.0, 1.0));

        System.out.println("Feet Equality (1.0 , 2.0): " +
                checkFeetEquality(1.0, 2.0));

        System.out.println("Inches Equality (1.0 , 1.0): " +
                checkInchesEquality(1.0, 1.0));

        System.out.println("Inches Equality (1.0 , 2.0): " +
                checkInchesEquality(1.0, 2.0));
    }
}