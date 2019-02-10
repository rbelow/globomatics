#!/usr/bin/env bash
# Shebang
# See: https://stackoverflow.com/questions/10376206/what-is-the-preferred-bash-shebang

# Start Globomatics in Linux OSs

cat ./doh.txt

# Colors
# See: https://stackoverflow.com/questions/5947742/how-to-change-the-output-color-of-echo-in-linux
GREEN='\033[0;32m'
BLUE='\033[0;34m'
YELLOW='\033[1;33m'
NC='\033[0m' # no color

# LOG_ERROR=../log_error.txt

start_angular()
{
    # Bash try..catch
    # See: https://stackoverflow.com/questions/22009364/is-there-a-try-catch-command-in-bash
    {
        cd bike-ui &&
        npm ci &&
        npm start
    } || {
        echo "[ERROR] Starting Angular: `date`" >> ../log_error.txt
        set -e
    }
}

start_spring()
{
    {
        cd bike &&
        ./mvnw clean install &&
        ./mvnw spring-boot:run
    } || {
        echo "[ERROR] Starting Spring: `date`" >> ../log_error.txt
        set -e
    }
}

open_browser()
{
    {
        firefox --new-window http://localhost:4200
        firefox --new-tab http://localhost:8080
    } || {
        echo "[ERROR] Opening browser: `date`" >> ../log_error.txt
    }

    # macOS
    # See: https://stackoverflow.com/questions/34796888/how-to-open-google-chrome-from-terminal
    # open -a "Google Chrome" http://localhost:4200
    # open -a "Google Chrome" http://localhost:8080
}

export -f start_angular
export -f start_spring
export -f open_browser

echo -e "[${GREEN}HELLO${NC}] $USERNAME, today is: `date` - Happy coding!"

# Open new terminal for each process
# See: https://stackoverflow.com/questions/23002132/running-a-bash-function-in-gnome-terminal-x
echo -e "[${BLUE}INFO${NC}] Starting Angular in new terminal"
gnome-terminal -x bash -c start_angular

echo -e "[${BLUE}INFO${NC}] Starting Spring in new terminal"
gnome-terminal -x bash -c start_spring

# Opening Chrome blocks the process so execute it outside
echo -e "[${BLUE}INFO${NC}] Opening browser in new terminal"
gnome-terminal -x bash -c open_browser

echo -e "[${BLUE}INFO${NC}] Having trouble? Try killing all bash instances with: ${YELLOW}pkill -f bash${NC}"

# echo "$USERNAME bike-ui and bike started and ready for development"
