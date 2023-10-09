#!/bin/bash
# description : 升级本工程下的快照版本
# note :
#  [1] 用于开发者在【本地电脑】端执行本脚本，以进行快速升级本maven工程内多个pom.xml文件的快照版本
#  [2] 要求本工程目录内除了自身工程为快照版本外，不得引入别的快照版本(Snapshot Version)
#  [3] 需在支持 perl / find 的 Linux shell 命令的命令行环境下执行，推荐： Git Bash
# author : johnny zen
# url : https://www.cnblogs.com/johnnyzen
# reference-doc : 
#     https://www.cnblogs.com/johnnyzen/p/15067593.html
#     https://blog.csdn.net/bluewait321/article/details/110643279
# usage : 
#     sample: 1.1.9-SNAPSHOT ==> 1.1.10-SNAPSHOT 
#     cmd : ./upgrade-project-snapshot-version.sh 1.1.9 1.1.10
# create-time : 2023-03-01 17:40

# [1] variables
oldSnapshotVersion="${1}-SNAPSHOT"
newSnapshotVersion="${2}-SNAPSHOT"
projectName="bdp-data-service-parent"
fileName="pom.xml"

echo "[INFO] oldSnapshotVersion: ${oldSnapshotVersion}"
echo "[INFO] newSnapshotVersion: ${newSnapshotVersion}"
echo "[INFO] fileName: ${fileName}"

# [2] execution
for filePath in `find ./../../ -type f -name "${fileName}"` ;
do 
	echo "[INFO]    perl -pi -e 's#${oldSnapshotVersion}#${newSnapshotVersion}#g' ${filePath}"
	perl -pi -e "s/${oldSnapshotVersion}/${newSnapshotVersion}/g" ${filePath}
done

echo "[INFO] success to upgrade snapshot version at ${fileName} for in the maven project(${projectName})~"