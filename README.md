# SharedPrefenceViewer

#前言
我们在开发APP过程中基本或多或少肯定会用到SharedPrefeerence来存储一些状态，数据等持久化的信息。
  不过我们在开发调试的过程中肯定会常常需要来验证我们SharedPrefeerence的值是否有效，或者验证这个值得各种情况下的响应状态，有时为了验证需要自己去代码中修改值，然后再编译运行。 如果你的APP规模小还行，要是是一个大的工程项目编译动辄需要好几分钟的情况下就很坑爹了。
因为为了解决这个查看、修改SharedPrefeerence内容的需求，我用了几个晚上的时间写了个专门针对SharedPrefeerence内容进行增删改查的工具，主旨为了方便开发者们可以很方便的进行调试你的APP。
 下面是一些具体的用法。
##在你的项目gradle文件中的dependencies首先导入工具包
    compile 'com.aquila.spviewer:sharedpreferencesviewer:1.0.5'

具体的引用大致结构如下：

       dependencies{
          compile 'com.aquila.spviewer:sharedpreferencesviewer:1.0.5'
          // 下面是你其他的引用库
          ...
      }
      
同步gradle之后你就可以愉快的使用了。使用起来非常简单
在你的APP中随便一个界面给你其中的任何一个View控件的OnCLickListener响应时间中添加上这行代码就可以跳转

      SPFileListActivity.gotoSPFileListActivity(this);

####【注意】参数中的 this就是指你当前的Context完整用法如下：
         
     @Override
          public void onClick(View v) {
        if (v == gotoSPButton){
            //这里的this就是Activity，传Context也可以
            SPFileListActivity.gotoSPFileListActivity(this); 
        }
    }

此工具已经自动处理好自在debug版本下进行跳转，在你Release版本的APP中是不会跳转过去的。当然如果你想要再release下也要进行调试的话可以在上面跳转的方法中在添加一行

      SPFileListActivity.setIsReleaseCanJump(true);
完整代码如下：

    @Override
    public void onClick(View v) {
        if (v == gotoSPButton){
            // 这个设置是在release下的APP也可以跳转过去
            SPFileListActivity.setIsReleaseCanJump(true);
            // 这里的this参数就是Activity自己，如果是在
            SPFileListActivity.gotoSPFileListActivity(this);
        }
    }

这样设置之后点击按钮就可以直接跳转到SharedPrefeerence文件列表以及参数列表了。

下面是简单的操作演示：
![操作示意图](http://upload-images.jianshu.io/upload_images/3987407-77a8326c08891375.gif?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


