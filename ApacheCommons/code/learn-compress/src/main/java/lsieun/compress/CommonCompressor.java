package lsieun.compress;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.compress.compressors.CompressorException;
import org.apache.commons.compress.compressors.CompressorStreamFactory;
import org.apache.commons.compress.compressors.deflate.DeflateCompressorInputStream;
import org.apache.commons.compress.compressors.deflate.DeflateCompressorOutputStream;
import org.apache.commons.compress.utils.IOUtils;

public class CommonCompressor {
    public static void toCompress(String source, String target, final String compressorName) {
        InputStream in = null;
        OutputStream out = null;
        try {
            in = new FileInputStream(source);
            in = new BufferedInputStream(in);
            out = new FileOutputStream(target);
            out = new BufferedOutputStream(out);
            out = new CompressorStreamFactory().createCompressorOutputStream(compressorName, out);
            IOUtils.copy(in, out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CompressorException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(out);
        }
    }

    public static void toUnCompress(String source, String target, final String compressorName) {
        InputStream in = null;
        OutputStream out = null;
        try {
            in = new FileInputStream(source);
            in = new BufferedInputStream(in);
            in = new CompressorStreamFactory().createCompressorInputStream(compressorName, in);
            out = new FileOutputStream(target);
            out = new BufferedOutputStream(out);
            IOUtils.copy(in, out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CompressorException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(out);
        }

    }
}
