package lsieun.archive;

import java.io.File;

public class TargzArchive {

    public static void main(String[] args) {
        String workingDir = System.getProperty("user.dir");
        String sourceDir = workingDir + File.separator + "src";
        String target = workingDir + File.separator + "target/targz/my.tar.gz";
        String outDir = workingDir + File.separator + "target/targz/out";

        toArchive(target, sourceDir);
        toExtract(target, outDir);
    }

    public static void toArchive(String filename, String filepath) {
        CommonArchive.toArchive("tar","gz", filename, filepath);
    }

    public static void toExtract(String filename, String filepath) {
        CommonArchive.toExtract("tar", "gz", filename, filepath);

    }

}
