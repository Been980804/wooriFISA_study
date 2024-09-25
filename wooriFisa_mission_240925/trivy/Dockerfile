FROM openjdk:17
COPY . /usr/src/myapp
WORKDIR /usr/src/myapp
RUN javac Trivy.java
CMD ["java", "Trivy"]