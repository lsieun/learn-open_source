package lsieun.compress;

import java.io.File;

public class XZ {

    public static void main(String[] args) {
        String workingDir = System.getProperty("user.dir");
        String sourceFile = workingDir + File.separator + "target/tar/my.tar";
        String compressedFile = workingDir + File.separator + "target/tar/my-xz-compress.tar.xz";
        String uncompressedFile = workingDir + File.separator + "target/tar/my-xz-uncompress.tar";

        toCompress(sourceFile, compressedFile);
        toUnCompress(compressedFile, uncompressedFile);
    }

    public static void toCompress(String source, String target) {
        CommonCompressor.toCompress(source, target, "xz");
    }

    public static void toUnCompress(String source, String target) {
        CommonCompressor.toUnCompress(source, target, "xz");
    }

}
