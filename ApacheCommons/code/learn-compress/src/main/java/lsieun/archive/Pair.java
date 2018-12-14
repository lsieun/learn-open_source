package lsieun.archive;

import java.io.File;

public class Pair {
    private String name;
    private File file;

    public Pair() {
    }

    public Pair(String name, File file) {
        this.name = name;
        this.file = file;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return "Pair{" +
                "name='" + name + '\'' +
                ", file=" + file.toURI() +
                '}';
    }
}
