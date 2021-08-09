#!/bin/bash

./gradlew :kmenu:createDistributable

./uninstallKMenu.sh

sudo cp -r kmenu/build/compose/binaries/main/app/kmenu /opt/

sudo ln -s /opt/kmenu/bin/kmenu /usr/bin/

echo Success