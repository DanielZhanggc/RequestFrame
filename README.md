
# RequestFrame
Retrofit+RxJava+OkHttp3请求框架

# 注意
1.在测试GET请求的时候将NetClient类下面的变量BASE_URL变换成get请求对应的url

2.在测试POST请求的时候将NetClient类下面的变量BASE_URL变换成post请求对应的url

3.测试文件上传功能的时候请在手机本地准备好待上传的图片，路径为/storage/emulated/0/Download/11.jpg并命名为11.jpg

4.如果测试使用的机型Android版本较高,需要动态请求读写权限(由于比较急促代码里面没有),则需要手动打开该应用的读写权限

5.在集成文件下载时,注意model注入的依赖和project注入的仓库

6.下载上传都需要在根目录下创建好/storage/emulated/0/Download/文件夹,代码里没有添加判断并创建

7.使用AS3.1创建的项目,在注入依赖使用的是implementation注入,需要注意

8.黄油刀也舍弃了apt,注意版本即可
