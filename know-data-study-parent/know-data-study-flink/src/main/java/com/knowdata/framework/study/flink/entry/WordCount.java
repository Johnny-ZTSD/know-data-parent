package com.knowdata.framework.study.flink.entry;

import cn.hutool.core.lang.Tuple;
import com.knowdata.framework.study.flink.utils.SecurityUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.flink.api.common.ExecutionConfig;
import org.apache.flink.api.common.RuntimeExecutionMode;
import org.apache.flink.api.common.functions.*;
import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.common.time.Time;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.configuration.RestOptions;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.MessageUtils;
import sun.security.provider.MD5;
import sun.security.rsa.RSASignature;

import java.io.IOException;
import java.security.MessageDigest;
import java.util.concurrent.TimeUnit;

/**
 * @reference-doc
 *  [0] 使用状态 - Apache/Flink - https://nightlies.apache.org/flink/flink-docs-release-1.13/docs/dev/datastream/fault-tolerance/state/
 *  [1] [Flink] Flink Demo Job : WordCount - 博客园/千千寰宇 - https://www.cnblogs.com/johnnyzen/p/17686521.html
 *  [2] 大话Flink之七-Flink的状态管理 - CSDN - https://blog.csdn.net/youhaitao_do/article/details/111406563
 *  [3] flink的ProcessWindowFunction函数的三种状态 - CSDN  https://blog.csdn.net/lixia0417mul2/article/details/132155845
 *  [4] Flink State 最佳实践 - Zhihu/阿里云 - https://zhuanlan.zhihu.com/p/136722111
 *  [5] Flink / Kafka - Recovery is suppressed by FixedDelayRestartBackoffTimeStrategy 排查与修复 - aliyun - https://developer.aliyun.com/article/999108
 *
 *  [1] Redis如何实现计数统计 - 脚本之家 - https://www.jb51.net/database/319366852.htm
 */
public class WordCount {
    private final static Logger logger = LoggerFactory.getLogger(WordCount.class);

    private final static String JOB_NAME = "word-count-job";

