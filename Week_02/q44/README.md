# 环境情况：
> 使用win10，AdoptOpenJDK 11.0.5+10 64bit,mixed mode， 16G内存。
与1.8不同。默认使用了G1

> java -XX:+PrintGC GCLogAnalysis

> java -Xlog:GC -Xlog:gc:./logs/gc.g1512m.log -XX:+UseG1GC -Xms512m -Xmx512m GCLogAnalysis
> java -Xlog:GC -Xlog:gc:./logs/gc.cms512m.log -XX:+UseConcMarkSweepGC -Xms512m -Xmx512m GCLogAnalysis
> java -Xlog:GC -Xlog:gc:./logs/gc.serial512m.log -XX:+UseSerialGC   -Xms512m -Xmx512m GCLogAnalysis
> java -Xlog:GC -Xlog:gc:./logs/gc.paralle512m.log -XX:+UseParallelGC   -Xms512m -Xmx512m GCLogAnalysis

*** 观察4G的情况。
> java -Xlog:GC -Xlog:gc:./logs/gc.g14G.log -Xms4g -Xmx4g GCLogAnalysis
> java -Xlog:GC -Xlog:gc:./logs/gc.cms4g.log -XX:+UseConcMarkSweepGC -Xms4g -Xmx4g GCLogAnalysis
> java -Xlog:GC -Xlog:gc:./logs/gc.serial4g.log -XX:+UseSerialGC   -Xms4g -Xmx4g GCLogAnalysis
> java -Xlog:GC -Xlog:gc:./logs/gc.paralle4g.log -XX:+UseParallelGC   -Xms4g -Xmx4g GCLogAnalysis

汇总图表如下：

|gc:对象数/GC次数 | 512m | 4G| 
|--|--|--|
|G1|7639/54|15358/12|
|cms|10321/24|14384/4|
|Parallel|8044/27|12912/2|
|Serial|10475/21|15095/2|

以上实际GC次数需要+1.
不同的内存情况下G1的提升明显。GC次数明显减少。单次GC时间增大。4G G1，只发生了 Evacuation Pause。
4G region大小为2m,512m region大小为1m；在实验中还观察到512m情况下，开启GCdetails参数时与未开启details的差距生产对象次数差距极大。开启details大约只能达到5000左右。
G1的 region的大小与生产对象的大小，极度影响到其性能表现。G1在大内存情况下，更能体现出优势。

## ab实验
机器上有安装有ab，故采用ab对性能进行实验。由于jar包不支持该jdk。故此处更换了jdk版本。
使用了oracle java 1.8.0_144
命令如下：
ab -t 10  -c 20  http://localhost:8088/api/hello
对应图标如下：

|gc/平均qps| 512m | 1G| 
|--|--|--|
|g1|4309|4095|
|cms|4278|4337|
|Parallel|4259|4368|
|Serial|4168|4054|
各个GC基本无差异,串行与g1有下降.由于使用jdk版本限制,导致无法继续放大内存.结果有限参考.



