# Maven

```xml
<!-- Used to work with the older excel file format - `.xls` -->
<!-- https://mvnrepository.com/artifact/org.apache.poi/poi -->
<dependency>
    <groupId>org.apache.poi</groupId>
    <artifactId>poi</artifactId>
    <version>${poi.version}</version>
</dependency>

<!-- Used to work with the newer excel file format - `.xlsx` -->
<!-- https://mvnrepository.com/artifact/org.apache.poi/poi-ooxml -->
<dependency>
    <groupId>org.apache.poi</groupId>
    <artifactId>poi-ooxml</artifactId>
    <version>${poi.version}</version>
</dependency>
```

The first dependency `poi` is used to work with the old Microsoft’s binary file format for excel. These file formats have `.xls` extension.

The second dependency `poi-ooxml` is used to work with the newer XML based file format. These file formats have `.xlsx` extension.

