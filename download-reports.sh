#!/usr/bin/env bash

set -e

crowd_token=$1
plan_key=$2
start_index=$3
end_index=$4

if [ -z $crowd_token ] || [ -z $plan_key ] || [ -z $start_index ] || [ -z $end_index ]; then
	echo "ERROR: Arguments are missing."
	echo
	echo "Usage: ${0} <crowd_token> <plan_key> <start_index> <end_index>"
	exit 1;
fi


artifact_path="artifact/JOB1/Report-Log/report.log"
base_url="https://bamtwo.apps.seibert-media.net"
test_url="${base_url}/browse/${plan_key}-${start_index}/${artifact_path}"
test_response_code=$(wget --no-check-certificate --header "Cookie: crowd.token_key=${crowd_token}"  --server-response ${test_url} 2>&1 | awk '/^  HTTP/{print $2}')

report_name="${plan_key}-${start_index}-${end_index}"


echo $test_response_code

if [[ $test_response_code != 200 ]]; then
	echo "ERROR: Expected response code 200 but got ${test_response_code}"
	exit 1
fi


for i in `seq ${start_index} ${end_index}`;
do
    key="${plan_key}-${i}"
	url="${base_url}/browse/${key}/${artifact_path}"
    wget --no-check-certificate --header "Cookie: crowd.token_key=${crowd_token}"  -O $key.log $url

    if [[ $i == $start_index ]]; then
    	head -n 1 $key.log > $report_name.log
    fi

    tail -n +2 $key.log >> $report_name.log
done


mkdir -p "${report_name}"

mv ${plan_key}-*.log "${report_name}"



 
