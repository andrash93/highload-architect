# Социальная сеть

    - Docker образ [Бэк + Фронт](https://hub.docker.com/repository/docker/andrash93/social-network)
    - Развернутое приложение  - http://83.102.204.46/

### Приложение состоит из backend сервиса 

    social-network - основной бэкенд соцсети
     - В качестве БД сервис использует MySQL
     - Работа ленты новостей использует Kafka + Redis(кэш)
       Пост представляет текст/автор(авторизованный пользователь)/время поста
       При добавлении поста, он добавлется в MySQL, затем отправлется в topic wall-post kafka по ключу userId.
       Consumer обновляет кеши всех подписчиков в Redis
       При запросе ленты новостей, посты берутся из redis. Если в Redis оказалось пусто(внештатная ситуация), 
       посты берутся из MySQL, добавляя в Redis
       
### Monitoring
    Prometheus
      - в качестве средства мониторинга используется Prometheus
      - графическое представление - grafana   
      - для мониторинга  MySQL, Redis  используются експортеры  Prometheus     

### Запуск проекта
    
    cd kafka 
    docker-compose up -d   
    
    cd monitoring
    docker-compose up -d 
     
    cd social-network/docker 
    docker-compose up -d
