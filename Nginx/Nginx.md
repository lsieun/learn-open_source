# Nginx #

## Nginx概述 ##

Nginx是一款轻量级的Web服务器/反向代理服务器及电子邮件(IMAP/POP3)代理服务器，并在一个BSD-like协议下发行。由俄罗斯的程序设计师Igor Sysoev所开发，供俄国大型的入口网站及搜索引擎Rambler使用。其特点是占有内存少，并发能力强，事实上Nginx的并发能力确实在同类型的网页服务器中表现较好，中国大陆使用Nginx网站用户有：百度、新浪、网易、腾讯等。

## 负载均衡策略 ##

1. 使用硬件复杂均衡策略实现，如使用F5、Array等负载均衡器
2. 使用软件进行负载均衡
	- 如使用阿里云服务器均衡负载SLB
	- 使用Nginx+Keeplived
	- 其他软件负载均衡LVS(Linux Virtual Server)、haproxy等技术

## Nginx优点 ##

Nginx可以在大多数Unix like OS上编译运行，并有Windows移植版。Nginx的1.4.0稳定版已经于2013年4月24日发布，一般情况下，对于新建站点，建议使用最新稳定版作为生产版本，已有站点的升级急迫性不高。Nginx的源代码使用2-clause BSD-like license。

Nginx是一个很强大的高性能Web和反向代理服务器，它具有很多非常优越的特性。

在高连接并发的情况下，Nginx是Apache服务器不错的替代品：Nginx在美国是做虚拟主机生意的老板们经常选择的软件平台之一。能够支持高达50,000个并发连接数的响应，感谢Nginx为我们选择了epoll and kqueue作为开发模型。

## Nginx环境搭建（一） ##

1. wget下载： http://nginx.org/download/niginx-1.6.2.tar.gz
2. 进行安装： 
	/usr/local/software
	tar -zxvf nginx-1.6.2.tar.gz
3. 下载所需要的依赖库文件：
	yum -y install pcre
	yum -y install pcre-devel
	yum -y install zlib
	yum -y install zlib-devel
4. 进行configure配置： cd nginx-1.6.2 && ./configure --prefix=/usr/local/nginx 查看是否报错
5. 编译安装 make && make install
6. 启动Nginx
	- cd /usr/local/nginx目录下：查看如下4个目录
	- conf配置文件、html网页文件、logs日志文件、sbin主要二进制程序
	- 启动命令： /usr/local/nginx/sbin/nginx 关闭(-s stop) 重启 (-s reload)
7. 成功：查看是否启动成功(netstat -ano | grep 80) 失败：可能为80端口被占用等，浏览器访问地址： http://192.168.80.21:80 看到欢迎界面

## Nginx配置文件说明 ##

nginx虚拟主机配置

	server {
		listen 1234;
		server_name bhz.com;
		location / {
			root bhz.com;
			index index.html;
		}
	}

## Nginx日志管理 ##

Nginx访问日志放在logs/host.access.log下，并且使用main格式（还可以自定义格式）

对于main格式如下定义：

#日志文件输出格式 这个位置相当于全局设置
	#log_format main '$remote_addr - $remote_user [$time_local] "$request"'
	#      '$status $body_bytes_sent "$http_referer"'
	#      '"$http_user_agent" "$http_x_forwarded_for"'

nginx.conf

    #log_format  main  '$remote_addr - $remote_user [$time_local] "$request" '
    #                  '$status $body_bytes_sent "$http_referer" '
    #                  '"$http_user_agent" "$http_x_forwarded_for"';

    #access_log  logs/access.log  main;



查看日志内容命令：

	tail -n 100 nginx/logs/access.log

在日常工作中，对Nginx日志的分析非常重要，通常需要运维去对Nginx的日志进行切割和分析处理。比如实现一个定时任务，去处理Nginx日志等。

第一步：分析如何去实现日志切分，编写shell脚本。
第二步：定时任务对脚本进行调度： ctrontab -e

	*/1 * * * * sh /usr/local/nginx/sbin/log.sh












