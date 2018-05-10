# CircleProgressBar
A custom circular progress bar
###最新版本号：1.0.0

# 介绍
这是一个自定义的圆形进度条控件，可以高度定制进度条样式。

# 效果图展示
![](https://i.imgur.com/PzvtQTj.png)

# 引入方法

- gradle引入方式：

		compile 'me.my.widget:CircleProgressBar:1.0.0'
- maven引入方式：

		<dependency>
  			<groupId>me.my.widget</groupId>
  			<artifactId>CircleProgressBar</artifactId>
  			<version>1.0.0</version>
  			<type>pom</type>
		</dependency>

- aar包下载：[点击下载](https://jcenter.bintray.com/me/my/widget/CircleProgressBar/1.0.0/CircleProgressBar-1.0.0.aar)

# 使用方法
- xml文件中添加：

		<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
        	xmlns:cp="http://schemas.android.com/apk/res-auto"
        	android:layout_width="match_parent"
        	android:layout_height="wrap_content"
        	android:gravity="center_horizontal"
        	android:orientation="vertical">

			<me.my.widget.CircleProgressBar
                android:id="@+id/progress1"
                android:layout_width="160dp"
                android:layout_height="160dp"
                android:layout_margin="10dp"
                cp:radius="70dp"
                cp:ringLineColor="#eeeeee"
                cp:ringLineWidth="1dp"
                cp:strokeWidth="10dp"
                cp:textColor="#333333"
                cp:textPercentColor="#333333"
                cp:textPercentSize="26sp"
                cp:textSize="45sp" />
		</LinearLayout>

- 代码中使用：

		progressBar1 = findViewById(R.id.progress1);
		//设置进度
        progressBar1.setProgress(100);

# 样式定制

- 圆环颜色 ringColor
- 圆线颜色 ringLineColor
- 文本颜色 textColor
- 百分号颜色 textPercentColor
- 半径 radius
- 圆环宽度 strokeWidth
- 圆线宽度 ringLineWidth
- 文本大小 textSize
- 百分号文本大小 textPercentSize
- 总进度 maxProgress
- 当前进度 currentProgress
- 百分号格式 percentStyle
- 渐变类型 gradientType
 - 由上向下线性渐变LINEAR_FROM_TOP
 - 由左向右线性渐变LINEAR_FROM_LEFT
 - 环形渐变SWEEP