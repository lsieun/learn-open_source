## 1、Nginx简介 ##

### 1.1、Nginx概述 ###

Nginx是一款轻量级的Web服务器/反向代理服务器及电子邮件(IMAP/POP3)代理服务器，并在一个BSD-like协议下发行。由俄罗斯的程序设计师Igor Sysoev所开发，供俄国大型的入口网站及搜索引擎Rambler使用。其特点是占有内存少，并发能力强，事实上Nginx的并发能力确实在同类型的网页服务器中表现较好，中国大陆使用Nginx网站用户有：百度、新浪、网易、腾讯等。



### 1.2、负载均衡策略 ###

1. 使用硬件复杂均衡策略实现，如使用F5、Array等负载均衡器
2. 使用软件进行负载均衡
	- 如使用阿里云服务器均衡负载SLB
	- 使用Nginx+Keeplived
	- 其他软件负载均衡LVS(Linux Virtual Server)、haproxy等技术

### 1.3、Nginx优点 ###

Nginx可以在大多数Unix like OS上编译运行，并有Windows移植版。Nginx的1.4.0稳定版已经于2013年4月24日发布，一般情况下，对于新建站点，建议使用最新稳定版作为生产版本，已有站点的升级急迫性不高。Nginx的源代码使用2-clause BSD-like license。

Nginx是一个很强大的高性能Web和反向代理服务器，它具有很多非常优越的特性。

在高连接并发的情况下，Nginx是Apache服务器不错的替代品：Nginx在美国是做虚拟主机生意的老板们经常选择的软件平台之一。能够支持高达50,000个并发连接数的响应，感谢Nginx为我们选择了epoll and kqueue作为开发模型。
