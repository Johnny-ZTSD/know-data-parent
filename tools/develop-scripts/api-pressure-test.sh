#!/bin/bash
# @author : johnny zen
# @url : https://www.cnblogs.com/johnnyzen
# @referenced-doc
#   [1] [Linux]常用命令之【cat/echo/iconv/vi/grep/find/head/tail】 - 博客园/千千寰宇 - https://www.cnblogs.com/johnnyzen/p/13438246.html#_label4
#   [2] shell队列实现线程并发控制 - CSDN - https://blog.51cto.com/egon09/1754317
#   [3] Shell printf 命令 - 菜鸟教程 - https://www.runoob.com/linux/linux-shell-printf.html
#   [4] https://blog.csdn.net/zxc024000/article/details/102914691
# @usage
#   [1] bash ./api-pressure-test.sh log 10
#   [2] rm -f api-tests-re*.json

dir=$1
totalTimes=$2

uri=http://userservice.know-data.com:80
#appApi=/user-service/v1/user-account/login/wechat/login
#appApiVersion=v1.0
apiPath=/user-service/v1/user-account/login/wechat/login
errorInfo='"status":false'

# `seq 0 2` => [1, 2]
for i in `seq 0 $totalTimes`; do
    dateStr=`date '+%Y%m%d%H%M%S'`;
    requestBody="{
      \"params\": {
          \"saleStatus\": \"SALE\"
          , \"vehicleType\": \"NORMAL\"
          , \"startTime\": 1667923200000
          , \"endTime\": 1668527999999
          , \"nation\": \"CHN\"
          , \"requestCount\":  $i
      }
    }";
    echo $requestBody > ./$dir/api-tests-request-$dateStr.json

    responseBody=`curl -s -XPOST $uri$apiPath -H 'Content-Type: application/json;charset=UTF-8' -d "$requestBody"`;
    responseBodyFilePath="./$dir/api-tests-reponse-$dateStr.json"
    echo $responseBody > $responseBodyFilePath
    # 判断匹配函数，匹配函数不为0，则包含给定字符
    appearTimes=`grep -o "$errorInfo" $responseBodyFilePath | wc -l`

    # echo "[$i] $dateStr is finished!";
    printf "%-5d %-14s is finished!\n" $i $dateStr

    if [ $appearTimes -ne '0' ]; then
        echo "[$responseBodyFilePath] The response contains ($errorInfo),and it appears $appearTimes times in total!"
        exit 1
    fi

done