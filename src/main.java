public class main {

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

    public static void main(String[] args) {

        // testEquality_SameValue
        Feet f1 = new Feet(1.0);
        Feet f2 = new Feet(1.0);
        System.out.println("Same Value Test: " + f1.equals(f2));

        // testEquality_DifferentValue
        Feet f3 = new Feet(2.0);
        System.out.println("Different Value Test: " + f1.equals(f3));

        // testEquality_NullComparison
        System.out.println("Null Comparison Test: " + f1.equals(null));

        // testEquality_SameReference
        Feet f4 = f1;
        System.out.println("Same Reference Test: " + f1.equals(f4));

        // testEquality_NonNumericInput
        String nonNumeric = "test";
        System.out.println("Non Numeric Test: " + f1.equals(nonNumeric));
    }
}