FROM mysql:latest

ENV MYSQL_DATABASE=hyk_portfolio
ENV LANG=C.UTF-8

COPY init.sql /docker-entrypoint-initdb.d
