package ru.job4j.pool.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Модель данных запрос
 *
 * @author Alexander Emelyanov
 * @version 1.0
 */
@AllArgsConstructor
@Getter
@Setter
public class Req {

    /**
     * Тип запроса
     */
    private final String httpRequestType;

    /**
     * Режим работы
     */
    private final String poolMode;

    /**
     * Имя топика или очереди
     */
    private final String sourceName;

    /**
     * Содержимое запроса
     */
    private final String param;

    /**
     * Получает и парсит строку с данными запроса, создавая
     * из входных данных объект запроса Req.
     *
     * @param content строка с данными запроса
     * @return объект запроса
     */
    public static Req of(String content) {
        String[] contentArr = content.split(System.lineSeparator());
        String firstLine = contentArr[0];
        String httpRequestType = firstLine.split(" ")[0];

        if (!"POST".equals(httpRequestType) && !"GET".equals(httpRequestType)) {
            throw new IllegalArgumentException("Bad Request. Method must be GET or POST.");
        }

        String poolMode = firstLine.split("/")[1];
        String sourceName = firstLine.split("/")[2].split(" ")[0];
        String param = "";

        if ("POST".equals(httpRequestType)) {
            param = contentArr[contentArr.length - 1];
        }
        if ("GET".equals(httpRequestType) && "topic".equals(poolMode)) {
            param = firstLine.split("/")[3].split(" ")[0];
        }
        return new Req(httpRequestType, poolMode, sourceName, param);
    }
}