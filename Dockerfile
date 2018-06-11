FROM ibrahim/alpine
ADD target/programmeServiceFonctionel.jar ws_programmeServiceFonctionel_sf.jar
EXPOSE 8091
ENTRYPOINT ["java","-jar","ws_programmeServiceFonctionel_sf.jar"]