    /**
     * @usage
     *  step1 install `nc` and start it by `nc -L -p 9000 -v` at windows os
     *  step2 start up the flink job(`WordCount`)
     *  step3 input any sentence at nc console.
     *      sample sentence : "Hello World"
     *  step4 flink job output log on console : (sample log)
     *      [Flat Map (8/8)#0raceId] [2024/05/24 11:42:28.582] [INFO] [Flat Map (8/8)#0] [com.knowdata.framework.study.flink.entry.WordCount] element | Hello World
     *      [Filter -> Map -> Sink: Print to Std. Out (2/8)#0raceId] [2024/05/24 11:42:28.627] [INFO] [Filter -> Map -> Sink: Print to Std. Out (2/8)#0] [com.knowdata.framework.study.flink.entry.WordCount] sentence : Hello World | totalWords : 2 | sentenceWordsValue: (Hello World,2,1) | currentHandledWords : 1 | currentWord : Hello
     *      2> (Hello World,2,HELLO)
     *      [Filter -> Map -> Sink: Print to Std. Out (2/8)#0raceId] [2024/05/24 11:42:28.628] [INFO] [Filter -> Map -> Sink: Print to Std. Out (2/8)#0] [com.knowdata.framework.study.flink.entry.WordCount] Success to handle the sentence's all words! sentence: Hello World, totalWords: 2, currentWord: World
     *      [Filter -> Map -> Sink: Print to Std. Out (2/8)#0raceId] [2024/05/24 11:42:28.628] [INFO] [Filter -> Map -> Sink: Print to Std. Out (2/8)#0] [com.knowdata.framework.study.flink.entry.WordCount] sentence : Hello World | totalWords : 2 | sentenceWordsValue: (Hello World,2,2) | currentHandledWords : 2 | currentWord : World
     *      2> (Hello World,2,WORLD)
     * @param args
     * @throws Exception
     * @reference-doc
     *  [1] Flink Configuration 配置文件的配置 - CSDN - https://blog.csdn.net/hell_oword/article/details/124524377
     *  [2] 【Flink源码篇】Flink提交流程之flink-conf.yaml的解析和3种flink命令行客户端的添加 - CSDN - https://blog.csdn.net/yy8623977/article/details/125768457
     *      env : FLINK_CONF_DIR | $FLINK_HOME/conf/flink-conf.yaml
     *  [3] Flink 指南与Hudi - hudi - https://hudi.incubator.apache.org/cn/docs/next/flink-quick-start-guide/
     *  [4] 【flink配置系列】FLink配置大全 - CSDN - https://blog.csdn.net/wang2leee/article/details/133808565
     */
    public static void main(String[] args) throws Exception {
        logger.info("job startup - start");
        // 1.准备环境
        StreamExecutionEnvironment env = createStreamExecutionEnvironment();

        // 设置作业参数
        //ExecutionConfig config = env.getConfig();
        // 设置运行模式 | STREAMING, BATCH , AUTOMATIC
        env.setRuntimeMode(RuntimeExecutionMode.AUTOMATIC);
        // 设置时间语义
        //env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);//事件时间
        env.setStreamTimeCharacteristic(TimeCharacteristic.ProcessingTime);//处理时间[Flink 默认值]

        //(可选) 设置重启策略 | 没有指定重启策略，在本地部署时，不需要指定重启策略。
        env.setRestartStrategy(RestartStrategies.fixedDelayRestart(
                3, // 尝试重启的次数
                Time.of(10, TimeUnit.SECONDS) // 间隔
        ));//以免报: Caused by: org.apache.flink.runtime.JobException: Recovery is suppressed by NoRestartBackoffTimeStrategy

        // 2.加载数据源
        DataStreamSource<String> elementsSource = env.socketTextStream("127.0.0.1", 9000);

        // 3.数据转换
        /**
         * flapMap operator (flatMap 算子)
         * inputStream = { "Hello World", "Hello this is a Flink Job" }
         * operator code : val words = dataStream.flatMap ( input => input.split(" ") )
         * output: ["Hello", "World", "Hello", "this", "is", "a", "Flink", "Job"]
         */

        DataStream<Tuple3<String, Integer, String>> originWordsDataStream = elementsSource
            .flatMap(new RichFlatMapFunction<String, Tuple3<String, Integer, String>>() { //RichFlatMapFunction / FlatMapFunction
                @Override
                public void open(Configuration configuration) throws Exception {
                    //...
                }

                @Override
                public void flatMap(String element, Collector<Tuple3<String, Integer, String>> out) throws IOException {
                    logger.info("element | {}", element);
                    String[] wordArr = element.replace(".", " ").replace(",", " ").split(" ");
                    for (String word : wordArr) {
                        out.collect(new Tuple3( element , wordArr.length, word));//element := sentence
                    }
                }
            });

        DataStream<Tuple3<String, Integer, String>> filterWordDataStream = originWordsDataStream
                //.keyBy( v -> SecurityUtils.md5( v.f0 ) )//以md5(每行)为key、1行即人为是1个句子
                .keyBy( v -> v.f0 )//以md5(每行)为key、1行即人为是1个句子
                .filter(new RichFilterFunction<Tuple3<String, Integer, String>>() {//RichFilterFunction / FilterFunction
            private transient ValueState<Tuple3<String, Integer , Integer>> sentenceWordsValueState;// v0:当前的句子、v1:每个句子总共的单词数 v2:已处理当前句子的单词个数

            @Override
            public void open(Configuration configuration) throws Exception {
                TypeInformation typeInformation = TypeInformation.of(new TypeHint<Tuple3<String, Integer , Integer>>() {});
                ValueStateDescriptor<Tuple3<String, Integer , Integer>> valueStateDescriptor = new ValueStateDescriptor<Tuple3<String, Integer , Integer>>("sentenceValueState", typeInformation);
                sentenceWordsValueState = getRuntimeContext().getState( valueStateDescriptor );
            }

            @Override
            public boolean filter(Tuple3<String, Integer, String> value) throws Exception {
                String sentence = value.f0;
                Integer sentenceTotalWords = value.f1;//本句子的总单词数 | 获取方式1
                String word = value.f2;

                boolean flag = false;
                flag = word.length() > 3 ? true : false;

                Tuple3<String, Integer , Integer> sentenceWordsValue = sentenceWordsValueState.value();//取出状态
                if(ObjectUtils.isEmpty( sentenceWordsValue )){
                    sentenceWordsValue = new Tuple3<>();
                    sentenceWordsValue.f0 = sentence;
                    sentenceWordsValue.f1 = sentenceTotalWords;
                    sentenceWordsValue.f2 = 0;//已处理的单词数，初始化为0
                } else {
                    Integer sentenceTotalWords2 = sentenceWordsValue.f1;//本句子的总单词数 | 获取方式2
                }

                Integer currentHandledWords = sentenceWordsValue.f2 + 1;//已处理的单词数
                sentenceWordsValue.f2 = currentHandledWords;
                if(currentHandledWords == sentenceTotalWords){//当处理完本句子的最后一个时，打印1条通知日志 | 使用 keyBy + KeyStream + ValueState 的最终目的
                    logger.info("Success to handle the sentence's all words! sentence: {}, totalWords: {}, currentWord: {}", sentenceWordsValue.f0, sentenceTotalWords, word);
                }
                sentenceWordsValueState.update( sentenceWordsValue );//更新状态

                logger.info("sentence : {} | totalWords : {} | sentenceWordsValue: {} | currentHandledWords : {} | currentWord : {}", sentence, sentenceTotalWords, sentenceWordsValue, currentHandledWords, word);

                return flag;
            }
        } );

        //DataStream 下边为 DataStream 子类
        SingleOutputStreamOperator<Tuple3<String, Integer, String>> source = filterWordDataStream.map(new RichMapFunction<Tuple3<String, Integer, String>, Tuple3<String, Integer, String>>() {// RichMapFunction / MapFunction
            @Override
            public Tuple3<String, Integer, String> map(Tuple3<String, Integer, String> value) {
                String sentence = value.f0;
                Integer sentenceTotalWords = value.f1;
                String word = value.f2;

                word = word.toUpperCase();

                return new Tuple3<>(sentence, sentenceTotalWords, word);
            }
        });

        // 4.数据输出
        source.print();
        //dataStream.print();

        // 5.执行程序
        env.execute(JOB_NAME);

        logger.info("job startup - end");
    }

    /**
     * @reference-doc
     *  [1] [Flink] Flink Job之Web UI - 博客园/千千寰宇 - https://www.cnblogs.com/johnnyzen/p/18003007
     * @return
     */
    public static StreamExecutionEnvironment createStreamExecutionEnvironment(){
        // step1 定义作业配置
        Configuration jobConfiguration = new Configuration();

        //设置WebUI绑定的本地端口
        //jobConfiguration.setInteger(RestOptions.PORT, 18081);//"rest.port" / RestOptions.PORT 均可 | 注: 8081 是默认端口
        jobConfiguration.setString(RestOptions.BIND_PORT, "18081");//样例值： "18081"、"8082-8089"
        jobConfiguration.setString(RestOptions.ADDRESS, "127.0.0.1");//"rest.address"
        jobConfiguration.setString(RestOptions.BIND_ADDRESS, "0.0.0.0");//"rest.bind-address" : 0.0.0.0

        //step2 基于作业配置，创建执行环境
        //StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();//方式1 : 不启用 WEB UI
        StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironmentWithWebUI(jobConfiguration);//方式2 : 本地运行模式 + 启用 WEB UI
        //StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment(jobConfiguration);//方式3 : 集群运行模式 + 启用 WEB UI

        return env;
    }
}
