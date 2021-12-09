#基础镜像
FROM java:8
#作者
MAINTAINER xuxu
#打包的jar
ADD @{app-name}.jar /opt/app/@{app-name}.jar
#把arthas镜像导入
COPY --from=hengyunabc/arthas:latest /opt/arthas /opt/arthas
#JVM环境参数 
ENV JAVA_OPTS=@{jvm.params}
#启动命令 删除无用镜像
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS  -jar /opt/app/@{app-name}.jar" ]
#暴露端口ll
EXPOSE @{app-port}