# What is an uber jar?

URL: https://stackoverflow.com/questions/11947037/what-is-an-uber-jar

`Über` is the German word for **above** or **over**, as in a line from a previous national anthem(赞美诗；圣歌): `Deutschland, Deutschland, über alles` (Germany, Germany above all else).

Hence, in this context, an **uber-jar** is an "over-jar", one level up from a simple "jar", defined as one that contains both your package and all its dependencies in one single JAR file. The name can be thought to come from **the same stable** as ultrageek, superman, hyperspace, and metadata, which all have similar meanings of "**beyond the normal**".

> the same stable  
> stable并不是“稳定”的意思，而是the establishment(机构) that trains or manages such a group of people。例句：two boxers(拳击运动员) from the same stable.

The advantage is that you can distribute your uber-jar and not care at all whether or not dependencies are installed at the destination, as your uber-jar actually has no dependencies.

All the dependencies of your own stuff within the uber-jar are also within that uber-jar. As are all dependencies of those dependencies. And so on.
