/*
 * Thanks to tkelly910 for this pretty damn awesome time parser.
 */
package net.loadingchunks.plugins.GuardWolf;

public class Numbers {

    public static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isNumber(String string) {
        try {
            Double.parseDouble(string);
        } catch (Throwable e) {
            return false;
        }
        return true;
    }
}
