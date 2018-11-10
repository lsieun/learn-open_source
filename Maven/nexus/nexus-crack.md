# 



https://www.52pojie.cn/thread-592545-1-1.html


createLicense
installLicense
uninstallLicense
verifyLicense
validateFeature

2018-10-15 13:48:39,053+0800 INFO  [FelixStartLevel] *SYSTEM org.sonatype.nexus.bootstrap.ConfigurationBuilder 

```txt
- Properties:
-   application-host='0.0.0.0'
-   application-port='8081'
-   fabric.etc='/home/liusen/workdir/dummy/nexus-3.14.0-04/etc/fabric'
-   jetty.etc='/home/liusen/workdir/dummy/nexus-3.14.0-04/etc/jetty'
-   karaf.base='/home/liusen/workdir/dummy/nexus-3.14.0-04'
-   karaf.data='/home/liusen/workdir/dummy/sonatype-work/nexus3'
-   karaf.etc='/home/liusen/workdir/dummy/nexus-3.14.0-04/etc/karaf'
-   karaf.home='/home/liusen/workdir/dummy/nexus-3.14.0-04'
-   karaf.instances='/home/liusen/workdir/dummy/sonatype-work/nexus3/instances'
-   logback.etc='/home/liusen/workdir/dummy/nexus-3.14.0-04/etc/logback'
-   nexus-args='/home/liusen/workdir/dummy/nexus-3.14.0-04/etc/jetty/jetty.xml,/home/liusen/workdir/dummy/nexus-3.14.0-04/etc/jetty/jetty-http.xml,/home/liusen/workdir/dummy/nexus-3.14.0-04/etc/jetty/jetty-requestlog.xml'
-   nexus-context-path='/'
-   nexus-edition='nexus-pro-edition'
-   nexus-features='nexus-pro-feature'
-   ssl.etc='/home/liusen/workdir/dummy/nexus-3.14.0-04/etc/ssl'
```

2018-10-15 13:48:39,055+0800 INFO  [FelixStartLevel] *SYSTEM org.sonatype.nexus.bootstrap.Launcher 
```txt
- Java: 1.8.0_181, Java HotSpot(TM) 64-Bit Server VM, Oracle Corporation, 25.181-b13
- OS: Linux, 4.18.12-200.fc28.x86_64, amd64
- User: liusen, en, /home/liusen
- CWD: /home/liusen/workdir/dummy/nexus-3.14.0-04
- TMP: /home/liusen/workdir/dummy/sonatype-work/nexus3/tmp
```


2018-10-15 13:48:39,258+0800 INFO  [jetty-main-1] *SYSTEM org.sonatype.nexus.bootstrap.osgi.BootstrapListener 

- Initializing
- Loading OSS Edition
- Installing: nexus-oss-edition/3.14.0.04
- Installed: nexus-oss-edition/3.14.0.04


ConfigurationBuilder
karaf.home='/home/liusen/workdir/dummy/nexus-3.14.0-04'
karaf.base='/home/liusen/workdir/dummy/nexus-3.14.0-04'
karaf.etc='/home/liusen/workdir/dummy/nexus-3.14.0-04/etc/karaf'
karaf.data='/home/liusen/workdir/dummy/sonatype-work/nexus3'
karaf.instances='/home/liusen/workdir/dummy/sonatype-work/nexus3/instances'


jar -uvf license-bundle-1.4.3.jar org/sonatype/licensing/trial/internal/DefaultTrialLicenseManager.class

jar -uvf nexus-bootstrap-3.14.0-04.jar org/sonatype/nexus/bootstrap/osgi/BootstrapListener.class

find . -name "license-bundle-1.4.3.jar"
find . -name "nexus-bootstrap-3.14.0-04.jar"



Feature->FeatureSet->License

LicenseContent->CustomLicenseContent

License->LicenseKey
CustomLicenseContent->->LicenseKey

TrialLicenseManager->DefaultTrialLicenseManager

LicenseKey->TrialLicenseManager
LicenseKeyRequest->TrialLicenseManager
LicensingException->TrialLicenseManager
Feature->TrialLicenseManager
TrialLicenseParam->TrialLicenseManager

https://blog.csdn.net/akon_vm/article/details/7426155

http://blog.sina.com.cn/s/blog_a5f093b401018spj.html

http://www.cnblogs.com/littlehb/p/3511689.html

- `codeguard.licensing.eui`: `cn.lsieun.licensing.KeyStoreParam`
- `codeguard.licensing.omj`: `cn.lsieun.licensing.CipherParam`
- `codeguard.licensing.pej`: `cn.lsieun.licensing.Param`
- `codeguard.licensing.rnn`: `cn.lsieun.licensing.AbstractLicense`












