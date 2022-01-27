#!/usr/bin/env bash

./gradlew :plugin:jar

cp ./plugin/build/libs/*.jar ./sample