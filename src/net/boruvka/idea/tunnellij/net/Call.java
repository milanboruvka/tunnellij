package net.boruvka.idea.tunnellij.net;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import net.boruvka.idea.tunnellij.ui.CallStringFormatter;

/**
 * @author boruvka
 * @since
 */
public class Call {
    private long start;

    private long end = -1;

    private String srcHost;

    private String destHost;

    private int srcPort;

    private int destPort;

    public static final int CMD_LENGTH = 80;

    private ByteArrayOutputStream input;

    private ByteArrayOutputStream output;

    public Call(String srcHost, int srcPort, String destHost, int destPort) {
        this.start = System.currentTimeMillis();
        this.srcHost = srcHost;
        this.srcPort = srcPort;
        this.destHost = destHost;
        this.destPort = destPort;
        this.input = new ByteArrayOutputStream();
        this.output = new ByteArrayOutputStream();
    }

    public OutputStream getOutputLogger() {
        return output;
    }

    public OutputStream getInputLogger() {
        return input;
    }

    public String toString() {
        return CallStringFormatter.format(this);
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public long getStart() {
        return start;
    }

    public long getEnd() {
        return end;
    }

    public String getSrcHost() {
        return srcHost;
    }

    public String getDestHost() {
        return destHost;
    }

    public int getSrcPort() {
        return srcPort;
    }

    public int getDestPort() {
        return destPort;
    }

    public ByteArrayOutputStream getInput() {
        return input;
    }

    public ByteArrayOutputStream getOutput() {
        return output;
    }

}
