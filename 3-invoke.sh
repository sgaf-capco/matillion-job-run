#!/bin/bash
set -eo pipefail
FUNCTION=$(aws cloudformation describe-stack-resource --stack-name matillion-job-run --logical-resource-id function --query 'StackResourceDetail.PhysicalResourceId' --output text)

aws lambda invoke --function-name $FUNCTION --payload '{"demoTableNameSuffix": "demo_suffix", "demoTableName":"training_airports_demo"}' out.json

cat out.json
echo ""
