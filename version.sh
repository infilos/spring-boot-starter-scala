#!/usr/bin/env bash

set -e

show_usage() {
  echo "Usage: $(basename "$0") [-h|--help] <binary>
where :
  -h| --help Display this help text
  -b Increase artifact's build-version.
  -s Update spring-boot release version.
" 1>&2
  exit 1
}

increase_build() {
  eval "mvn build-helper:parse-version versions:set -DnewVersion='\${parsedVersion.majorVersion}.\${parsedVersion.minorVersion}.\${parsedVersion.incrementalVersion}-\${parsedVersion.nextBuildNumber}' -DgenerateBackupPoms=false versions:commit"
}

update_spring_boot() {
  eval "mvn versions:set-property -Dproperty=spring.boot.version -DnewVersion=${SPRING_BOOT_V}.RELEASE versions:commit"
  eval "mvn versions:set -DnewVersion=${SPRING_BOOT_V}-0 -DprocessAllModules -DgenerateBackupPoms=false versions:commit"
}

if [[ ($# -eq 1) && ($1 == "-b") ]]; then
  increase_build
elif [[ ($# -eq 2) && ($1 == "-s") ]]; then
  SPRING_BOOT_V=$2
  update_spring_boot
elif [[ ($# -ne 2) || ($1 == "--help") || $1 == "-h" ]]; then
  show_usage
fi
