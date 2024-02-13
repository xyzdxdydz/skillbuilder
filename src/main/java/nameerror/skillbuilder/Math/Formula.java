package nameerror.skillbuilder.Math;

import java.util.HashSet;
import java.util.Set;

public class Formula {

    public static Set<Double> solveQuadratic(double a, double b, double c, double shift) {
        // A(x - h)^2 + B(x-h) + C = 0
        Set<Double> ans = new HashSet<>();
        double decr = Math.sqrt((b * b) - 4 * a * c);

        if (Double.isNaN(decr)) {
            return ans;
        }
        ans.add((-b + decr) / (2 * a) + shift);
        ans.add((-b - decr) / (2 * a) + shift);
        return ans;
    }

    public static double Pow2(double a) { return a * a; }

    public static int Pow2(int a) {
        return a * a;
    }

}
