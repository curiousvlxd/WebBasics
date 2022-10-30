<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Guice Impl</title>
</head>
<body>
<h1>Додаємо до проєкту Guice</h1>
<p>
    Guice - модуль інверсії управління, впровадження залежностей,
    керування життєвим циклом об'єктів.
</p>
<p>
    Встановлення:
    Guice
    https://mvnrepository.com/artifact/com.google.inject/guice/5.1.0
    Для роботи з веб - Guice Extension (Servlet)
    https://mvnrepository.com/artifact/com.google.inject.extensions/guice-servlet/5.1.0
</p>
<p>
    Конфігурація:
    - Listener - точка входження (аналог main) - найперші коди, що
    запускаються при роботі програми:
    ConfigListener extends GuiceServletContextListener
    - Конфігуратор сервлетів
    ConfigServlets extends ServletModule
    - Конфігуратор сервісів (модуль)
    ConfigModule extends AbstractModule
</p>
<p>
    Впровадження:
    web.xml - наявний перейменовуємо на web-old.xml
    і створюємо новий. Зазначаємо наш Listener та GuiceFilter
    ConfigServlets: визначаємо задіяні фільтри та сервлети
    Помічаємо як @Singleton фільтри, сервлети та Listener
</p>
<p>
    Використання:
    Переносимо DataService у залежності (у DataFilter)
</p>
<p>
    getRequestURI(): <%= request.getRequestURI() %>
</p>
<p>
    getContextPath(): <%= request.getContextPath() %>
</p>
<p>
    getServletPath(): <%= request.getServletPath() %>
</p>
<p>
    getPathInfo(): <%= request.getPathInfo() %>
</p>
Д.З. Додати до проєкту хеш-сервіси
Забезпечити ін'єкцію хеш-перетворювача
Реалізувати сторінку, на якій можна ввести рядок та
дізнатись його хеш
* Вивести два різні хеші введеного рядку (MD5, SHA)
</body>
</html>