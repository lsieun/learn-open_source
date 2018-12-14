package lsieun.archive;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.compressors.CompressorException;
import org.apache.commons.compress.compressors.CompressorStreamFactory;
import org.apache.commons.compress.utils.IOUtils;

public class CommonArchive {

    public static void toArchive(final String archiveName, String filename, String filepath) {
        toArchive(archiveName, null, filename, filepath);
    }

    public static void toArchive(final String archiveName, final String compressorName, String filename, String filepath) {
        if(filename == null || "".equals(filename)) return;

        List<Pair> pairs = PairUtils.listPairs(filepath, true);
        if(pairs == null || pairs.size() < 1) return;

        File archiveFile = new File(filename);
        File dirFile = archiveFile.getParentFile();
        if(!dirFile.exists()) {
            dirFile.mkdirs();
        }

        OutputStream out = null;
        ArchiveOutputStream archiveOut = null;

        try {
            out = new FileOutputStream(filename);
            out = new BufferedOutputStream(out);
            if (compressorName != null && !"".equals(compressorName)) {
                out = new CompressorStreamFactory().createCompressorOutputStream(compressorName, out);
            }
            archiveOut = new ArchiveStreamFactory().createArchiveOutputStream(archiveName, out);

            toArchive(archiveOut, pairs);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ArchiveException e) {
            e.printStackTrace();
        } catch (CompressorException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(archiveOut);
            IOUtils.closeQuietly(out);
        }

    }

    public static void toArchive(ArchiveOutputStream archiveOut, List<Pair> pairs) throws IOException {
        for(Pair p : pairs) {
            String name = p.getName();
            File file = p.getFile();
            ArchiveEntry entry = archiveOut.createArchiveEntry(file, name);
            archiveOut.putArchiveEntry(entry);
            if(file.isFile()) {
                try(
                        InputStream fi = new FileInputStream(file);
                        InputStream in = new BufferedInputStream(fi);
                ) {
                    IOUtils.copy(in, archiveOut);
                }
            }
            archiveOut.closeArchiveEntry();
        }
        archiveOut.finish();

    }

    public static void toExtract(final String archiveName, String filename, String filepath) {
        toExtract(archiveName, null, filename, filepath);
    }

    public static void toExtract(final String archiveName, final String compressorName, String filename, String filepath) {
        if(archiveName == null || "".equals(archiveName)) return;
        File archiveFile = new File(filename);
        if(!archiveFile.exists()) return;

        File dirFile = new File(filepath);
        if(!dirFile.exists()) {
            dirFile.mkdirs();
        }

        InputStream in = null;
        ArchiveInputStream archiveIn = null;
        try {
            in = new FileInputStream(filename);
            in = new BufferedInputStream(in);
            if (compressorName != null && !"".equals(compressorName)) {
                in = new CompressorStreamFactory().createCompressorInputStream(compressorName, in);
            }
            archiveIn = new ArchiveStreamFactory().createArchiveInputStream(archiveName, in);
            toExtract(archiveIn, dirFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (CompressorException e) {
            e.printStackTrace();
        } catch (ArchiveException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(archiveIn);
            IOUtils.closeQuietly(in);
        }
    }

    public static void toExtract(ArchiveInputStream archiveIn, File dirFile) throws IOException {
        ArchiveEntry entry = null;
        while((entry = archiveIn.getNextEntry()) != null) {
            if(!archiveIn.canReadEntryData(entry)) {
                continue;
            }

            String name = entry.getName();
            File f = new File(dirFile, name);
            if (entry.isDirectory()) {
                if (!f.isDirectory() && !f.mkdirs()) {
                    throw new IOException("failed to create directory " + f);
                }
            } else {
                File parent = f.getParentFile();
                if (!parent.isDirectory() && !parent.mkdirs()) {
                    throw new IOException("failed to create directory " + parent);
                }
                try (
                        OutputStream fo = new FileOutputStream(f);
                        OutputStream out = new BufferedOutputStream(fo);
                ) {
                    IOUtils.copy(archiveIn, out);
                }
            }
        }

    }

}
