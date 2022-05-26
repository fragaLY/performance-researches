package by.vk.springbootwebnative.exception.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class MessageBeautifier {

    public static String beautify(String input, String suffix, String prefix) {
        input = input.endsWith(suffix) ? input.substring(0, input.length() - suffix.length()) : input;
        return input.endsWith(prefix) ? input.substring(prefix.length()) : input;
    }
}
