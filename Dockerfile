FROM azul/zulu-openjdk-alpine:11-jre
LABEL maintainer="Alessandro.LA-PORTA@ext.ec.europa.eu" \
      maintainer="Giancarlo.PACE@ec.europa.eu"

# Create a group and user
#RUN addgroup -S appgroup && adduser -S appuser -G appgroup

# All future commands should run as the appuser user
#USER appuser

RUN apk update &&\
    apk upgrade &&\
    apk add bash

COPY target/cise-sim-1.3.0-ALPHA.tar.gz /srv/cise-sim-1.3.0-ALPHA.tar.gz
RUN mkdir -p /srv/cise-simulator && tar xvfz /srv/cise-sim-1.3.0-ALPHA.tar.gz -C /srv/cise-simulator --strip-components 1

# 9999 is the debug port. It sholdn't be exposed
EXPOSE 8080 8081 9999

WORKDIR /srv/cise-simulator

VOLUME /srv/cise-simulator/conf /srv/cise-simulator/logs /srv/cise-simulator/msghistory /srv/cise-simulator/templates

ENTRYPOINT ["/srv/cise-simulator/sim"]
CMD ["run"]
