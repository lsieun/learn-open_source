# Archivers

URL: 

- http://commons.apache.org/proper/commons-compress/examples.html

## 1. Unsupported Features

Many of the supported formats have developed different dialects and extensions and some formats allow for features (not yet) supported by Commons Compress.

这里的主要意思是各种不同的Archiver算法在不断的发展过程中，时不时的会加进一些新的特性，但是这些新特性可能是Commons Compress不支持的，所以为了解决这样一个问题，它提供的解决方法是使用`ArchiveInputStream.canReadEntryData(final ArchiveEntry archiveEntry)`进行检验。

The `ArchiveInputStream` class provides a method `canReadEntryData` that will return `false` if Commons Compress can detect that an archive uses a feature that is not supported by the current implementation. If it returns `false` you **should not try to read the entry** but **skip over** it.

## 2. Entry Names

All archive formats provide meta data about the individual archive entries via instances of `ArchiveEntry` (or rather subclasses of it). When reading from an archive the information provided the `getName` method is **the raw name** as stored inside of the archive. There is no guarantee the name represents a relative file name or even a valid file name on your target operating system at all. You should **double check the outcome** when you try to create file names from entry names.

这里讲述使用`ArchiveEntry.getName()`时，要特别注意的地方。

## 3. Common Extraction Logic

Apart from `7z`, all formats provide a subclass of `ArchiveInputStream` that can be used to create an archive. For 7z, `SevenZFile` provides a similar API that does not represent a stream as our implementation requires random access to the input and cannot be used for general streams. The ZIP implementation can benefit a lot from random access as well, see the [zip page](http://commons.apache.org/proper/commons-compress/zip.html#ZipArchiveInputStream_vs_ZipFile) for details.

Assuming you want to **extract an archive to a target directory** you'd call `getNextEntry`, verify the entry can be read, construct a sane(明智的；理智的) file name from the entry's name, create a File and write all contents to it - here `IOUtils.copy` may come handy. You do so for every entry until `getNextEntry` returns null.

> `IOUtils.copy`是位于当前JAR包中的。

A skeleton might look like:

```java
File targetDir = ...
try (ArchiveInputStream i = ... create the stream for your format, use buffering...) {
    ArchiveEntry entry = null;
    while ((entry = i.getNextEntry()) != null) {
        if (!i.canReadEntryData(entry)) {
            // log something?
            continue;
        }
        String name = fileName(targetDir, entry);
        File f = new File(name);
        if (entry.isDirectory()) {
            if (!f.isDirectory() && !f.mkdirs()) {
                throw new IOException("failed to create directory " + f);
            }
        } else {
            File parent = f.getParentFile();
            if (!parent.isDirectory() && !parent.mkdirs()) {
                throw new IOException("failed to create directory " + parent);
            }
            try (OutputStream o = Files.newOutputStream(f.toPath())) {
                IOUtils.copy(i, o);
            }
        }
    }
}
```

where the hypothetical `fileName` method is written by you and provides the absolute name for the file that is going to be written on disk. Here you should perform checks that ensure the resulting file name actually is a valid file name on your operating system or belongs to a file inside of `targetDir` when using the entry's name as input.

If you want to combine **an archive format** with **a compression format** - like when reading a "tar.gz" file - you wrap the `ArchiveInputStream` around `CompressorInputStream` for example:

```java
try (InputStream fi = Files.newInputStream(Paths.get("my.tar.gz"));
     InputStream bi = new BufferedInputStream(fi);
     InputStream gzi = new GzipCompressorInputStream(bi);
     ArchiveInputStream o = new TarArchiveInputStream(gzi)) {
}
```

## 4. Common Archival Logic

Apart from 7z, all formats that support writing provide a subclass of `ArchiveOutputStream` that can be used to create an archive. For 7z, `SevenZOutputFile` provides a similar API that does not represent a stream as our implementation requires random access to the output and cannot be used for general streams. The `ZipArchiveOutputStream` class will benefit from random access as well but can be used for non-seekable streams - but not all features will be available and the archive size might be slightly bigger, see the zip page for details.

Assuming you want to **add a collection of files to an archive**, you can first use `createArchiveEntry` for **each file**. In general this will set **a few flags** (usually *the last modified time*, *the size* and *the information whether this is a file or directory*) based on the File instance. Alternatively you can create the `ArchiveEntry` subclass corresponding to your format directly. Often you may want to **set additional flags** like *file permissions* or *owner information* before adding the entry to the archive.

Next you use `putArchiveEntry` in order to add the entry and then start using `write` to add the content of the entry - here `IOUtils.copy` may come handy. Finally you invoke `closeArchiveEntry` once you've written all content and before you add the next entry.

Once all entries have been added you'd invoke `finish` and finally `close` the stream.

A skeleton might look like:

```java
Collection<File> filesToArchive = ...
try (ArchiveOutputStream o = ... create the stream for your format ...) {
    for (File f : filesToArchive) {
        // maybe skip directories for formats like AR that don't store directories
        ArchiveEntry entry = o.createArchiveEntry(f, entryName(f));
        // potentially add more flags to entry
        o.putArchiveEntry(entry);
        if (f.isFile()) {
            try (InputStream i = Files.newInputStream(f.toPath())) {
                IOUtils.copy(i, o);
            }
        }
        o.closeArchiveEntry();
    }
    o.finish();
}
```

where the hypothetical `entryName` method is written by you and provides the name for the entry as it is going to be written to the archive.

If you want to combine **an archive format** with **a compression format** - like when creating a "tar.gz" file - you wrap the `ArchiveOutputStream` around a `CompressorOutputStream` for example:

```java
try (OutputStream fo = Files.newOutputStream(Paths.get("my.tar.gz"));
     OutputStream gzo = new GzipCompressorOutputStream(fo);
     ArchiveOutputStream o = new TarArchiveOutputStream(gzo)) {
}
```





















