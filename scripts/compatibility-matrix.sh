#!/usr/bin/env bash
set -euo pipefail

# Usage:
#   GROOVY_VERSIONS="4.0.29 5.0.3" JAVA_HOME_17=/path/to/jdk17 JAVA_HOME_21=/path/to/jdk21 \
#     MAVEN_PROJECTS="-pl gsvg-core" MAVEN_ARGS="test" ./scripts/compatibility-matrix.sh
#
# Defaults:
#   GROOVY_VERSIONS = "4.0.29 5.0.3"
#   MAVEN_PROJECTS  = ""
#   MAVEN_ARGS      = "test"

GROOVY_VERSIONS=${GROOVY_VERSIONS:-"4.0.29 5.0.3"}
MAVEN_PROJECTS=${MAVEN_PROJECTS:-""}
MAVEN_ARGS=${MAVEN_ARGS:-"test"}
MAVEN_CMD=${MAVEN_CMD:-"mvn"}

JAVA_HOMES=()
for var in JAVA_HOME_17 JAVA_HOME_21 JAVA_HOME_23; do
  java_home=${!var:-}
  if [[ -n "$java_home" ]]; then
    JAVA_HOMES+=("$java_home")
  fi
done

if [[ ${#JAVA_HOMES[@]} -eq 0 ]]; then
  if [[ -n "${JAVA_HOME:-}" ]]; then
    JAVA_HOMES+=("$JAVA_HOME")
  else
    JAVA_HOMES+=("")
  fi
fi

for java_home in "${JAVA_HOMES[@]}"; do
  if [[ -n "$java_home" ]]; then
    echo "==> Using JAVA_HOME=$java_home"
    export JAVA_HOME="$java_home"
    export PATH="$JAVA_HOME/bin:$PATH"
  else
    echo "==> Using JAVA_HOME from PATH"
  fi

  for groovy_version in $GROOVY_VERSIONS; do
    echo "==> Groovy ${groovy_version}"
    $MAVEN_CMD $MAVEN_PROJECTS -Dgroovy.version="${groovy_version}" $MAVEN_ARGS
  done
done
