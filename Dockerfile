# 使用华为云获取镜像
# 阿里云官方镜像FROM registry.cn-hangzhou.aliyuncs.com/opensource/graalvm:22
FROM crpi-ywhwp11e0hd1cfoz.cn-chengdu.personal.cr.aliyuncs.com/kais/openjdk:22

# 安装 Tesseract OCR
RUN apt-get update && \
    apt-get install -y tesseract-ocr tesseract-ocr-chi-sim && \
    apt-get clean

WORKDIR /app

#设置编码和时区
ENV LANG=C.UTF-8
ENV LC_ALL=C.UTF-8
ENV TZ=Asia/Shanghai

# 复制 JAR
COPY build/libs/backend-core-0.1.0.jar app.jar

EXPOSE 8010

# 运行（标准 JVM 模式）
ENTRYPOINT ["java", "-jar", "app.jar"]