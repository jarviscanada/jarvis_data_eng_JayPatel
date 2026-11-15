#!/bin/bash

psql_host=$1
psql_port=$2
db_name=$3
psql_user=$4
psql_password=$5

# Ensures 5 parameters are passed to the script
if [ "$#" -ne 5 ]; then
    echo "Illegal number of parameters"
    exit 1
fi

# Stores CPU and machine information in variables
lscpu_out=$(lscpu)
hostname=$(hostname -f)

# Stores additional CPU and memory information in variables
cpu_number=$(echo "$lscpu_out" | egrep "^CPU\(s\):" | awk '{print $2}' | xargs)
cpu_architecture=$(echo "$lscpu_out" | egrep "^Architecture:" | awk '{print $2}' | xargs)
cpu_model=$(echo "$lscpu_out" | egrep "^Model name:" | awk -F: '{print $2}' | xargs)
cpu_mhz=$(awk -F: '/cpu MHz/ {print $2; exit}' /proc/cpuinfo | xargs)
l2_cache=$(echo "$lscpu_out" | egrep "^L2 cache:" | awk '{print $3}' | xargs)
total_mem=$(grep MemTotal /proc/meminfo | awk '{print $2}')
timestamp=$(date -u +"%Y-%m-%d %H:%M:%S")

# Creates an insert statement to insert the collected information into the host_info table in the database
insert_stmt="INSERT INTO host_info (hostname, cpu_number, cpu_architecture, cpu_model, cpu_mhz, l2_cache, \"timestamp\", total_mem)
VALUES ('$hostname', $cpu_number, '$cpu_architecture', '$cpu_model', $cpu_mhz, $l2_cache, '$timestamp', $total_mem);"

# Exports the password and executes the insert statement using psql
export PGPASSWORD=$psql_password
psql -h "$psql_host" -p "$psql_port" -d "$db_name" -U "$psql_user" -c "$insert_stmt"
exit $?