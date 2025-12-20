#!/usr/bin/env bash
set -e
mvn clean package site deploy
echo "Deployed to Maven repository successfully!"