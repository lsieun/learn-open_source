package lsieun.openvpn.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Arrays;

public class ExecuteCommand {
    public static String run(String dir, String[] commands) {
        StringBuffer output = new StringBuffer();
        output.append("Run Command: " + Arrays.toString(commands) + "\n");

        Process p;
        BufferedReader br = null;
        try {
            ProcessBuilder builder = new ProcessBuilder(commands);
            builder = builder.directory(new File(dir));
            p = builder.start();
            p.waitFor();
            br = new BufferedReader(new InputStreamReader(p.getInputStream(), Charset.defaultCharset()));

            String line = null;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                output.append(line + "\n");
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            if(br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    // do nothing
                }
            }
        }

        return output.toString();
    }
}
