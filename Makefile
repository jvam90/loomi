# Makefile for Loomi project

COMPOSE := docker compose
MVNW := ./mvnw
APP_SERVICE := app
POSTGRES_CONTAINER := loomi-postgres
IMAGE_NAME := loomi:21

.PHONY: help setup up down build test clean logs db-migrate

help:
	@echo "Available targets:"
	@echo "  make setup       # Build docker images (app + deps)"
	@echo "  make up          # Start infrastructure (docker compose up -d)"
	@echo "  make down        # Stop and remove containers"
	@echo "  make build       # Build the application (Maven package)"
	@echo "  make test        # Run tests (Maven)"
	@echo "  make clean       # Stop containers, remove volumes and built artifacts"
	@echo "  make logs        # Follow application logs"
	@echo "  make db-migrate  # Run SQL migration scripts against the DB container"

# Build docker images defined in docker-compose
setup:
	@echo "Building docker images..."
	$(COMPOSE) build --pull

# Start infra
up:
	@echo "Starting services..."
	$(COMPOSE) up -d --remove-orphans

# Stop infra
down:
	@echo "Stopping services..."
	$(COMPOSE) down

# Build the application jar
build:
	@echo "Building application..."
	$(MVNW) -B -DskipTests package

# Run tests
test:
	@echo "Running tests..."
	$(MVNW) test

# Clean containers, volumes and build artifacts
clean:
	@echo "Tearing down containers and removing volumes..."
	$(COMPOSE) down -v --remove-orphans
	@echo "Removing built artifacts and image (if present)..."
	-rm -rf target
	-docker image rm $(IMAGE_NAME) || true

# Follow app logs (default to the production app service)
logs:
	@echo "Tailing logs for service: $(APP_SERVICE)"
	$(COMPOSE) logs -f $(APP_SERVICE)

# Run SQL migration script (insert_stuff.sql if present) against postgres container
# Copies local file into container and executes it with psql
db-migrate:
	@if [ -f ./insert_stuff.sql ]; then \
		echo "Applying insert_stuff.sql to $(POSTGRES_CONTAINER)"; \
		docker cp ./insert_stuff.sql $(POSTGRES_CONTAINER):/tmp/insert_stuff.sql; \
		docker exec -i $(POSTGRES_CONTAINER) psql -U postgres -d loomi -f /tmp/insert_stuff.sql; \
	else \
		echo "No insert_stuff.sql found in project root. Nothing to migrate."; \
	fi
