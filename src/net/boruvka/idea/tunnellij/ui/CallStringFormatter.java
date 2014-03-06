package net.boruvka.idea.tunnellij.ui;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.boruvka.idea.tunnellij.net.Call;

/**
 * @author boruvka
 * @since
 */
public class CallStringFormatter {

    static SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss.SS");

    public synchronized static String format(Call call) {

        StringBuffer sb = new StringBuffer();
        sb.append("[");
        sb.append(dateFormat.format(new Date(call.getStart())));

        if (call.getOutput() != null) {
            byte[] arr = call.getOutput().toByteArray();
            if (arr != null) {
                int len = (arr.length > Call.CMD_LENGTH) ? Call.CMD_LENGTH
                        : arr.length;
                byte[] text = new byte[len];
                System.arraycopy(arr, 0, text, 0, len);
                sb.append("] " + new String(text) + "...");
            }
        }

        sb.append("; from ");
        sb.append(call.getSrcHost());
        sb.append(":");
        sb.append(call.getSrcPort());
        sb.append(" to ");
        sb.append(call.getDestHost());
        sb.append(":");
        sb.append(call.getDestPort());
        sb.append(", output: ");

        sb.append((call.getOutput() == null) ? " ? B" : call.getOutput()
                .toByteArray().length
                + " B");

        sb.append(", input: ");
        sb.append((call.getInput() == null) ? " ? B" : call.getInput()
                .toByteArray().length
                + " B");

        if (call.getEnd() != -1) {
            sb.append(", duration: " + (call.getEnd() - call.getStart())
                    + " ms");
        }

        return sb.toString();
    }
}
