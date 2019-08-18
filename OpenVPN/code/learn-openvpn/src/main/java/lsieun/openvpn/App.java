package lsieun.openvpn;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Scanner;

import lsieun.openvpn.utils.ExecuteCommand;

public class App {
    public static void main(String[] args) throws IOException {
//        String dir = "/home/liusen/Workspace/vpn";
//        String[] commands = new String[] {"sudo", "openvpn", "nl272.nordvpn.com.tcp443.ovpn"};
//        //String[] commands = new String[] {"/bin/bash", "-c", "sudo", "openvpn", "nl272.nordvpn.com.tcp443.ovpn", " 2>&1"};
////        String[] commands = new String[] {"sudo", "ifconfig"};
////        String[] commands = new String[] {"ls", "."};
//        String str = ExecuteCommand.run(dir, commands);
//        System.out.println(str);
//        boolean flag = runWithPrivileges();
//        System.out.println("flag = " + flag);


        ProcessBuilder builder = new ProcessBuilder("sudo", "openvpn", "nl272.nordvpn.com.tcp443.ovpn");
        builder.directory(new File("/home/liusen/Workspace/vpn"));
        Process process = builder.start();

        OutputStream stdin = process.getOutputStream(); // <- Eh?
        InputStream stdout = process.getInputStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(stdout));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stdin));

        writer.write("lsieun2015@gmail.com");
        writer.flush();
        writer.write("liusen1026");
        writer.flush();
        writer.close();

        Scanner scanner = new Scanner(stdout);
        while (scanner.hasNextLine()) {
            System.out.println(scanner.nextLine());
        }
    }

    public static boolean runWithPrivileges() {
        InputStreamReader input;
        OutputStreamWriter output;

        try {
            //Create the process and start it.
            //Process pb = new ProcessBuilder(new String[]{"/bin/bash", "-c", "/usr/bin/sudo -S /bin/cat /etc/sudoers 2>&1"}).start();
            Process pb = new ProcessBuilder(new String[]{"sudo", "openvpn", "nl272.nordvpn.com.tcp443.ovpn"}).directory(new File("/home/liusen/Workspace/vpn")).start();
            //Process pb = new ProcessBuilder(new String[]{"/bin/bash", "-c", "/usr/bin/sudo -S /bin/cat /etc/sudoers 2>&1"}).start();
            output = new OutputStreamWriter(pb.getOutputStream());
            input = new InputStreamReader(pb.getInputStream());

            int bytes, tryies = 0;
            char buffer[] = new char[1024];
            while ((bytes = input.read(buffer, 0, 1024)) != -1) {
                if(bytes == 0)
                    continue;
                //Output the data to console, for debug purposes
                String data = String.valueOf(buffer, 0, bytes);
                System.out.println(data);
                // Check for password request
                if (data.contains("library versions")) {
                    // Here you can request the password to user using JOPtionPane or System.console().readPassword();
                    // I'm just hard coding the password, but in real it's not good.
                    char password[] = new char[]{'l','s','i','e', 'u', 'n'};
                    output.write(password);
                    output.write('\n');
                    output.flush();
                    // erase password data, to avoid security issues.
                    Arrays.fill(password, '\0');
                    tryies++;
                }
                System.out.println("Hello");
            }

            return tryies < 3;
        } catch (IOException ex) {
        }

        return false;
    }

}
