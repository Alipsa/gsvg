#!/usr/bin/env bash
set -e

SDKMAN_DIR="${1:-$HOME/.sdkman}"
if [[ ! -d "$SDKMAN_DIR" ]]; then
    echo "SDKMAN is not installed in $SDKMAN_DIR. Please install SDKMAN first."
    exit 1
fi

# shellcheck disable=SC1090
source "$SDKMAN_DIR/bin/sdkman-init.sh"

JAVA_MAJORS=(17 21 25)
GROOVY_MAJORS=(4 5)
JAVA_VENDOR_PATTERN=${JAVA_VENDOR_PATTERN:-"librca"}

EXAMPLES_BASE=${EXAMPLES_BASE:-"gsvg-examples/src/main/groovy"}
EXAMPLES_DIR=${EXAMPLES_DIR:-"$EXAMPLES_BASE/examples/10-practical-use-cases"}
if [[ -z "${EXAMPLES_WORKDIR:-}" ]]; then
    if [[ "$EXAMPLES_DIR" == *"/src/main/groovy"* ]]; then
        EXAMPLES_WORKDIR="${EXAMPLES_DIR%%/src/main/groovy*}"
    else
        EXAMPLES_WORKDIR="."
    fi
fi

latest_java_version() {
    local major="$1"
    sdk list java | awk -F'|' '
        {
            ver=$NF
            gsub(/^[[:space:]]+|[[:space:]]+$/, "", ver)
            if (ver ~ /^[0-9]/) {
                print ver
            }
        }' | awk -v major="$major" -v vendor="$JAVA_VENDOR_PATTERN" '
        $0 ~ ("^" major "\\.") && $0 ~ vendor { print }' | sort -V | tail -n1
}

latest_groovy_version() {
    local major="$1"
    sdk list groovy | awk '
        match($0, /[0-9]+(\.[0-9]+)+([-a-z0-9.]+)?/, m) {
            print m[0]
        }' | awk -v major="$major" '$0 ~ ("^" major "\\.")' | sort -V | tail -n1
}

ensure_installed() {
    local candidate="$1"
    local version="$2"
    if ! sdk home "$candidate" "$version" >/dev/null 2>&1; then
        sdk install "$candidate" "$version"
    fi
}

java_versions=()
for major in "${JAVA_MAJORS[@]}"; do
    version="$(latest_java_version "$major")"
    if [[ -z "$version" ]]; then
        echo "Unable to determine latest Java $major version via SDKMAN for vendor pattern '${JAVA_VENDOR_PATTERN}'."
        exit 1
    fi
    ensure_installed java "$version"
    java_versions+=("$version")
done

groovy_versions=()
for major in "${GROOVY_MAJORS[@]}"; do
    version="$(latest_groovy_version "$major")"
    if [[ -z "$version" ]]; then
        echo "Unable to determine latest Groovy $major.x version via SDKMAN."
        exit 1
    fi
    ensure_installed groovy "$version"
    groovy_versions+=("$version")
done

if [[ ! -d "$EXAMPLES_DIR" ]]; then
    echo "Examples directory not found: $EXAMPLES_DIR"
    exit 1
fi

mapfile -t example_scripts < <(find "$EXAMPLES_DIR" -maxdepth 1 -type f -name '*.groovy' | sort)
if [[ ${#example_scripts[@]} -eq 0 ]]; then
    echo "No Groovy scripts found in $EXAMPLES_DIR"
    exit 1
fi

for java_version in "${java_versions[@]}"; do
    sdk use java "$java_version" >/dev/null
    for groovy_version in "${groovy_versions[@]}"; do
        sdk use groovy "$groovy_version" >/dev/null
        echo "==> Running examples with Java $java_version and Groovy $groovy_version"
        (
            cd "$EXAMPLES_WORKDIR"
            for script in "${example_scripts[@]}"; do
                script_rel="${script#${EXAMPLES_WORKDIR}/}"
                echo "Running ${script_rel}"
                groovy -Djava.awt.headless=true "$script_rel"
            done
        )
    done
done
