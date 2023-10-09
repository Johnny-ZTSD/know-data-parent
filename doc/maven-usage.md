# Maven 常见操作手册

# 1 常见操作
## CASE1 查看所有的系统变量、环境变量 
```shell 
> mvn help:system
C:\Users\Johnny>mvn help:system
[WARNING]
[WARNING] Some problems were encountered while building the effective settings
[WARNING] 'profiles.profile.id' must be unique but found duplicate profile with id jdk-1.8 @ D:\Program Files(x86)\Maven\apache-maven-3.6.3-self\bin\..\conf\settings.xml
[WARNING]
[INFO] Scanning for projects...
Downloading from repo1: https://repo1.maven.org/maven2/org/apache/maven/plugins/maven-help-plugin/maven-metadata.xml
Downloaded from repo1: https://repo1.maven.org/maven2/org/apache/maven/plugins/maven-help-plugin/maven-metadata.xml (714 B at 421 B/s)
Downloading from repo1: https://repo1.maven.org/maven2/org/apache/maven/plugins/maven-help-plugin/3.4.0/maven-help-plugin-3.4.0.pom
Downloaded from repo1: https://repo1.maven.org/maven2/org/apache/maven/plugins/maven-help-plugin/3.4.0/maven-help-plugin-3.4.0.pom (9.7 kB at 19 kB/s)
Downloading from repo1: https://repo1.maven.org/maven2/org/apache/maven/plugins/maven-plugins/39/maven-plugins-39.pom
Downloaded from repo1: https://repo1.maven.org/maven2/org/apache/maven/plugins/maven-plugins/39/maven-plugins-39.pom (8.1 kB at 15 kB/s)
Downloading from repo1: https://repo1.maven.org/maven2/org/apache/maven/maven-parent/39/maven-parent-39.pom
Downloaded from repo1: https://repo1.maven.org/maven2/org/apache/maven/maven-parent/39/maven-parent-39.pom (48 kB at 63 kB/s)
Downloading from repo1: https://repo1.maven.org/maven2/org/apache/apache/29/apache-29.pom
Downloaded from repo1: https://repo1.maven.org/maven2/org/apache/apache/29/apache-29.pom (21 kB at 39 kB/s)
Downloading from repo1: https://repo1.maven.org/maven2/org/apache/maven/plugins/maven-help-plugin/3.4.0/maven-help-plugin-3.4.0.jar
Downloaded from repo1: https://repo1.maven.org/maven2/org/apache/maven/plugins/maven-help-plugin/3.4.0/maven-help-plugin-3.4.0.jar (64 kB at 95 kB/s)
[INFO]
[INFO] ------------------< org.apache.maven:standalone-pom >-------------------
[INFO] Building Maven Stub Project (No POM) 1
[INFO] --------------------------------[ pom ]---------------------------------
[INFO]
[INFO] --- maven-help-plugin:3.4.0:system (default-cli) @ standalone-pom ---
Downloading from repo1: https://repo1.maven.org/maven2/org/apache/maven/plugin-tools/maven-plugin-tools-generators/3.7.0/maven-plugin-tools-generators-3.7.0.pom
Downloaded from repo1: https://repo1.maven.org/maven2/org/apache/maven/plugin-tools/maven-plugin-tools-generators/3.7.0/maven-plugin-tools-generators-3.7.0.pom (3.8 kB at 7.2 kB/s)
Downloading from repo1: https://repo1.maven.org/maven2/org/apache/maven/plugin-tools/maven-plugin-tools/3.7.0/maven-plugin-tools-3.7.0.pom
Downloaded from repo1: https://repo1.maven.org/maven2/org/apache/maven/plugin-tools/maven-plugin-tools/3.7.0/maven-plugin-tools-3.7.0.pom (17 kB at 34 kB/s)
Downloading from repo1: https://repo1.maven.org/maven2/org/apache/maven/maven-parent/37/maven-parent-37.pom
Downloaded from repo1: https://repo1.maven.org/maven2/org/apache/maven/maven-parent/37/maven-parent-37.pom (46 kB at 86 kB/s)
Downloading from repo1: https://repo1.maven.org/maven2/org/apache/apache/27/apache-27.pom
Downloaded from repo1: https://repo1.maven.org/maven2/org/apache/apache/27/apache-27.pom (20 kB at 38 kB/s)
Downloading from repo1: https://repo1.maven.org/maven2/org/jsoup/jsoup/1.15.3/jsoup-1.15.3.pom
Downloaded from repo1: https://repo1.maven.org/maven2/org/jsoup/jsoup/1.15.3/jsoup-1.15.3.pom (13 kB at 26 kB/s)
Downloading from repo1: https://repo1.maven.org/maven2/org/codehaus/plexus/plexus-interactivity-api/1.1/plexus-interactivity-api-1.1.pom
Downloaded from repo1: https://repo1.maven.org/maven2/org/codehaus/plexus/plexus-interactivity-api/1.1/plexus-interactivity-api-1.1.pom (823 B at 1.6 kB/s)
Downloading from repo1: https://repo1.maven.org/maven2/org/codehaus/plexus/plexus-interactivity/1.1/plexus-interactivity-1.1.pom
Downloaded from repo1: https://repo1.maven.org/maven2/org/codehaus/plexus/plexus-interactivity/1.1/plexus-interactivity-1.1.pom (1.7 kB at 3.4 kB/s)
Downloading from repo1: https://repo1.maven.org/maven2/org/codehaus/plexus/plexus-components/6.5/plexus-components-6.5.pom
Downloaded from repo1: https://repo1.maven.org/maven2/org/codehaus/plexus/plexus-components/6.5/plexus-components-6.5.pom (2.7 kB at 5.5 kB/s)
Downloading from repo1: https://repo1.maven.org/maven2/org/codehaus/plexus/plexus-utils/3.5.0/plexus-utils-3.5.0.pom
Downloaded from repo1: https://repo1.maven.org/maven2/org/codehaus/plexus/plexus-utils/3.5.0/plexus-utils-3.5.0.pom (8.0 kB at 16 kB/s)
Downloading from repo1: https://repo1.maven.org/maven2/org/codehaus/plexus/plexus/10/plexus-10.pom
Downloaded from repo1: https://repo1.maven.org/maven2/org/codehaus/plexus/plexus/10/plexus-10.pom (25 kB at 46 kB/s)
Downloading from repo1: https://repo1.maven.org/maven2/org/codehaus/plexus/plexus-utils/3.5.1/plexus-utils-3.5.1.pom
Downloaded from repo1: https://repo1.maven.org/maven2/org/codehaus/plexus/plexus-utils/3.5.1/plexus-utils-3.5.1.pom (8.8 kB at 12 kB/s)
Downloading from repo1: https://repo1.maven.org/maven2/com/thoughtworks/xstream/xstream/1.4.20/xstream-1.4.20.pom
Downloaded from repo1: https://repo1.maven.org/maven2/com/thoughtworks/xstream/xstream/1.4.20/xstream-1.4.20.pom (25 kB at 29 kB/s)
Downloading from repo1: https://repo1.maven.org/maven2/com/thoughtworks/xstream/xstream-parent/1.4.20/xstream-parent-1.4.20.pom
Downloaded from repo1: https://repo1.maven.org/maven2/com/thoughtworks/xstream/xstream-parent/1.4.20/xstream-parent-1.4.20.pom (44 kB at 70 kB/s)
Downloading from repo1: https://repo1.maven.org/maven2/org/apache/maven/plugin-tools/maven-plugin-tools-generators/3.7.0/maven-plugin-tools-generators-3.7.0.jar
Downloading from repo1: https://repo1.maven.org/maven2/com/thoughtworks/xstream/xstream/1.4.20/xstream-1.4.20.jar
Downloading from repo1: https://repo1.maven.org/maven2/org/codehaus/plexus/plexus-interactivity-api/1.1/plexus-interactivity-api-1.1.jar
Downloading from repo1: https://repo1.maven.org/maven2/org/codehaus/plexus/plexus-utils/3.5.1/plexus-utils-3.5.1.jar
Downloading from repo1: https://repo1.maven.org/maven2/org/jsoup/jsoup/1.15.3/jsoup-1.15.3.jar
Downloaded from repo1: https://repo1.maven.org/maven2/org/apache/maven/plugin-tools/maven-plugin-tools-generators/3.7.0/maven-plugin-tools-generators-3.7.0.jar (54 kB at 83 kB/s)
Downloaded from repo1: https://repo1.maven.org/maven2/org/codehaus/plexus/plexus-interactivity-api/1.1/plexus-interactivity-api-1.1.jar (9.4 kB at 9.3 kB/s)
Downloaded from repo1: https://repo1.maven.org/maven2/org/codehaus/plexus/plexus-utils/3.5.1/plexus-utils-3.5.1.jar (269 kB at 143 kB/s)
Downloaded from repo1: https://repo1.maven.org/maven2/org/jsoup/jsoup/1.15.3/jsoup-1.15.3.jar (438 kB at 205 kB/s)
Downloaded from repo1: https://repo1.maven.org/maven2/com/thoughtworks/xstream/xstream/1.4.20/xstream-1.4.20.jar (645 kB at 233 kB/s)
[INFO]
===============================================================================
========================= Platform Properties Details =========================
===============================================================================

===============================================================================
System Properties
===============================================================================

java.runtime.name=Java(TM) SE Runtime Environment
sun.boot.library.path=D:\Program_Files\Java\jdk1.8.0_261\jre\bin
java.vm.version=25.261-b12
java.vm.vendor=Oracle Corporation
maven.multiModuleProjectDirectory=C:\Users\Johnny
java.vendor.url=http://java.oracle.com/
path.separator=;
guice.disable.misplaced.annotation.check=true
java.vm.name=Java HotSpot(TM) 64-Bit Server VM
file.encoding.pkg=sun.io
user.script=
user.country=CN
sun.java.launcher=SUN_STANDARD
sun.os.patch.level=
java.vm.specification.name=Java Virtual Machine Specification
user.dir=C:\Users\Johnny
java.runtime.version=1.8.0_261-b12
java.awt.graphicsenv=sun.awt.Win32GraphicsEnvironment
java.endorsed.dirs=D:\Program_Files\Java\jdk1.8.0_261\jre\lib\endorsed
os.arch=amd64
java.io.tmpdir=C:\Users\Johnny\AppData\Local\Temp\
line.separator=

java.vm.specification.vendor=Oracle Corporation
user.variant=
os.name=Windows 10
classworlds.conf=D:\Program Files(x86)\Maven\apache-maven-3.6.3-self\bin\..\bin\m2.conf
sun.jnu.encoding=GBK
java.library.path=D:\Program_Files\Java\jdk1.8.0_261\bin;C:\WINDOWS\Sun\Java\bin;C:\WINDOWS\system32;C:\WINDOWS;D:\Program Files(x86)\NetSaRang\XmanagerPowerSuite6\Xlpd 6\;D:\Program Files(x86)\NetSaRang\XmanagerPowerSuite6\Xmanager 6\;D:\Program Files(x86)\NetSaRang\Xftp 6\;D:\Program Files(x86)\NetSaRang\Xshell 6\;C:\Windows\system32;C:\Windows;C:\Windows\System32\Wbem;C:\Windows\System32\WindowsPowerShell\v1.0\;C:\Windows\System32\OpenSSH\;C:\Program Files (x86)\NVIDIA Corporation\PhysX\Common;C:\Program Files\NVIDIA Corporation\NVIDIA NGX;C:\Program Files\NVIDIA Corporation\NVIDIA NvDLISR;C:\WINDOWS\system32;C:\WINDOWS;C:\WINDOWS\System32\Wbem;C:\WINDOWS\System32\WindowsPowerShell\v1.0\;C:\WINDOWS\System32\OpenSSH\;D:\Program_Files\Java\jdk1.8.0_261\bin;D:\Program_Files\Java\jdk1.8.0_261\jre\bin;D:\Program Files(x86)\Maven\apache-maven-3.6.3-self\bin;D:\Program Files(x86)\Python\3.8.5;D:\Program Files(x86)\Apache Tomcat\apache-tomcat-8.5.33\lib;D:\Program Files(x86)\Apache Tomcat\apache-tomcat-8.5.33\bin;D:\Program Files(x86)\Wechat;D:\Program Files(x86)\WXWechat\WXWork;D:\Program Files(x86)\Foxmail;D:\Program Files(x86)\QQ\Bin;D:\Program Files(x86)\HBuilderX\HBuilderX;D:\Program Files(x86)\Snipaste;D:\Program Files(x86)\Git\Git\cmd;D:\Program Files(x86)\TortoiseSVN\bin;D:\Program Files(x86)\SPSS\SPSS Statistics 24_64\JRE\bin;D:\Program Files(x86)\Apache Tomcat\Muiti-Tomcats\apache-tomcat-8.5.33-A\lib;D:\Program Files(x86)\Apache Tomcat\Muiti-Tomcats\apache-tomcat-8.5.33-B\lib;D:\Program Files(x86)\Apache Tomcat\Muiti-Tomcats\apache-tomcat-8.5.33-C\lib;D:\Program Files(x86)\Apache Tomcat\Muiti-Tomcats\apache-tomcat-8.5.33-A\bin;D:\Program Files(x86)\Apache Tomcat\Muiti-Tomcats\apache-tomcat-8.5.33-B\bin;D:\Program Files(x86)\Apache Tomcat\Muiti-Tomcats\apache-tomcat-8.5.33-C\bin;D:\Program Files(x86)\Microsoft VS Code\Microsoft VS Code\bin;D:\Program Files(x86)\Redis;D:\Program Files(x86)\WinSCP\WinSCP\;D:\Program Files(x86)\Oracle-Client\SQLPlus\instantclient_11_2;D:\Program Files(x86)\MySQL\mysql-5.7.19-winx64\bin;D:\Program Files(x86)\Java Decomplier;D:\Program Files(x86)\Android-SDK\platform-tools;D:\Program Files(x86)\MongoDB\mongodb-win32-x86_64-2008plus-ssl-3.6.5\bin;D:\Program_Files\fscan;D:\Program Files(x86)\DiskGenius\DiskGenius;D:\Program_Files\Scala\scala-2.13.8\bin;D:\Program_Files\Spark\spark-2.4.8-bin-hadoop2.7\bin;D:\Program_Files\hadoop\hadoop-2.10.1\bin;D:\Program Files(x86)\Git\Git\\usr\bin\;D:\Program_Files\Flume\apache-flume-1.7.0-bin\conf;D:\Program_Files\Flume\apache-flume-1.7.0-bin\bin;D:\Program_Files\netcat;D:\Program_Files\hive\apache-hive-2.1.1-bin\bin;D:\Program_Files\Apache-Zookeeper\apache-zookeeper-3.8.0-bin\bin;D:\Program_Files\Kafka\kafka_2.12-0.10.2.0\bin;D:\Program_Files\Kafka\kafka_2.12-0.10.2.0\bin\windows;D:\Program_Files\Flink\flink-1.9.3\bin\;D:\Program_Files\WeChatDevTools\dll;C:\Users\Johnny\AppData\Local\Microsoft\WindowsApps;;D:\Program Files(x86)\Microsoft VS Code\Microsoft VS Code\bin;D:\Program Files(x86)\PyCharm\PyCharm 2019.1.3\bin;;C:\Program Files\Bandizip\;C:\Users\Johnny\AppData\Local\Programs\Fiddler;D:\Program_Files\Nmap;.
maven.conf=D:\Program Files(x86)\Maven\apache-maven-3.6.3-self\bin\../conf
java.specification.name=Java Platform API Specification
java.class.version=52.0
sun.management.compiler=HotSpot 64-Bit Tiered Compilers
os.version=10.0
library.jansi.path=D:\Program Files(x86)\Maven\apache-maven-3.6.3-self\bin\..\lib\jansi-native
user.home=C:\Users\Johnny
user.timezone=Asia/Shanghai
java.awt.printerjob=sun.awt.windows.WPrinterJob
java.specification.version=1.8
file.encoding=GBK
user.name=Johnny
java.class.path=D:\Program Files(x86)\Maven\apache-maven-3.6.3-self\bin\..\boot\plexus-classworlds-2.6.0.jar
java.vm.specification.version=1.8
sun.arch.data.model=64
java.home=D:\Program_Files\Java\jdk1.8.0_261\jre
sun.java.command=org.codehaus.plexus.classworlds.launcher.Launcher help:system
java.specification.vendor=Oracle Corporation
user.language=zh
awt.toolkit=sun.awt.windows.WToolkit
java.vm.info=mixed mode
java.version=1.8.0_261
java.ext.dirs=D:\Program_Files\Java\jdk1.8.0_261\jre\lib\ext;C:\WINDOWS\Sun\Java\lib\ext
sun.boot.class.path=D:\Program_Files\Java\jdk1.8.0_261\jre\lib\resources.jar;D:\Program_Files\Java\jdk1.8.0_261\jre\lib\rt.jar;D:\Program_Files\Java\jdk1.8.0_261\jre\lib\sunrsasign.jar;D:\Program_Files\Java\jdk1.8.0_261\jre\lib\jsse.jar;D:\Program_Files\Java\jdk1.8.0_261\jre\lib\jce.jar;D:\Program_Files\Java\jdk1.8.0_261\jre\lib\charsets.jar;D:\Program_Files\Java\jdk1.8.0_261\jre\lib\jfr.jar;D:\Program_Files\Java\jdk1.8.0_261\jre\classes
sun.stderr.encoding=ms936
java.vendor=Oracle Corporation
maven.home=D:\Program Files(x86)\Maven\apache-maven-3.6.3-self\bin\..
file.separator=\
java.vendor.url.bug=http://bugreport.sun.com/bugreport/
sun.cpu.endian=little
sun.io.unicode.encoding=UnicodeLittle
sun.stdout.encoding=ms936
sun.desktop=windows
sun.cpu.isalist=amd64

===============================================================================
Environment Variables
===============================================================================

COMSPEC=C:\WINDOWS\system32\cmd.exe
EXCELPATH=C:\Users\Johnny\Desktop\sample-data.xlsx
NUMBER_OF_PROCESSORS=8
SYSTEMROOT=C:\WINDOWS
PATH=D:\Program Files(x86)\NetSaRang\XmanagerPowerSuite6\Xlpd 6\;D:\Program Files(x86)\NetSaRang\XmanagerPowerSuite6\Xmanager 6\;D:\Program Files(x86)\NetSaRang\Xftp 6\;D:\Program Files(x86)\NetSaRang\Xshell 6\;C:\Windows\system32;C:\Windows;C:\Windows\System32\Wbem;C:\Windows\System32\WindowsPowerShell\v1.0\;C:\Windows\System32\OpenSSH\;C:\Program Files (x86)\NVIDIA Corporation\PhysX\Common;C:\Program Files\NVIDIA Corporation\NVIDIA NGX;C:\Program Files\NVIDIA Corporation\NVIDIA NvDLISR;C:\WINDOWS\system32;C:\WINDOWS;C:\WINDOWS\System32\Wbem;C:\WINDOWS\System32\WindowsPowerShell\v1.0\;C:\WINDOWS\System32\OpenSSH\;D:\Program_Files\Java\jdk1.8.0_261\bin;D:\Program_Files\Java\jdk1.8.0_261\jre\bin;D:\Program Files(x86)\Maven\apache-maven-3.6.3-self\bin;D:\Program Files(x86)\Python\3.8.5;D:\Program Files(x86)\Apache Tomcat\apache-tomcat-8.5.33\lib;D:\Program Files(x86)\Apache Tomcat\apache-tomcat-8.5.33\bin;D:\Program Files(x86)\Wechat;D:\Program Files(x86)\WXWechat\WXWork;D:\Program Files(x86)\Foxmail;D:\Program Files(x86)\QQ\Bin;D:\Program Files(x86)\HBuilderX\HBuilderX;D:\Program Files(x86)\Snipaste;D:\Program Files(x86)\Git\Git\cmd;D:\Program Files(x86)\TortoiseSVN\bin;D:\Program Files(x86)\SPSS\SPSS Statistics 24_64\JRE\bin;D:\Program Files(x86)\Apache Tomcat\Muiti-Tomcats\apache-tomcat-8.5.33-A\lib;D:\Program Files(x86)\Apache Tomcat\Muiti-Tomcats\apache-tomcat-8.5.33-B\lib;D:\Program Files(x86)\Apache Tomcat\Muiti-Tomcats\apache-tomcat-8.5.33-C\lib;D:\Program Files(x86)\Apache Tomcat\Muiti-Tomcats\apache-tomcat-8.5.33-A\bin;D:\Program Files(x86)\Apache Tomcat\Muiti-Tomcats\apache-tomcat-8.5.33-B\bin;D:\Program Files(x86)\Apache Tomcat\Muiti-Tomcats\apache-tomcat-8.5.33-C\bin;D:\Program Files(x86)\Microsoft VS Code\Microsoft VS Code\bin;D:\Program Files(x86)\Redis;D:\Program Files(x86)\WinSCP\WinSCP\;D:\Program Files(x86)\Oracle-Client\SQLPlus\instantclient_11_2;D:\Program Files(x86)\MySQL\mysql-5.7.19-winx64\bin;D:\Program Files(x86)\Java Decomplier;D:\Program Files(x86)\Android-SDK\platform-tools;D:\Program Files(x86)\MongoDB\mongodb-win32-x86_64-2008plus-ssl-3.6.5\bin;D:\Program_Files\fscan;D:\Program Files(x86)\DiskGenius\DiskGenius;D:\Program_Files\Scala\scala-2.13.8\bin;D:\Program_Files\Spark\spark-2.4.8-bin-hadoop2.7\bin;D:\Program_Files\hadoop\hadoop-2.10.1\bin;D:\Program Files(x86)\Git\Git\\usr\bin\;D:\Program_Files\Flume\apache-flume-1.7.0-bin\conf;D:\Program_Files\Flume\apache-flume-1.7.0-bin\bin;D:\Program_Files\netcat;D:\Program_Files\hive\apache-hive-2.1.1-bin\bin;D:\Program_Files\Apache-Zookeeper\apache-zookeeper-3.8.0-bin\bin;D:\Program_Files\Kafka\kafka_2.12-0.10.2.0\bin;D:\Program_Files\Kafka\kafka_2.12-0.10.2.0\bin\windows;D:\Program_Files\Flink\flink-1.9.3\bin\;D:\Program_Files\WeChatDevTools\dll;C:\Users\Johnny\AppData\Local\Microsoft\WindowsApps;;D:\Program Files(x86)\Microsoft VS Code\Microsoft VS Code\bin;D:\Program Files(x86)\PyCharm\PyCharm 2019.1.3\bin;;C:\Program Files\Bandizip\;C:\Users\Johnny\AppData\Local\Programs\Fiddler;D:\Program_Files\Nmap
CLASSWORLDS_LAUNCHER=org.codehaus.plexus.classworlds.launcher.Launcher
PSMODULEPATH=C:\Program Files\WindowsPowerShell\Modules;C:\WINDOWS\system32\WindowsPowerShell\v1.0\Modules
PYCHARM=D:\Program Files(x86)\PyCharm\PyCharm 2019.1.3\bin;
PATHEXT=.COM;.EXE;.BAT;.CMD;.VBS;.VBE;.JS;.JSE;.WSF;.WSH;.MSC
FLINK_HOME=D:\Program_Files\Flink\flink-1.9.3
TEMP=C:\Users\Johnny\AppData\Local\Temp
MAVEN_CMD_LINE_ARGS=help:system
PROGRAMW6432=C:\Program Files
WXDRIVE_START_ARGS=--wxdrive-setting=0 --disable-gpu --disable-software-rasterizer --enable-features=NetworkServiceInProcess
CLASSWORLDS_JAR="D:\Program Files(x86)\Maven\apache-maven-3.6.3-self\bin\..\boot\plexus-classworlds-2.6.0.jar"
KAFKA_HOME=D:\Program_Files\Kafka\kafka_2.12-0.10.2.0
DRIVERDATA=C:\Windows\System32\Drivers\DriverData
PROMPT=$P$G
JVMCONFIG=\.mvn\jvm.config
MYSQL_HOME=D:\Program Files(x86)\MySQL\mysql-5.7.19-winx64
LOCALAPPDATA=C:\Users\Johnny\AppData\Local
PROCESSOR_LEVEL=6
USERNAME=Johnny
CONFIGSETROOT=C:\WINDOWS\ConfigSetRoot
HOMEDRIVE=C:
SYSTEMDRIVE=C:
COMMONPROGRAMFILES(X86)=C:\Program Files (x86)\Common Files
ALLUSERSPROFILE=C:\ProgramData
CATALINA_HOME=D:\Program Files(x86)\Apache Tomcat\apache-tomcat-8.5.33
WINDIR=C:\WINDOWS
ONEDRIVE=C:\Users\Johnny\OneDrive
TMP=C:\Users\Johnny\AppData\Local\Temp
SPARK_HOME=D:\Program_Files\Spark\spark-2.4.8-bin-hadoop2.7
JAVACMD=D:\Program_Files\Java\jdk1.8.0_261\bin\java.exe
USERDOMAIN_ROAMINGPROFILE=LAPTOP-RFPOFJM7
ZOOKEEPER_HOME=D:\Program_Files\Apache-Zookeeper\apache-zookeeper-3.8.0-bin
JAVA_HOME=D:\Program_Files\Java\jdk1.8.0_261
MAVEN_PROJECTBASEDIR=C:\Users\Johnny
HOMEPATH=\Users\Johnny
HADOOP_CONF_DIR=D:\Program_Files\hadoop\hadoop-2.10.1\etc\hadoop\
COMPUTERNAME=LAPTOP-RFPOFJM7
PUBLIC=C:\Users\Public
HADOOP_HOME=D:\Program_Files\hadoop\hadoop-2.10.1
USERPROFILE=C:\Users\Johnny
ASL.LOG=Destination=file
CLASSPATH=;D:\Program_Files\Java\jdk1.8.0_261\lib;D:\Program_Files\Java\jdk1.8.0_261\lib\dt.jar;D:\Program_Files\Java\jdk1.8.0_261\lib\tools.jar
APPDATA=C:\Users\Johnny\AppData\Roaming
COMMONPROGRAMFILES=C:\Program Files\Common Files
PROCESSOR_ARCHITECTURE=AMD64
SSO_CLIENT_DIRECTORY=c:\sso_client\
SESSIONNAME=Console
PROCESSOR_IDENTIFIER=Intel64 Family 6 Model 158 Stepping 10, GenuineIntel
EXEC_DIR=C:\Users\Johnny
CATALINA_BASE=D:\Program Files(x86)\Apache Tomcat\apache-tomcat-8.5.33
OS=Windows_NT
ERROR_CODE=0
CATALINA_BASE_C=D:\Program Files(x86)\Apache Tomcat\Muiti-Tomcats\apache-tomcat-8.5.33-C
CATALINA_BASE_B=D:\Program Files(x86)\Apache Tomcat\Muiti-Tomcats\apache-tomcat-8.5.33-B
SCALA_HOME=D:\Program_Files\Scala\scala-2.13.8
CATALINA_BASE_A=D:\Program Files(x86)\Apache Tomcat\Muiti-Tomcats\apache-tomcat-8.5.33-A
LOGONSERVER=\\LAPTOP-RFPOFJM7
COMMONPROGRAMW6432=C:\Program Files\Common Files
USERDOMAIN=LAPTOP-RFPOFJM7
HIVE_HOME=D:\Program_Files\hive\apache-hive-2.1.1-bin
MAVEN_HOME=D:\Program Files(x86)\Maven\apache-maven-3.6.3-self\bin\..
HADOOP_USER_NAME=hdfs
PROCESSOR_REVISION=9e0a
ANDROID_HOME=D:\Program Files(x86)\Android-SDK
PROGRAMFILES(X86)=C:\Program Files (x86)
WDIR=C:\
PROGRAMDATA=C:\ProgramData
=C:=C:\Users\Johnny
GIT_HOME=D:\Program Files(x86)\Git\Git\
CATALINA_HOME_C=D:\Program Files(x86)\Apache Tomcat\Muiti-Tomcats\apache-tomcat-8.5.33-C
CATALINA_HOME_B=D:\Program Files(x86)\Apache Tomcat\Muiti-Tomcats\apache-tomcat-8.5.33-B
CATALINA_HOME_A=D:\Program Files(x86)\Apache Tomcat\Muiti-Tomcats\apache-tomcat-8.5.33-A
PROGRAMFILES=C:\Program Files
FLUME_HOME=D:\Program_Files\Flume\apache-flume-1.7.0-bin

[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  19.953 s
[INFO] Finished at: 2023-04-12T01:00:55+08:00
[INFO] ------------------------------------------------------------------------
```

## CASE2 打包插件 `org.apache.maven.plugins:maven-assembly-plugin`
+ [maven中使用assembly打包 - CSDN](https://blog.csdn.net/qq_36319965/article/details/122453545)
+ [Maven中将项目打包成Zip，conf、lib、jar分离 - CSDN](https://blog.csdn.net/llb_3601478/article/details/117574841)