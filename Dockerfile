FROM amazoncorretto:21
COPY build/libs/github-codechallenge-*.war /github-codechallenge.war
EXPOSE 8080
CMD java -jar /github-codechallenge.war 2>&1
