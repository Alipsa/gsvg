#!/usr/bin/env bash
set -e
mvn clean site deploy
echo "Deployed to Maven repository successfully!"