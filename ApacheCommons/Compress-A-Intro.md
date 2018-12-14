# Apache Commons Compress

URL: 

- [x] http://commons.apache.org/proper/commons-compress/
- [x] http://commons.apache.org/proper/commons-compress/examples.html



## General Notes

### Archivers and Compressors

**The compress component** is split into **compressors** and **archivers**. 

- The compress component
  - compressors
  - archivers

While **compressors** (un)compress streams that usually store a single entry, **archivers** deal with archives that contain structured content represented by `ArchiveEntry` instances which in turn usually correspond to single files or directories.

> 分述两者的不同功能

**The compressor formats** supported are `gzip`, `bzip2`, `xz`, `lzma`, `Pack200`, `DEFLATE`, `Brotli`, `DEFLATE64`, `ZStandard` and `Z`, **the archiver formats** are `7z`, `ar`, `arj`, `cpio`, `dump`, `tar` and `zip`. `Pack200` is a special case as it can only compress JAR files.

> 列举compressor formats和archiver formats都有哪些

We currently only provide **read** support for `arj`, `dump`(前两个是archiver formats), `Brotli`, `DEFLATE64` and `Z`(后三个是compressor formats). `arj` can only read uncompressed archives, `7z` can read archives with many compression and encryption algorithms supported by 7z but doesn't support encryption when writing archives.

> 这里应该是说目前仍然存在的功能上的限制

### Buffering

The stream classes all wrap around streams provided by the calling code and they work on them directly without any additional buffering. On the other hand most of them will benefit from buffering so it is highly recommended that users wrap their stream in `Buffered(In|Out)putStreams` before using the Commons Compress API.

> 推荐使用Buffering

### Factories

**Compress** provides **factory methods** to create input/output streams based on **the names of the compressor or archiver format** as well as **factory methods** that try to **guess the format** of an input stream.

To create a compressor writing to a given output by **using the algorithm name**:

```java
CompressorOutputStream gzippedOut = new CompressorStreamFactory()
    .createCompressorOutputStream(CompressorStreamFactory.GZIP, myOutputStream);
```

> 需要注意的是：  
> （1）对于输出流，不能进行guess，需要明确指定算法的名字  
> （2）对于输入流，可以进行guess  
> 上面的代码片段，是输出流； 下面的两段代码是输入流

Make the factory **guess the input format** for **a given archiver stream**:

```java
ArchiveInputStream input = new ArchiveStreamFactory()
    .createArchiveInputStream(originalInput);
```

Make the factory guess the input format for **a given compressor stream**:

```java
CompressorInputStream input = new CompressorStreamFactory()
    .createCompressorInputStream(originalInput);
```

Note that there is no way to detect the `lzma` or `Brotli` formats so only the **two-arg version** of `createCompressorInputStream` can be used. Prior to Compress 1.9 the `.Z` format hasn't been auto-detected either.

> 功能上的限制

### Restricting Memory Usage

Starting with Compress 1.14 `CompressorStreamFactory` has an optional constructor argument that can be used to **set an upper limit of memory** that may be used while decompressing or compressing a stream. As of 1.14 this setting only affects decompressing Z, XZ and LZMA compressed streams.

> 内存使用方面的优化

For the `Snappy` and `LZ4` formats the amount of memory used during compression is directly proportional to the window size.

> 特殊情况

### Statistics

Starting with Compress 1.17 most of the `CompressorInputStream` implementations as well as `ZipArchiveInputStream` and all streams returned by `ZipFile.getInputStream` implement the `InputStreamStatistics` interface. `SevenZFile` provides statistics for the current entry via the `getStatisticsForCurrentEntry` method. This interface can be used to track progress while extracting a stream or to detect potential zip bombs when the compression ration becomes suspiciously large.






















