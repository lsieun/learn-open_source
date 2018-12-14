package lsieun.archive;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class PairUtils {

    public static void main(String[] args) {
        String workingDir = System.getProperty("user.dir");
        String targetDir = workingDir + File.separator + "src";

        List<Pair> list = listPairs(targetDir, true);
        for(Pair p : list) {
            System.out.println(p);
        }
    }

    public static List<Pair> listPairs(String dir, boolean recursive) {
        if(dir == null || "".equals(dir)) return null;
        File f = new File(dir);
        return listPairs(f, recursive);
    }

    public static List<Pair> listPairs(File dir, boolean recursive) {
        if(dir == null) return null;
        String path = dir.getPath();
        System.out.println("Working Directory: " + path + "\n");
        int pos = path.length() + 1;

        List<File> fileList = listFiles(dir, recursive);
        if(fileList == null || fileList.size() < 1) return null;

        List<Pair> list = new ArrayList<Pair>();
        for(File f : fileList) {
            String filepath = f.getPath();
            String name = filepath.substring(pos);
            Pair pair = new Pair(name, f);
            list.add(pair);
        }
        return list;
    }

    public static List<File> listFiles(File dir, boolean recursive) {
        if (dir == null) return null;
        File[] files = dir.listFiles();
        if(files == null || files.length == 0) return null;

        List<File> fileList = new LinkedList<File>(Arrays.asList(files));

        if(recursive) {
            for(File f : files) {
                if(f.isDirectory()) {
                    List<File> list = listFiles(f, recursive);
                    if(list == null || list.size() < 1) continue;
                    for(File item : list) {
                        fileList.add(item);
                    }
                }
            }
        }
        return fileList;
    }

}
