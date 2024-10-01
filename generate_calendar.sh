#!/bin/bash

set -e

echo "Building calendar. For full options run: $0 --help"

if [ -z "$*" ]
then
    ./gradlew run --quiet
else
    ./gradlew run --args="$*" --quiet
fi
