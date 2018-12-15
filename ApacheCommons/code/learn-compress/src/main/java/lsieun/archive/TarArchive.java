package lsieun.archive;

import java.io.File;

public class TarArchive {

    public static void main(String[] args) {
        String workingDir = System.getProperty("user.dir");
        String sourceDir = workingDir + File.separator + "src";
        String target = workingDir + File.separator + "target/tar/my.tar";
        String outDir = workingDir + File.separator + "target/tar/out";

        toJar(target, sourceDir);
        toExtract(target, outDir);
    }

    public static void toJar(String filename, String path) {
        CommonArchive.toArchive("tar", filename, path);
    }

    public static void toExtract(String filename, String filepath) {
        CommonArchive.toExtract("tar", filename, filepath);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
