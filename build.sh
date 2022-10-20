#!/usr/bin/env bash



cd /Users/crazyandy/Documents/codesource/kingmeter-framework/kingmeter-common
mvn clean
mvn install


cd /Users/crazyandy/Documents/codesource/kingmeter-framework/kingmeter-utils
mvn clean
mvn install


cd /Users/crazyandy/Documents/codesource/kingmeter-framework/kingmeter-dto-charging
mvn clean
mvn install



cd /Users/crazyandy/Documents/codesource/kingmeter-framework/kingmeter-socket-framework
mvn clean
mvn install



cd /Users/crazyandy/Documents/codesource/kingmeter-framework/kingmeter-socket-charging-old
mvn clean
mvn install


cd /Users/crazyandy/Documents/codesource/kingmeter-framework/kingmeter-mvc
mvn clean
mvn install


cd /Users/crazyandy/Documents/codesource/KingMeterIOTGroup/single-charging-server-old
mvn clean
mvn package