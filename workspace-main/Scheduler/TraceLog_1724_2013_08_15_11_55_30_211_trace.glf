FILE_TYPE:DAAA96DE-B0FB-4c6e-AF7B-A445F5BF9BE2
ENCODING:UTF-8
RECORD_SEPARATOR:30
COLUMN_SEPARATOR:124
ESC_CHARACTER:27
COLUMNS:Location|Guid|Time|Tzone|Trace|Log|Importance|Severity|Exception|DeviceName|ProcessID|ThreadID|ThreadName|ScopeTag|MajorTick|MinorTick|MajorDepth|MinorDepth|RootName|RootID|CallerName|CallerID|CalleeName|CalleeID|ActionID|DSRRootContextID|DSRTransaction|DSRConnection|DSRCounter|User|ArchitectComponent|DeveloperComponent|Administrator|Unit|CSNComponent|Text
SEVERITY_MAP: |None|S|Information|W|Warning|E|Error|A|Assertion|F|Fatal
HEADER_END
|26E7C38C5F7F4F679FC947E6445FBD0B0|2013 08 15 11:55:30.268|-0400|Error| |>>| | |TraceLog| 1724|  20|pool-1-thread-2 | ||||||||||||||||||||com.crystaldecisions.sdk.framework.LocaleHelper||LanguagePacks.xml not found:/Users/ygoron/bobje/LanguagePacks.xml
java.io.FileNotFoundException
	at com.businessobjects.foundation.language.LocaleHelper.getInstalledLocales(LocaleHelper.java:127)
	at com.businessobjects.foundation.language.LangMgr.getInstalledLocales(LangMgr.java:39)
	at com.businessobjects.foundation.exception.ExceptionWorker.getSupportedLanguages(ExceptionWorker.java:356)
	at com.businessobjects.foundation.exception.ExceptionWorker.getErrorMessages(ExceptionWorker.java:299)
	at com.businessobjects.foundation.exception.ExceptionWorker.<init>(ExceptionWorker.java:132)
	at com.businessobjects.foundation.exception.CheckedException.<init>(CheckedException.java:73)
	at com.crystaldecisions.celib.exception.CEException.<init>(CEException.java:98)
	at com.crystaldecisions.celib.exception.AbstractException.<init>(AbstractException.java:87)
	at com.crystaldecisions.sdk.exception.SDKException.<init>(SDKException.java:156)
	at com.crystaldecisions.sdk.exception.SDKServerException.<init>(SDKServerException.java:95)
	at com.crystaldecisions.sdk.exception.SDKServerException.map(SDKServerException.java:107)
	at com.crystaldecisions.sdk.exception.SDKException.map(SDKException.java:196)
	at com.crystaldecisions.sdk.occa.infostore.internal.InternalInfoStore.queryHelper(InternalInfoStore.java:750)
	at com.crystaldecisions.sdk.occa.infostore.internal.InternalInfoStore.query(InternalInfoStore.java:569)
	at com.crystaldecisions.sdk.occa.infostore.internal.InfoStore.query(InfoStore.java:167)
	at com.apos.scheduler.ScheduleUtils.scheduleObject(ScheduleUtils.java:126)
	at com.apos.scheduler.ProcessorThreadType6.call(ProcessorThreadType6.java:107)
	at com.apos.scheduler.ProcessorThreadType6.call(ProcessorThreadType6.java:1)
	at java.util.concurrent.FutureTask$Sync.innerRun(FutureTask.java:303)
	at java.util.concurrent.FutureTask.run(FutureTask.java:138)
	at java.util.concurrent.ThreadPoolExecutor$Worker.runTask(ThreadPoolExecutor.java:895)
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:918)
	at java.lang.Thread.run(Thread.java:680)

|26E7C38C5F7F4F679FC947E6445FBD0B1|2013 08 15 11:55:30.273|-0400|Error| |>>| | |TraceLog| 1724|  20|pool-1-thread-2 | ||||||||||||||||||||com.businessobjects.foundation.exception.ExceptionWorker||getErrorMessage(): cannot obtain installed locales
