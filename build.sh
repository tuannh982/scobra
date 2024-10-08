#!/bin/bash -u
set -eo pipefail

bin/sbt clean +compile +test:compile fmt +test:scalafmt +test