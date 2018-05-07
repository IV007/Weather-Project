package com.neek.tech.weatherapp.weatherama.utilities;

import java.util.regex.Pattern;

/**
 * @author <a href="mailto:utsaloosi@gmail.com">Ivan Utsalo</a>
 */
public class StringUtils {

    private static final String HTML_TAG_PATTERN = "(?i)(<[^\\s]+>)(.+?)(</[^\\s]+>)";


    public static boolean isValidHtml(final String str) {
        Pattern pattern = Pattern.compile(HTML_TAG_PATTERN);
        return pattern.matcher(str).find();
    }
}
