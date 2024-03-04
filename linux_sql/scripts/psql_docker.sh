#! /bin/sh

# Capture CLI arguments
cmd=$1
db_username=$2
db_password=$3

#start docker
sudo systemctl status docker || sudo systemctl start docker #DONE

# Check container status (try the following cmds on terminal)
docker container inspect jarvis_psql
container_status=$? #$? hold exit status of last exicuted command

# User switch case to handle create|stop|start opetions
case $cmd in
  create)
  # Check if the container is already created
  if [ $container_status -eq 0 ]; then
  	echo 'Container already exists'
  	exit 1
  fi
  # Check # of CLI arguments
  if [ $# -ne 3 ]; then #checks if the number of command-line arguments ($#) is not equal to (-ne means "not equal") 3.
     echo 'Create requires username and password'
     exit 1
  fi
  # Create container
  docker volume create pg_vol #DONE
  # Start the container
  docker run --name jarvis_psql -e POSTGRES_PASSWORD=$db_password -e POSTGRES_USER=$db_username -d -v pg_vol:/var/lib/postgresql/data -p 5432:5432 postgres:9.6-alpine #DONE
  # Make sure you understand what's `$?`
  exit $? #will exit with exit status of last exicuted command
  ;;

  start|stop)
  # Check instance status; exit 1 if container has not been created
  if [ $container_status -ne 0 ]; then #DONE
    echo 'Container does not exist' #DONE
    exit 1   #DONE
  fi  #DONE
  # Start or stop the container
  docker container $cmd jarvis_psql
  exit $?
  ;;
  *)
  	echo 'Illegal command'
  	echo 'Commands: start|stop|create'
  	exit 1
  	;;
esac