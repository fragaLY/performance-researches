FROM postgres:16.1-alpine AS dumper

COPY a2b-dump.sql /docker-entrypoint-initdb.d/

RUN ["sed", "-i", "s/exec \"$@\"/echo \"skipping...\"/", "/usr/local/bin/docker-entrypoint.sh"]

ENV POSTGRES_USER=user
ENV POSTGRES_PASSWORD=password
ENV PGDATA=/data

RUN ["/usr/local/bin/docker-entrypoint.sh", "postgres"]

# final build stage
FROM postgres:16.1-alpine

COPY --from=dumper /data $PGDATA