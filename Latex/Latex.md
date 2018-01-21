先在文件的开头添加宏包：

\usepackage{amssymb}

\usepackage{amsmath}

百分号可以用  \%  来输入，反斜杠用  \backslash  输入。（注意反斜杠算符号，要在$...$或者$$...$$中输入）

要输入的文字可以用如下语句：
\%123$\backslash$dadss

求和

```latex
\begin{displaymath}
\sum_{i=1}^{n}
\end{displaymath}
```

[常用数学符号的 LaTeX 表示方法](http://www.mohu.org/info/symbols/symbols.htm)

http://blog.sina.com.cn/s/blog_5e16f1770100fs38.html

    https://www.cnblogs.com/GarfieldEr007/p/5577727.html

    

    http://blog.csdn.net/u014803202/article/details/50410748

p(\epsilon^{(i)})=\frac{1}{\sqrt{2\pi}\sigma}e^{-\frac{(\epsilon^{(i)})^{2}}{2\sigma^{2}}}

[LaTeX 设置字体颜色](http://blog.csdn.net/yhl_leo/article/details/50240179)
[Latex中如何设置字体颜色（三种方式）](https://www.cnblogs.com/tsingke/p/7457236.html)

如何在公式中表示上下堆积关系？

答：在latex有一个命令\stackre{上部符号}{下部符号}，但更好的一个是用宏包
amslatex中命令\overset和\underset。一个例子是
\[ \overset{*}{X} \qquad
\underset{*}{X} \qquad
\overset{a}{\underset{b}{X}} \]


WinEdt显示行号

	在编辑窗口中右键点击左侧边框，【右键】-【Show Line Numbers】。
	若要取消行号，在左侧边框上【右键】-【Hide  Line Numbers】

WinEdt修改字体大小

	Options-->Preferences-->Font




