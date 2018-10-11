# Nexus Installation and Run

## 1. Installation

URL： https://help.sonatype.com/repomanager3/installation

### 1.1 Extract the archive

On a Unix machine common practice is to use `/opt`.

```bash
$ sudo tar -zxvf nexus-3.13.0-01-unix.tar.gz -C /opt/
$ cd /opt/ && ls -l
```

Output:

```txt
total 0
drwxr-xr-x. 9 root root 163 Oct 11 10:00 nexus-3.13.0-01
drwxr-xr-x. 3 root root  20 Oct 11 10:00 sonatype-work
```

The extraction process creates **two** sibling directories: **an application directory** and **a data directory**, sometimes called the "Sonatype work" directory.

---

### 1.2 Create symbolic link

Create **a version-irrelevant symbolic link** pointing to the original `nexus-3.13.0-01` directory.

```bash
$ cd /opt/
$ sudo ln -s nexus-3.13.0-01/ nexus
```

---

### 1.3 Nexus Direcotry

```bash
$ tree /opt/nexus -L 1
```

Output:

```txt
/opt/nexus
├── bin
├── deploy
├── etc
├── lib
├── NOTICE.txt
├── OSS-LICENSE.txt
├── PRO-LICENSE.txt
├── public
└── system

6 directories, 3 files
```

The `bin` folder contains **the generic startup scripts** for Unix-like platforms called `nexus`.

```bash
$ ls -l /opt/nexus/bin/
```

Output:

```txt
total 32
drwxr-xr-x. 2 root root  4096 Oct 11 10:00 contrib
-rwxr-xr-x. 1 root root 17269 Jul 12 07:23 nexus
-rw-r--r--. 1 root root    20 Oct 11 10:56 nexus.rc
-rw-r--r--. 1 root root   464 Jul 12 07:23 nexus.vmoptions
```




## 2. Run

### 2.1 Foreground Run

To start the repository manager from application directory in the `bin` folder on a Unix-like platform like Linux use:

```bash
$ NEXUS_HOME="/opt/nexus"
$ cd $NEXUS_HOME/bin
$ sudo ./nexus run
```

Starting the repository manager with the `run` command will leave it running in the current shell and display the log output.

Sample log:

```txt
- Properties:
-   application-host='0.0.0.0'
-   application-port='8081'
-   fabric.etc='/opt/nexus-3.13.0-01/etc/fabric'
-   jetty.etc='/opt/nexus-3.13.0-01/etc/jetty'
-   karaf.base='/opt/nexus-3.13.0-01'
-   karaf.data='/opt/sonatype-work/nexus3'
-   karaf.etc='/opt/nexus-3.13.0-01/etc/karaf'
-   karaf.home='/opt/nexus-3.13.0-01'
-   karaf.instances='/opt/sonatype-work/nexus3/instances'
-   logback.etc='/opt/nexus-3.13.0-01/etc/logback'
-   nexus-args='/opt/nexus-3.13.0-01/etc/jetty/jetty.xml,/opt/nexus-3.13.0-01/etc/jetty/jetty-http.xml,/opt/nexus-3.13.0-01/etc/jetty/jetty-requestlog.xml'
-   nexus-context-path='/'
-   nexus-edition='nexus-pro-edition'
-   nexus-features='nexus-pro-feature'
-   ssl.etc='/opt/nexus-3.13.0-01/etc/ssl'

- Java: 1.8.0_161, OpenJDK 64-Bit Server VM, Oracle Corporation, 25.161-b14
- OS: Linux, 3.10.0-862.el7.x86_64, amd64
- User: root, en, /root
- CWD: /opt/nexus-3.13.0-01
- TMP: /opt/sonatype-work/nexus3/tmp
```

Startup is complete when the log shows the message "**Started Sonatype Nexus**".

```txt
- Started ServerConnector@4e4ee459{HTTP/1.1,[http/1.1]}{0.0.0.0:8081}
- Started @143398ms
- 
-------------------------------------------------

Started Sonatype Nexus OSS 3.13.0-01

-------------------------------------------------
```

