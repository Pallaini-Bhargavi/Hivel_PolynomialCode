import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class hivelcode {

    // Convert a number represented in an arbitrary base to BigInteger
    static BigInteger toBig(String s, int base) {
        BigInteger result = BigInteger.ZERO;
        BigInteger b = BigInteger.valueOf(base);

        for (char ch : s.toCharArray()) {
            int digit = Character.isDigit(ch) ? (ch - '0') : (Character.toLowerCase(ch) - 'a' + 10);
            result = result.multiply(b).add(BigInteger.valueOf(digit));
        }
        return result;
    }

    // Extract JSON object belonging to a numbered key
    static String fetchObject(String json, String keyName) {
        int p = json.indexOf(keyName);
        if (p < 0) return null;

        int left = json.indexOf("{", p);
        int right = json.indexOf("}", left);
        if (left < 0 || right < 0) return null;

        return json.substring(left, right + 1);
    }

    // Extract quoted field value, e.g. "value":"123"
    static String readField(String block, String field) {
        String marker = "\"" + field + "\"";
        int pos = block.indexOf(marker);
        if (pos < 0) return null;

        int colon = block.indexOf(":", pos);
        int q1 = block.indexOf("\"", colon + 1);
        int q2 = block.indexOf("\"", q1 + 1);

        return block.substring(q1 + 1, q2);
    }

    public static void main(String[] args) throws Exception {

        // Collect full JSON input
        BufferedReader rd = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder text = new StringBuilder();
        String temp;

        while ((temp = rd.readLine()) != null) {
            text.append(temp.trim());
        }
        String json = text.toString();

        // Retrieve n and k
        int n = Integer.parseInt(json.replaceAll(".*\"n\"\\s*:\\s*(\\d+).*", "$1"));
        int k = Integer.parseInt(json.replaceAll(".*\"k\"\\s*:\\s*(\\d+).*", "$1"));
        int degree = k - 1;

        // Store decoded root values
        List<BigInteger> decodedRoots = new ArrayList<>();

        for (int i = 1; i <= n; i++) {
            String key = "\"" + i + "\":";
            String obj = fetchObject(json, key);

            if (obj == null) continue;

            String baseStr = readField(obj, "base");
            String valStr = readField(obj, "value");

            int base = Integer.parseInt(baseStr);
            decodedRoots.add(toBig(valStr, base));
        }

        // Sort roots before constructing polynomial
        Collections.sort(decodedRoots);

        // Compute constant coefficient C = product of (-r1)...(-rm)
        BigInteger C = BigInteger.ONE;

        for (int i = 0; i < degree; i++) {
            C = C.multiply(decodedRoots.get(i).negate());
        }

        System.out.println("Constant term C:");
        System.out.println(C);
        System.out.println();

        // Polynomial expansion using iterative coefficient building
        BigInteger[] poly = new BigInteger[degree + 1];
        Arrays.fill(poly, BigInteger.ZERO);
        poly[0] = BigInteger.ONE;  // Start with polynomial = 1

        for (int idx = 0; idx < degree; idx++) {
            BigInteger root = decodedRoots.get(idx);
            BigInteger[] updated = new BigInteger[degree + 1];
            Arrays.fill(updated, BigInteger.ZERO);

            for (int d = 0; d <= idx; d++) {
                updated[d + 1] = updated[d + 1].add(poly[d]);  // multiply by x
                updated[d] = updated[d].add(poly[d].negate().multiply(root)); // multiply by (-root)
            }
            poly = updated;
        }

        System.out.println("Polynomial Coefficients (from constant term upward):");
        for (int i = 0; i <= degree; i++) {
            System.out.println("a" + i + " = " + poly[i]);
        }

        System.out.println();
        System.out.println("Polynomial P(x):");

        for (int i = degree; i >= 0; i--) {
            System.out.print("(" + poly[i] + ")*x^" + i);
            if (i > 0) System.out.print(" + ");
        }

        System.out.println();
    }
}
