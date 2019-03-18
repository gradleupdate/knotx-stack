# A base Dockerfile for Knot.x

FROM openjdk:8u171-jre

MAINTAINER Marcin Czeczko <marcin.czeczko@gmail.com>

# Install the ps command to get the Launcher 'stop' command
# working properly
RUN apt-get update && apt-get install -y procps
COPY ./ /usr/local/
RUN chmod +x /usr/local/knotx/bin/knotx

# Set path
ENV KNOTX_HOME /usr/local/knotx
ENV PATH $KNOTX_HOME/bin:$PATH
