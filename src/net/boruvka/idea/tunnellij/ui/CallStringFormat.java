package net.boruvka.idea.tunnellij.ui;

import java.util.Locale;

/**
 * @author boruvka
 * @since
 */
public class CallStringFormat extends java.text.MessageFormat {

    public CallStringFormat(String pattern) {
        super(pattern);
    }

    public CallStringFormat(String pattern, Locale locale) {
        super(pattern, locale);
    }

}
