# Encrypting credentials in settings.xml

```xml
<settings>
    <localRepository/>
    <interactiveMode/>
    <usePluginRegistry/>
    <offline/>
    <pluginGroups/>
    <servers/> <!-- 本文是讲这里 -->
    <mirrors/> <!-- 还有这里 -->
    <proxies/>
    <profiles/>
    <activeProfiles/>
</settings>
```

Maven keeps confidential data such as passwords in `settings.xml`. For example, the `passwords` for **the proxy server** and **the repository** are kept in cleartext. 

```xml
<proxy>
    <id>internal_proxy</id>
    <active>true</active>
    <protocol>http</protocol>
    <username>proxyuser</username>
    <password>proxypass</password>
    <host>proxy.host.net</host>
    <port>80</port>
    <nonProxyHosts>local.net|some.host.com</nonProxyHosts>
</proxy>
```

```xml
<server>
    <id>central</id>
    <username>my_username</username>
    <password>my_password</password>
</server>
```

> More details about encrypting Maven passwords can be found at http://maven.apache.org/guides/mini/guide-encryption.html.


## 加密过程

Keeping confidential data in configuration files in cleartext is a security threat that must be avoided. Maven provides a way to encrypt configuration data in `settings.xml`, which is as follows:

1. First, we need to create a master encryption key by using the following command:

```bash
$ mvn -emp mymasterpassword
{lJ1MrCQRnngHIpSadxoyEKyt2zIGbm3Yl0ClKdTtRR6TleNaEfGOEoJaxNcdMr+G}
```

2. With the output from the previous command, we need to create a file called `settings-security.xml` under `USER_HOME/.m2/` and add the encrypted master password there as shown here:

```xml
<settingsSecurity>
    <master>
        {lJ1MrCQRnngHIpSadxoyEKyt2zIGbm3Yl0ClKdTtRR6TleNaEfGOEoJaxNcdMr+G}
    </master>
</settingsSecurity>
```

3. Once the master password is configured properly, we can start encrypting rest of the confidential data in `settings.xml`. Let's see how to encrypt **the server password**. First, we need to generate the encrypted password for the cleartext one using the following command. Note that, earlier we used `emp` (**encrypt master password**) and now we are using `ep` (**encrypt password**):

```bash
$ mvn -ep my_password

{PbYw8YaLb3cHA34/5EdHzoUsmmw/u/nWOwb9e+x6Hbs=}
```

4. Copy the value of the encrypted password and replace the corresponding value in the `settings.xml` file, as shown here:

```xml
<server>
    <id>central</id>
    <username>my_username</username>
    <password>
        {PbYw8YaLb3cHA34/5EdHzoUsmmw/u/nWOwb9e+x6Hbs=}
    </password>
</server>
```

5. If you are still concerned about keeping the encrypted master key in the computer itself, use the following approach to remove it from the computer and take it with you in a USB stick. However, the disadvantage in this approach is that to trigger a build, Maven always looks for your USB stick, and this will prevent any scheduled online builds. To read the master key from the USB stick, use the following configuration in `settings-security.xml` under `USER_HOME/.m2/`:

```xml
<settingsSecurity>
    <relocation>
        /Volumes/MyUSBPEN/settings-security.xml
    </relocation>
</settingsSecurity>
```

## 加密算法

For any inquisitive mind, there remains a question. How does Maven encrypt the password? What is the key used to encrypt the master password and where does Maven keep it?

Maven uses **AES 128** with the **PBE SHA-256** algorithm for encryption. Password-Based Encryption (PBE) is a way of performing symmetric key encryption using a password or a passphrase. Once Maven gets **the master password in cleartext**, it will calculate the salted hash against it using the SHA256 algorithm. This will be performed for a few iterations to end up with **the encrypted master key**. Even though we call it encrypted, it is not really encrypted using another key.

If you run the following command multiple times against the same cleartext password, you will end up with different encrypted passwords each time. The reason is that each time you run the command, Maven generates **a random salt value** and uses this to derive the encrypted master key:

```bash
$ mvn -emp mymasterpassword
```

To encrypt confidential data in `settings.xml`, Maven uses this master key along with the **AES 128-bits symmetric-key encryption algorithm**. Anyone with access to the master key will be able to decrypt all the encrypted passwords kept in `settings.xml`.

How does Maven find what to decrypt from the `settings.xml` file? If you keep the data in the `settings.xml` file in the `{xxxxx}` format, Maven will try to decrypt it using **the master key**.

