详细项目介绍见： https://blog.csdn.net/kingmax54212008/article/details/82254578

项目演示及讲解 

优酷  http://v.youku.com/v_show/id_XODk5NjkwOTg4.html

爱奇艺  http://www.iqiyi.com/w_19rs1v2m15.html#vfrm=8-7-0-1

土豆 http://www.tudou.com/programs/view/fv0H93IHfhM

 

大家在调试的同时一定要注意

1、网络下载、上传这些操作，就是耗时的操作，要另外开线程操作，不能放到主线程里面。

2、网络下载、上传里面不能有ui操作,不能在里面显示ui。就是不能在子线程里面操作UI。如果操作完毕ui提示用户等操作，可以使用利用handler结合Thread更新UI，或者AsyncTask异步更新UI。

3、上传到本地Tomcat服务器，要关闭防火墙

接下来将给出两个项目部分代码，当然两个项目都有一个工具类HttpPost

 ![](https://github.com/kkman2008/androidbrowseuploadfile/blob/master/res/20180901.png)
