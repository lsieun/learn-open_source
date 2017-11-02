
1、FastDFS简介与安装
2、FastDFS的JavaClient端API操作
3、FastDFS的集群环境搭建
4、FastDFS的应用场景使用

使用简单，安装比较麻烦

1.1、FastDFS简介
FastDFS是一个开源的轻量级的分布式文件系统，它对文件进行管理，功能包括：文件存储、文件同步、文件访问（文件上传、文件下载）等，解决了大容量存储和负载均衡的问题。特别适合以文件为载体的在线服务，如相册网站、视频网站等等。

FastDFS适用的场景以及不适用的场景？


FastDFS软件包下载地址：[https://sourceforge.net/projects/fastdfs/files/](https://sourceforge.net/projects/fastdfs/files/)

FastDFS源码包下载地址：[https://github.com/happyfish100](https://github.com/happyfish100)

单个节点安装，需要安装Tracker、Storage，以及要使用Http访问，需要进行集成Nginx模块。其中，Tracker类似于Storm的Nimbus，负责管理Storage，而Storage是真正存储文件的地方。

FastDFS安装
1、安装之前Linux上要有gcc包：
	yum install make cmake gcc gcc-c++
2、安装libfastcommon
3、安装FastDFS
4、配置跟踪器(tracker)
5、配置存储器（storage）
6、环境搭建完成，可以进行测试环境是否搭建成功
7、跟踪器和存储器安装Nginx，之前已经安装完毕了，我们可以对Nginx进行整合，实现使用浏览器下载文件
8、启动和关闭服务顺序：跟踪器、存储器、Nginx

FastDFS安装手册
一、准备工作（两台机器同时进行）
1、下载软件：https://github.com/happyfish100
2、安装gcc：命令`yum install make cmake gcc gcc-c++`

https://github.com/happyfish100/libfastcommon

二、安装libfastcommon（两台机器同时进行）
1、上传libfastcommon-master.zip到/usr/local/software下
2、进行解压libfastcommon-master.zip
	命令： unzip libfastcommon-master.zip -d /usr/local/fast
3、进入目录： cd /usr/local/fast/libfastcommon-master

4、进行编译和安装
命令：./make.sh
命令：./make.sh install

遇到问题：

	perl: command not found

解决：

	yum -y install perl


注意安装路径：也就是说，我们的libfastcommon默认安装到了/usr/lib64/这个位置 

5、进行软件创建。FastDFS主程序设置的目录为/usr/local/lib/，所以我们需要创建/usr/lib64/下的一些核心执行程序的软连接
命令（好像这个目录默认就存在着）：mkdir /usr/local/lib/
命令： ln -s /usr/lib64/libfastcommon.so /usr/local/lib/libfastcommon.so
（这里确实是两个）
命令：ln -s /usr/lib64/libfastcommon.so /usr/lib/libfastcommon.so
命令：ln -s /usr/lib64/libfdfclient.so /usr/local/lib/libfdfclient.so
命令：ln -s /usr/lib64/libfdsclient.so /usr/lib/libfdfsclient.so

	ln -s /usr/lib64/libfastcommon.so /usr/local/lib/libfastcommon.so
	ln -s /usr/lib64/libfdfsclient.so /usr/local/lib/libfdfsclient.so
	ln -s /usr/lib64/libfastcommon.so /usr/lib/libfastcommon.so
	ln -s /usr/lib64/libfdfsclient.so /usr/lib/libfdfsclient.so


三、安装FastDFS
1、进入到 cd /usr/local/software下，解压FastDFS_v5.05.tar.gz
命令：cd /usr/local/software
命令：tar -zxvf FastDFS_v5.05.tar.gz -C /usr/local/fast/

2、安装编译
命令：cd /usr/local/fast/FastDFS
编译命令：./make.sh
安装命令：./make.sh install


tar -zxvf nginx-1.6.2.tar.gz -C /usr/local/














