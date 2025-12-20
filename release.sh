#!/usr/bin/env bash
set -e
mvn -Prelease clean package site deploy
echo "Deployed to Maven repository successfully!"