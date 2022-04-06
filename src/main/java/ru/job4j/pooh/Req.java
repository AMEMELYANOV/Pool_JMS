package ru.job4j.pooh;

public class Req {

    private final String httpRequestType;
    private final String poohMode;
    private final String sourceName;
    private final String param;

    public Req(String httpRequestType, String poohMode, String sourceName, String param) {
        this.httpRequestType = httpRequestType;
        this.poohMode = poohMode;
        this.sourceName = sourceName;
        this.param = param;
    }

    public static Req of(String content) {
        String[] contentArr = content.split(System.lineSeparator());
        String firstLine = contentArr[0];
        String httpRequestType = firstLine.split(" ")[0];

        if (!"POST".equals(httpRequestType) && !"GET".equals(httpRequestType)) {
            throw new IllegalArgumentException("Bad Request. Method must be GET or POST.");
        }

        String poohMode = firstLine.split("/")[1];
        String sourceName = firstLine.split("/")[2].split(" ")[0];
        String param = "";

        if ("POST".equals(httpRequestType)) {
            param = contentArr[contentArr.length - 1];
        }
        if ("GET".equals(httpRequestType) && "topic".equals(poohMode)) {
            param = firstLine.split("/")[3].split(" ")[0];
        }
        return new Req(httpRequestType, poohMode, sourceName, param);
    }

    public String httpRequestType() {
        return httpRequestType;
    }

    public String getPoohMode() {
        return poohMode;
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getParam() {
        return param;
    }
}