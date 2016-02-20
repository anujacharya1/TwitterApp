#!/bin/bash

adb -d shell 'run-as com.anuj.twitter cat /data/data/com.anuj.twitter/databases/Twitter.db> /sdcard/Twitter.db'
adb pull /sdcard/Twitter.db ~/Desktop/