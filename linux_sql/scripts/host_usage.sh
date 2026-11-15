#!/bin/bash

# Command line arguments
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

# Stores virtual memory statics and machine information in variables
vmstat_mb=$(vmstat --unit M)
hostname=$(hostname -f)

# Stores additional memory, CPU, and disk information in variables (and timestamp)
memory_free=$(echo "$vmstat_mb" | awk '{print $4}' | tail -n1 | xargs)
cpu_idle=$(echo "$vmstat_mb" | tail -n1 | awk '{print $15}' | xargs)
cpu_kernel=$(echo "$vmstat_mb" | tail -n1 | awk '{print $14}' | xargs)
disk_io=$(vmstat -d | tail -n1 | awk '{print $10}' | xargs)
disk_available=$(df -BM / | tail -n1 | awk '{print $4}' | tr -d 'M')

timestamp=$(date -u +"%Y-%m-%d %H:%M:%S")

# Subquery to look up this specific machine's id in host_info by its hostname
host_id="(SELECT id FROM host_info WHERE hostname='$hostname')"

# Creates an insert statement to insert the collected information into the host_usage table in the database
insert_stmt="INSERT INTO host_usage (\"timestamp\", host_id, memory_free, cpu_idle, cpu_kernel, disk_io, disk_available)
VALUES ('$timestamp', $host_id, $memory_free, $cpu_idle, $cpu_kernel, $disk_io, $disk_available);"

# Exports the password and executes the insert statement using psql
export PGPASSWORD=$psql_password
psql -h "$psql_host" -p "$psql_port" -d "$db_name" -U "$psql_user" -c "$insert_stmt"
exit $?