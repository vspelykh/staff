# Используем базовый образ с JDK 17
FROM eclipse-temurin:17-jdk-alpine as builder

# Устанавливаем рабочую директорию внутри контейнера
WORKDIR /app

# Копируем файлы проекта в контейнер
COPY . .

# Скачиваем зависимости и собираем приложение
RUN ./mvnw clean package -DskipTests

# Этап для создания минимального образа
FROM eclipse-temurin:17-jre-alpine

# Устанавливаем рабочую директорию для финального образа
WORKDIR /app

# Копируем собранный jar-файл из предыдущего этапа
COPY --from=builder /app/target/*.jar app.jar

# Указываем команду для запуска приложения
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
