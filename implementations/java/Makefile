.PHONY: build test run infra-up

build:
	./mvnw clean package -DskipTests

test:
	./mvnw test

run:
	./mvnw spring-boot:run

infra-up:
	docker-compose up -d
