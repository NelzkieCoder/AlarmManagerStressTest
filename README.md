AlarmManagerStressTest
======================

Stress test how many alarms you can set at once (spoiler: it's a lot!)

You can play with `ALARM_DELAY` and `MAX_ALARMS` to test this yourself. On the moto x (1st gen), I was seeing about 3k alarms set before `alarmManager.set()` started slowing down severly.
