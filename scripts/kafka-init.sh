#!/usr/bin/env bash
set -e

BOOTSTRAP_SERVER="${BOOTSTRAP_SERVER:-kafka-broker:9092}"

until kafka-topics --bootstrap-server "$BOOTSTRAP_SERVER" --list; do
  echo "Waiting for Kafka..."
  sleep 2
done

kafka-topics --bootstrap-server "$BOOTSTRAP_SERVER" \
  --create \
  --if-not-exists \
  --topic telemetry-topic \
  --partitions 3 \
  --replication-factor 1