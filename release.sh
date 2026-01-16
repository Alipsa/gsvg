#!/usr/bin/env bash
set -e
if (command -v jdk17 >/dev/null 2>&1); then
    echo "Using jdk17 from PATH"
else
    echo "jdk17 not found in PATH. Please install jdk17 and ensure it's available in your PATH."
    exit 1
fi
. jdk17
mvn -Prelease,run-examples clean verify site deploy
echo "Deployed to Maven repository successfully!"
