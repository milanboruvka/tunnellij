package net.boruvka.idea.tunnellij.ui;

import java.awt.BorderLayout;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import net.boruvka.idea.tunnellij.TunnelPlugin;
import net.boruvka.idea.tunnellij.net.Call;
import net.boruvka.idea.tunnellij.net.Tunnel;
import net.boruvka.idea.tunnellij.net.TunnelException;
import net.boruvka.idea.tunnellij.net.TunnelListener;

import com.intellij.openapi.ui.Messages;

/**
 * @author boruvka
 * @since
 */
public class TunnelPanel extends JPanel {

    private CallsPanel list;

    private ControlPanel control;

    private Tunnel tunnel;

    private PortNumberVerifier portNumberVerifier;

    public TunnelPanel() {

        setLayout(new BorderLayout());

        list = new CallsPanel();
        control = new ControlPanel();
        add(list, BorderLayout.CENTER);
        add(control, BorderLayout.SOUTH);

        portNumberVerifier = new PortNumberVerifier();
    }

    public void start() throws Exception {
        Runnable r = new Runnable() {

            public void run() {
                tunnel = new Tunnel(control.getSrcPort(),
                        control.getDestPort(), control.getDestHost());
                try {
                    tunnel.addTunnelListener(list);
                    tunnel.addTunnelListener(control);
                    tunnel.start();

                } catch (TunnelException e) {

                    Messages.showMessageDialog("Error when starting server: "
                            + e.getMessage(), "Error", Messages.getErrorIcon());
                    e.printStackTrace();

                }
            }

        };

        Thread t = new Thread(r);
        t.start();

    }

    public void stop() throws Exception {
        Runnable r = new Runnable() {
            public void run() {
                tunnel.stop();
                // tunnellij.removeTunnelListener(list);
            }
        };
        Thread t = new Thread(r);
        t.start();
        repaint();
    }

    public void clear() {
        list.clear();
    }

    public void clearSelected() {
        list.clearSelected();
    }

    public void wrap() {
        list.wrap();
    }

    public void unwrap() {
        list.unwrap();
    }

    private boolean isRunning = false;

    public boolean isRunning() {
        return isRunning;
    }

    class ControlPanel extends JPanel implements TunnelListener {

        private JPanel subPanelAddress;

        private JTextField srcPort;

        private JTextField destHost;

        private JTextField destPort;

        public ControlPanel() {
            super();
            initComponents();
        }

        protected void initComponents() {
            setLayout(new BorderLayout());

            subPanelAddress = new JPanel();
            subPanelAddress.setBorder(new TitledBorder("properties"));

            srcPort = new JTextField(TunnelPlugin.TunnelConfig.getSourcePort());
            srcPort.setInputVerifier(portNumberVerifier);
            srcPort.setHorizontalAlignment(JTextField.RIGHT);
            srcPort.setColumns(5);

            destPort = new JTextField(TunnelPlugin.TunnelConfig
                    .getDestinationPort());
            destPort.setInputVerifier(portNumberVerifier);
            destPort.setHorizontalAlignment(JTextField.RIGHT);
            destPort.setColumns(5);

            destHost = new JTextField(TunnelPlugin.TunnelConfig
                    .getDestinationString());
            destHost.setHorizontalAlignment(JTextField.RIGHT);
            destHost.setColumns(24);

            subPanelAddress.add(new JLabel("tunnel from localhost:"));
            subPanelAddress.add(srcPort);
            subPanelAddress.add(new JLabel("to "));
            subPanelAddress.add(destHost);
            subPanelAddress.add(new JLabel(":"));
            subPanelAddress.add(destPort);

            add(subPanelAddress, BorderLayout.SOUTH);
        }

        public void setControlPanelEditable(boolean b) {

            TunnelPlugin.PROPERTIES.put(TunnelPlugin.TunnelConfig.DST_HOST,
                    destHost.getText());
            TunnelPlugin.PROPERTIES.put(TunnelPlugin.TunnelConfig.DST_PORT,
                    destPort.getText());
            TunnelPlugin.PROPERTIES.put(TunnelPlugin.TunnelConfig.SRC_PORT,
                    srcPort.getText());

            srcPort.setEditable(b);
            destHost.setEditable(b);
            destPort.setEditable(b);

            srcPort.setEnabled(b);
            destHost.setEnabled(b);
            destPort.setEnabled(b);
        }

        public int getSrcPort() {
            return Integer.parseInt(srcPort.getText());
        }

        public int getDestPort() {
            return Integer.parseInt(destPort.getText());
        }

        public String getDestHost() {
            return destHost.getText();
        }

        public void newCall(Call call) {
            //
        }

        public void endCall(Call call) {
            //
        }

        public void tunnelStarted() {
            isRunning = true;
            setControlPanelEditable(false);
        }

        public void tunnelStopped() {
            isRunning = false;
            setControlPanelEditable(true);
        }

    }

}

class PortNumberVerifier extends InputVerifier {

    public boolean verify(JComponent input) {
        String text = ((JTextField) input).getText();
        try {
            Integer.parseInt(text);
            return true;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return false;
        }
    }

}
