package net.boruvka.idea.tunnellij;

import java.util.PropertyResourceBundle;

/**
 * @author boruvka
 * @since
 */
public class TunnelBundle {

    private static java.util.ResourceBundle bundle;

    public static java.util.ResourceBundle getBundle() {
        if (bundle == null)
            bundle = PropertyResourceBundle
                    .getBundle("net/boruvka/idea/tunnellij/TunnelPlugin");
        return bundle;
    }
}
