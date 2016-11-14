package com.neek.tech.weatherapp.weatherama.utilities;

import java.util.regex.Pattern;

/**
 * Created by ivanutsalo on 11/13/16.
 */

public class StringUtils {

    private static final String HTML_TAG_PATTERN = "(?i)(<[^\\s]+>)(.+?)(</[^\\s]+>)";


    public static boolean isValidHtml(final String str) {
        Pattern pattern = Pattern.compile(HTML_TAG_PATTERN);
        return pattern.matcher(str).find();
    }
}