In order to shut down the repository manager running via the `run` command, you have to press `CTRL-C`.

### 2.2 Background Run

The `nexus` script can be used to manage the repository manager as **a background application** on OSX and Unix with the `start`, `stop`, `restart`, `force-reload` and `status` commands. 

```bash
$ sudo ./nexus start
$ sudo ./nexus stop
$ sudo ./nexus restart
$ sudo ./nexus force-reload
$ sudo ./nexus status
```

## 3. Run as a Service

URL: https://help.sonatype.com/repomanager3/installation/run-as-a-service

When installing **Nexus Repository Manager** for production usage it has to be configured it to run as a **service**, so it restarts after the server reboots. It is good practice to run that service or daemon as **a specific user** that has **only the required access rights**.

The following sections provide instructions for configuring the service manually. Independent of the operating system the steps are:

- **Create operating system user** with limited access rights dedicated to run the repository manager as a service
- **Configure the service** and ensure it starts as part of the operating system boot process

### 3.1 Create a dedicated user for Nexus

For security purposes, you need to create a dedicated non-root user "nexus" who belongs to the "nexus" group:

```bash
$ sudo groupadd nexus
$ sudo useradd -s /bin/nologin -g nexus -d /opt/nexus nexus
```

In this fashion, you created a user "nexus" who belongs to the group "nexus". You cannot use this user account to log into the system. The home directory is `/opt/nexus`, which is where the Nexus program will reside.

```bash
$ cd /opt/
$ sudo chown -R nexus:nexus nexus-3.13.0-01/ sonatype-work/ nexus
$ ls -l
```

Output:

```txt
total 0
lrwxrwxrwx. 1 nexus nexus  16 Oct 11 10:02 nexus -> nexus-3.13.0-01/
drwxr-xr-x. 9 nexus nexus 163 Oct 11 10:00 nexus-3.13.0-01
drwxr-xr-x. 3 nexus nexus  20 Oct 11 10:00 sonatype-work
```

---

### 3.2 Configure Nexus User

```bash
$ cd $NEXUS_HOME/bin
$ sudo vim nexus.rc
```

In `nexus.rc` assign the user "`nexus`" between the quotes:

```txt
run_as_user="nexus"
```

---

### 3.3 Create nexus service

This example is a script that uses `systemd` to run the repository manager service. Create a file called `nexus.service`. Add the following contents, then save the file in the  `/etc/systemd/system/` directory:

```bash
$ cd /etc/systemd/system/
$ sudo vim nexus.service
```

Content:

```txt
[Unit]
Description=nexus service
After=network.target
  
[Service]
Type=forking
ExecStart=/opt/nexus/bin/nexus start
ExecStop=/opt/nexus/bin/nexus stop
User=nexus
Restart=on-abort
  
[Install]
WantedBy=multi-user.target
```

---

### 3.4 Activate nexus service

Activate the service with the following commands:

```bash
sudo systemctl daemon-reload
sudo systemctl enable nexus.service
sudo systemctl start nexus.service
```

After starting the service for any Linux-based operating systems, verify that the service started successfully.

```bash
tail -f /opt/sonatype-work/nexus3/log/nexus.log
```

The `tail` command verifies that the service has been started successfully. If successful, you should see a message notifying you that it is listening for `HTTP`.

---

### 3.5 Firewall

In order to test Nexus in a web browser, you need to modify the firewall rules:

```bash
sudo firewall-cmd --permanent --zone=public --add-port=8081/tcp
sudo firewall-cmd --reload
```

Then, you can test your installation of Nexus by visiting the following URL from a web browser:

```txt
http://[your-server-IP]:8081
```

```txt
Username: admin
Password: admin123
```

---

## 4. Uninstallation

```bash
# Service
$ sudo systemctl disable nexus.service
$ sudo systemctl stop nexus.service
$ sudo rm -rf /etc/systemd/system/nexus.service

# User
$ sudo userdel -r nexus

# directory
$ cd /opt/
$ sudo rm -rf nexus nexus-3.13.0-01/ sonatype-work/
```

