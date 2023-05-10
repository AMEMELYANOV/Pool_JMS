package ru.job4j.pool.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Модель данных ответ
 *
 * @author Alexander Emelyanov
 * @version 1.0
 */
@AllArgsConstructor
@Getter
public class Resp {

    /**
     * Текст ответа
     */
    private final String text;

    /**
     * Статус ответа
     */
    private final String status;
}