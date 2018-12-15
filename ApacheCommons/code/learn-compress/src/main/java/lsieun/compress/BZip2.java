package lsieun.compress;

import java.io.File;

public class BZip2 {

    public static void main(String[] args) {
        String workingDir = System.getProperty("user.dir");
        String sourceFile = workingDir + File.separator + "target/tar/my.tar";
        String compressedFile = workingDir + File.separator + "target/tar/my-bzip-compress.tar.bzip2";
        String uncompressedFile = workingDir + File.separator + "target/tar/my-bzip-uncompress.tar";

        toCompress(sourceFile, compressedFile);
        toUnCompress(compressedFile, uncompressedFile);
    }

    public static void toCompress(String source, String target) {
        CommonCompressor.toCompress(source, target, "bzip2");
    }

    public static void toUnCompress(String source, String target) {
        CommonCompressor.toUnCompress(source, target, "bzip2");
    }

}
