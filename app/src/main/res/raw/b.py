import os,sys
import time
list_cmmd = ['adb shell input tap 440 1640 ',
'adb shell  input tap 640 340',
'adb shell   input tap 440 1840',
'adb shell  input tap 340 340',
'adb shell  input tap 340 1440',
'adb shell  input tap 540 1240',
'adb shell  input tap 540 1840']
for items in list_cmmd:
    print items,os.system(items)
    time.sleep(4)
