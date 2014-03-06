package net.boruvka.idea.tunnellij.ui;

import javax.swing.ImageIcon;

/**
 * @author boruvka
 * @since
 */
public class Icons {

    // my icons
    public static final ImageIcon ICON_WATCH = getIcon("icons/tunnellij.png");

    public static final ImageIcon ICON_REMOVE = getIcon("icons/remove.png");

    public static final ImageIcon ICON_CLEAR = getIcon("icons/removeall.png");

    public static final ImageIcon ICON_WRAP = getIcon("icons/wrap.png");

    // Intellij icons
    public static final ImageIcon ICON_START = getIcon("actions/execute.png");

    public static final ImageIcon ICON_STOP = getIcon("actions/suspend.png");

    public static final ImageIcon ICON_HELP = getIcon("actions/help.png");

    private static ImageIcon getIcon(String file) {
        try {
            java.net.URL url = Icons.class.getResource("/" + file);
            ImageIcon icon = new ImageIcon(url);
            return icon;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Cannot find icon " + file);
            return null;
        }
    }

}
