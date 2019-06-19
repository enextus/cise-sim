#!/usr/bin/env bash

# VARIABLES ###
CISEEMU_HOME="$( cd "$( dirname "${BASH_SOURCE[0]}" )/.." && pwd )"
JAVA_EXEC=`which java`
JAVA_VERSION=`${JAVA_EXEC} -version 2>&1 | head -n 1 | awk '{ print $3 }'`
NOHUP_EXEC=`which nohup`
PID_DIR=${CISEEMU_HOME}/tmp
CISEEMU_PID_FILE=${PID_DIR}/emulatorsender.pid
DEBUG_PORT=9999

# FUNCTIONS ###
function log_exit_msg {

test $? -eq 0 && echo ">>> sender started at `date -Iseconds`" || \
    echo "xxx sender not started"; exit 1

}

function log_start_msg {

echo ">>> CISE emulator sender"
echo ">>> Java path:    ${JAVA_EXEC}"
echo ">>> Java version: ${JAVA_VERSION}"

}

function log_debug_start_msg {

log_start_msg
echo ">>> DEBUG is ON listening on port ${DEBUG_PORT}"
echo ">>> the server is now waiting for a connection from a java remote "
echo ">>> debugger to the port ${DEBUG_PORT}"

}

function setup_debug {

export JAVA_OPTS="-Xdebug -agentlib:jdwp=transport=dt_socket,address=${DEBUG_PORT},server=n,suspend=y"

}

function start {
echo '<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Starting SSH Service>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>'
service ssh start
${NOHUP_EXEC} ${CISEEMU_RUN_CMD} > ${CISEEMU_HOME}/logs/localhost.log 2>&1 &
pid=$!
sleep 1
kill -0 ${pid} > /dev/null 2>&1
echo ${pid} > ${CISEEMU_PID_FILE}



echo ">>> 'tail -100f logs/localhost.log' will check the server log files"
echo
log_exit_msg

}

function run {
echo '<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Starting SSH Service>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>'
service ssh start
echo '<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Finished starting SSH Service>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>'
${CISEEMU_RUN_CMD} 2>&1

}

function CISEEMU_run_cmd {

if [ "$JAVA_VERSION" == "1.9" ]; then
    JAVA_OPTS="${JAVA_OPTS} --add-modules java.xml.bind"
fi

CISEEMU_RUN_CMD="${JAVA_EXEC} ${JAVA_OPTS} -Dconfdir=${CISEEMU_HOME}/conf/ \
-Djava.io.tmpdir=${CISEEMU_HOME}/tmp -jar ${CISEEMU_HOME}/lib/cise-emu-app-0.0.1-SNAPSHOT.jar sender \
${CISEEMU_HOME}/conf/AisModifiedPush.xml"

}

# MAIN ###
cd ${CISEEMU_HOME}

echo

CISEEMU_run_cmd

case $1 in
    start)
        log_start_msg
        start
        exit 0
        ;;
    run)
        log_start_msg
        run
        exit 0
        ;;
    debug-start)
        setup_debug
        log_debug_start_msg
        CISEEMU_run_cmd
        start
        exit 0
        ;;
    debug-run)
        setup_debug
        log_debug_start_msg
        CISEEMU_run_cmd
        run
        exit 0
        ;;
    stop)
        CISEEMU_PIDS=`ps aux | grep sender.jar | grep -v grep | awk '{ printf $2" " }'`

        (kill -15 ${CISEEMU_PIDS} 2>&1) > /dev/null && \
            echo ">>> sender has been stopped" || echo "xxx the sender was not running"

        rm -f ${CISEEMU_PID_FILE}
        ;;
    restart)
        $0 stop
        sleep 1
        $0 start
        ;;
    status)
        test `ps aux | grep sender.jar | grep -v grep | wc -l` -eq 0 && \
            echo "xxx sender is stopped" || echo ">>> sender is running"
        ;;
    *)
        echo "Usage: sender.sh {start|stop|restart|debug-start|debug-run|status}"
        exit 1
        ;;
esac

echo

exit 0