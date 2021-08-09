#!/bin/bash

./gradlew :kpower:createDistributable

./uninstall.sh

sudo cp -r kpower/build/compose/binaries/main/app/kpower /opt/

sudo ln -s /opt/kpower/bin/kpower /usr/bin/

echo Success