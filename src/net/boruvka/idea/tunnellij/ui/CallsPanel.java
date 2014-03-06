package net.boruvka.idea.tunnellij.ui;

import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.ByteArrayOutputStream;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.boruvka.idea.tunnellij.net.Call;
import net.boruvka.idea.tunnellij.net.TunnelListener;

/**
 * @author boruvka
 * @since
 */
public class CallsPanel extends JPanel implements TunnelListener {

    private JList list;

    private DefaultListModel model;

    private ViewersPanel viewers;

    private JSplitPane splitPaneTopBottom;

    public static final int DIVIDER_SIZE = 2;

    public CallsPanel() {

        setBackground(UIManager.getColor("Tree.textBackground"));
        model = new DefaultListModel();
        list = new JList(model);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        viewers = new ViewersPanel();
        list.addListSelectionListener(new CallsListSelectionListener(viewers));

        list.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_DELETE) {
                    clearSelected();
                }
            }
        });

        setLayout(new BorderLayout());
        initComponents();
    }

    protected void initComponents() {
        list.setBackground(UIManager.getColor("Tree.textBackground"));
        list.setVisibleRowCount(3);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(new JScrollPane(list), BorderLayout.CENTER);
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(viewers, BorderLayout.CENTER);

        splitPaneTopBottom = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPaneTopBottom.setDividerLocation(0.20d);
        splitPaneTopBottom.setResizeWeight(0.20d);
        splitPaneTopBottom.setDividerSize(DIVIDER_SIZE);

        splitPaneTopBottom.add(topPanel, JSplitPane.TOP);
        splitPaneTopBottom.add(bottomPanel, JSplitPane.BOTTOM);

        add(splitPaneTopBottom, BorderLayout.CENTER);
    }

    public void tunnelStarted() {
        // nothing yet
    }

    public void tunnelStopped() {
        // nothing yet
    }

    public synchronized void newCall(Call call) {
        model.addElement(call);
    }

    public synchronized void endCall(Call call) {
        if (list.isVisible()) {
            list.repaint();
            viewers.repaint();
        }

    }

    public void wrap() {
        viewers.wrap();
    }

    public void unwrap() {
        viewers.unwrap();
    }

    public synchronized void clear() {
        model.clear();
    }

    public synchronized void clearSelected() {
        int index = list.getSelectedIndex();
        if (index != -1) {
            model.removeElementAt(index);
        }
    }

}

class CallsListSelectionListener implements ListSelectionListener {

    private ViewersPanel v;

    public CallsListSelectionListener(ViewersPanel v) {
        this.v = v;
    }

    public void valueChanged(ListSelectionEvent e) {
        JList list = (JList) e.getSource();
        Call call = (Call) list.getSelectedValue();

        if (call != null)
            v.view(call);
        else
            v.clear();

    }
}

class ViewersPanel extends JPanel {

    private JTextArea left;

    private JTextArea right;

    private JScrollPane leftScroll;

    private JScrollPane rightScroll;

    private JSplitPane splitPaneLeftRight;

    public ViewersPanel() {
        initComponents();
    }

    protected void initComponents() {
        setLayout(new BorderLayout());

        setBackground(UIManager.getColor("Tree.textBackground"));

        left = new JTextArea();
        right = new JTextArea();

        left.setEditable(true);
        right.setEditable(true);

        left.setBackground(UIManager.getColor("Tree.textBackground"));
        right.setBackground(UIManager.getColor("Tree.textBackground"));

        leftScroll = new JScrollPane(left);
        rightScroll = new JScrollPane(right);

        splitPaneLeftRight = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPaneLeftRight.setDividerSize(CallsPanel.DIVIDER_SIZE);
        splitPaneLeftRight.setDividerLocation(0.50d);
        splitPaneLeftRight.setResizeWeight(0.50d);

        splitPaneLeftRight.add(leftScroll, JSplitPane.LEFT);
        splitPaneLeftRight.add(rightScroll, JSplitPane.RIGHT);

        add(splitPaneLeftRight, BorderLayout.CENTER);
    }

    public void view(Call call) {

        boolean asBytes = false;
        if (call == null)
            return;

        ByteArrayOutputStream leftBaos = (ByteArrayOutputStream) call
                .getOutputLogger();
        if (leftBaos == null)
            return;

        left.setText("");

        if (!asBytes) {
            left.setText(leftBaos.toString());

        } else {
            // TODO

            byte[] bytes = leftBaos.toByteArray();
            for (int i = 0; i < bytes.length; i++) {
                byte b = bytes[i];
                String s = Integer.toHexString(b).toUpperCase();
                if (s.length() == 1)
                    s = "0" + s;
                left.append(s);
            }
        }
        left.setCaretPosition(0);

        ByteArrayOutputStream rightBaos = ((ByteArrayOutputStream) call
                .getInputLogger());
        if (rightBaos == null)
            return;
        right.setText(rightBaos.toString());
        right.setCaretPosition(0);
    }

    public void wrap() {
        left.setLineWrap(true);
        left.setWrapStyleWord(true);
        right.setLineWrap(true);
        right.setWrapStyleWord(true);
    }

    public void unwrap() {
        left.setLineWrap(false);
        right.setLineWrap(false);
    }

    public void clear() {
        left.setText("");
        right.setText("");
    }

}
