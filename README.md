# ScanQRcode
扫描二维码，识别二维码代表的飞控ID到手机内存和网络上提取此飞控的信息列表，可修改
主界面如下所示

![主界面](https://github.com/IsMaQian/ScanQRcode/raw/master/picture/mainView.jpg)

上面按钮是扫描二维码后，得到ID，根据ID从服务器数据库中获取数据（服务器数据库是Apache搭建的一个局域网服务器，在里面通过php脚本语言建立orcal数据库和手机的联系）
下面按钮是扫描二维码后，得到ID，根据ID从本地获取数据，（通过realm在本地建立数据库），
在下面的按钮是增加ID号到本地数据库中，
在对话框中可以判断飞控数据是否激活，根据激活状态显示和修改ID所对应的数据
！[dialog]（https://github.com/IsMaQian/ScanQRcode/blob/master/picture/dialog.jpg）
！[修改界面]（https://github.com/IsMaQian/ScanQRcode/blob/master/picture/update.jpg）
