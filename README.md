[![Build Status](https://app.travis-ci.com/AMEMELYANOV/Pooh-JMS.svg?branch=master)](https://app.travis-ci.com/AMEMELYANOV/Pooh-JMS)
[![codecov](https://codecov.io/gh/AMEMELYANOV/Pooh-JMS/branch/main/graph/badge.svg?token=O2dMZg5eD0)](https://codecov.io/gh/AMEMELYANOV/Pooh-JMS)
# Pooh_JMS

## Описание проекта
Приложение является аналогом асинхронной очереди, ожидает клиентов 
через запущенный Socket. Работа осуществляется через HTTP протокол. 
Клиенты могут быть двух типов: отправители (publisher) и  получатели (subscriber).

# Функционал:
- Добавление сообщений в очереди посредством POST-методов (publisher).
- Получение сообщений из очередй посредством GET-методов (subscriber).
- Добавление тем, подписчиков.
# Применяемые технологии:
* Java 14 (Multithreading)
* JUnit
# Применяемые инструменты:
* Maven
* Checkstyle