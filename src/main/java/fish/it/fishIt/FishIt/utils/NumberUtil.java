package fish.it.fishIt.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class NumberUtil {

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#,###.##", DecimalFormatSymbols.getInstance(Locale.US));
    private static final String[] SUFFIXES = new String[]{"", "K", "M", "B", "T", "Q", "QT"};

    public static String formatNumber(double number) {
        return DECIMAL_FORMAT.format(number);
    }

    public static String formatNumberSuffix(double number) {
        if (number < 1000) {
            return String.format("%.0f", number);
        }

        int exp = (int) (Math.log(number) / Math.log(1000));
        char unit = SUFFIXES[exp].charAt(0);
        return String.format("%.1f%c", number / Math.pow(1000, exp), unit);
    }

    public static boolean isInt(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isDouble(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}