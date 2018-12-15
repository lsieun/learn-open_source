package lsieun.compress;

import java.io.File;

public class Deflate {

    public static void main(String[] args) {
        String workingDir = System.getProperty("user.dir");
        String sourceFile = workingDir + File.separator + "pom.xml";
        String compressedFile = workingDir + File.separator + "target/deflate-compress.txt";
        String uncompressedFile = workingDir + File.separator + "target/deflate-uncompress.txt";

        toCompress(sourceFile, compressedFile);
        toUnCompress(compressedFile, uncompressedFile);
    }

    public static void toCompress(String source, String target) {
        CommonCompressor.toCompress(source, target, "deflate");
    }

    public static void toUnCompress(String source, String target) {
        CommonCompressor.toUnCompress(source, target, "deflate");
    }

}
