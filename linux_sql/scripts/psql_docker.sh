#!/bin/sh

# Command line arguments
cmd=$1
db_username=$2
db_password=$3

# Start docker if docker is not already running
sudo systemctl status docker || sudo systemctl start docker

# Check if container exists and returns an error code
docker container inspect jrvs-psql
container_status=$?

# User switch case to handle the different create|stop|start options
case $cmd in
  create)

  # Check if the container is already created
  if [ $container_status -eq 0 ]; then
		echo 'Container already exists'
		exit 1
	fi

  # Check the number of CLI arguments to make sure a username and password has been provided
  if [ $# -ne 3 ]; then
    echo 'Create requires username and password'
    exit 1
  fi
  
  # Create container
	docker volume create postgresdata
  # Start the container
  docker run --name jrvs-psql \
	  -e POSTGRES_USER="$db_username" \
	  -e POSTGRES_PASSWORD="$db_password" \
	  -d \
	  -v postgresdata:/var/lib/postgresql/data \
	  -p 5432:5432 \
	  postgres:9.6-alpine

	exit $?
	;;

  start|stop)
  # Check container status and exit 1 if container has not been created
  if [ $container_status -ne 0 ]; then
    echo 'Container has not been created'
    exit 1
  fi

  # Start or stop the container
	docker container $cmd jrvs-psql
	exit $?
	;;

  *)
	echo 'Illegal command'
	echo 'Commands: start|stop|create'
	exit 1
	;;
esac