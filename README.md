# Spark_learn
## 概记
### 当前进度
P81/P201    P174/P201
### 课程信息

1.课程跟随：[尚硅谷Spark](https://www.bilibili.com/video/BV11A411L7CK)
### 遇到的坑
1.配置环境老费劲了

## 课程笔记
### 1 *********
### 2 ***********
### 3 Spark运行环境
这一章主要是讲环境配置的，然后我看了看后面，后面基本上都是用win环境做教学开发的，这一章的环境配置是在linux集群上做操作的，我觉得，刚开始学习的时候可以不太在意这个配置，虽然配置很重要，但是在工作中，这个是由运维人员配置的，作为开发人员，要把重心放在开发上，十分的合理
#### 3.1 Local模式
本地环境，不需要任何其节点资源就可以在本地执行Spark代码的环境
> 启动：/usr/local/spark/bin/spark-shell
> 关闭：
### 3.2 Standalone模式
* 可以配置历史记录-历史服务
### 3.3 Yarn模式
企业常用的
### 4 Spark运行架构
Driver是驱动器节点，用于执行spark任务中的main方法，
Executor是执行的节点，

#### 4.3 核心概念
##### 4.3.1 Executor与Core

### 5 Spark核心编程
三个数据结构：
* RDD：弹性分布式数据集
* 累加器：分布式共享只写变量（只写？不可以读嘛？）
* 广播变量：分布式共享只读变量

#### 5.1 RDD
* 代表一个弹性的，不可变，可分区，里面的元素可并行计算，
* 只有调用collect方法的时候，才进行业务操作，类似于lazy模式，
* RDD不保存数据，IO可以临时保存一部分数据，
* 内存和硬盘存储切换、数据丢失可以自动恢复、计算出错，重试机制、可根据分片重新计算；
* 数据存储在大数据不同节点上；
* RDD封存数据集，不保存数据只是封装计算逻辑；
* RDD是一个抽象类，需要子类具体实现；
* RDD封装了计算逻辑，是不可以改变的，要想改变，只能产生新的RDD，在新的RDD里面封装新的逻辑；
* 可分区，并行计算；
* 移动数据不如移动计算，数据不移动；
* RDD主要是用于讲逻辑进行封装，并生成Task发送给Executor节点进行计算；

##### RDD转换算子
###### 1. Value类型
* map算子是分区内一个数据一个数据的执行，类似于查串行操作，注意是分区内，不同分区可并行；
* map是数据进行转换和改变，不会减少和增多数据；
* map性能低，但是不占内存；
* mapPartitions算子是以分区为单位进行批处理操作，也就是可以一次性读取整个分区的数据进行操作；
* mapPartitions是返回一个迭代器，可以增加或者减少数据；
* mapPartitions性能高，但是占用内存，短时间不释放；
> 完成比完美更重要~

* mapParatitionsWithIndex: 相比于mapPartitions多一个分区的索引；
* flatMap：将函数进行扁平化后在映射处理，说着很抽象，别人的博客上看了一个图，特别好，沾一下：

![image-20220520220323189](https://20178666.oss-cn-beijing.aliyuncs.com/img/image-20220520220323189.png)

![image-20220520220344879](https://20178666.oss-cn-beijing.aliyuncs.com/img/image-20220520220344879.png)

* glom：将同一个分区的数据直接转换为相同类型的内存数组进行处理，分区不变
* groupBy：将数据根据指定规则进行分组，分区默认不变，但是数据会被打乱重新组合（毕竟是要分区），这样的操作也被成为shuffle。极端情况下，数据可能被分在同一个组？数据在groupBy条件下一样？
* filter：将数据根据指定的规则进行筛选过滤，符合规则的留下，不符合规则的丢弃，当数据进行筛选过滤后，分区不变，但是分区内的数据可能不均衡（当然了，毕竟筛选出去了数据）
* sample： 根据指定的规则从数据集中抽取数据，随机抽取，可选择抽取后放回和不放回；
* distinct：将数据集中重复的元素去重，比较容易理解
* coalesce：根据数据量缩减分区，用于大数据集过滤后，提高小数据集的执行效率；
* sortBy：根据指定的规则将数据进行排序；

###### 2. 双Value类型
* intersection：交集
* union：并集
* subtract：差集
* zip：拉链

###### 3. Key-Value类型
* partitionBy：将数据按照指定的Partitioner重新进行分区，Spark默认分区器是HashPartitioner
* reduceByKey：可以将数据按照相同的Key对Value进行聚合，分区内和分区间的计算逻辑相同
* groupByKey：将分区的数据直接转换为相同类型的内存数组进行后续处理；

> reduceByKey和groupByKey的区别： 它们两个都可以对数据进行分组，
>> 从 shuffle 的角度：reduceByKey 和 groupByKey 都存在 shuffle 的操作，但是 reduceByKey
可以在 shuffle 前对分区内相同 key 的数据进行预聚合（combine）功能，这样会减少落盘的
数据量，而 groupByKey 只是进行分组，不存在数据量减少的问题，reduceByKey 性能比较
高。
>> 从功能的角度：reduceByKey 其实包含分组和聚合的功能。GroupByKey 只能分组，不能聚
合，所以在分组聚合的场合下，推荐使用 reduceByKey，如果仅仅是分组而不需要聚合。那
么还是只能使用 groupByKey

* aggregateByKey：将数据根据不同的规则进行分区内计算和分区间计算，即可以完成分区内取最大值，分区间再相加的操作 
* join：在类型为(k,v)和(k,w)的RDD上调用，返回一个相同key对应的所有元素连接在一起
> join:相当于内连接，只保留两个rdd相同key的结果，相当于inner join
> leftOuterJoin：相当于是左外连接
> rightOuterJoin：相当于是右外连接
> fullOuterJoin：全连接

* cogroup：


`记于：2022-05-21 23:30:52，刚学完了RDD的转换算子，但是SparkSQL十分的紧急，所以跳过中间的，直接看SparkSQL，估计会遇到环境配置的问题吧，毕竟是用到sql，起码会有数据库的问题`


## SparkSQL
### hive和SparkSQL
* Spark SQL是Spark用于结构化数据处理的spark模块
* hive底层是MapReduce，SparkSQLs是基于spark

#### DataFrame
是一个以RDD为基础的分布式数据集，传统数据库的二维表格
* createTempView(): 闯进临时视图，只在本次session中起作用
* createOrReplaceTempView(): 见名知意，同上，同名可替代
* createOrReplaceGlobalTempView(): 创建全部session的连接都可以使用的视图，使用的时候global_temp.tablename使用

##### DSL语法

#### DataSet

#### UDF
用户可以通过spark.udf功能添加自定义函数，实现自定义功能 

### 数据的加载和保存
SparkSQL默认读取和保存的文件格式是parquet

> spark.read.load是加载数据的通用方法，之前使用的是spark.read.json()读取json数据


