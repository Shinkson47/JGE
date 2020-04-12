# [JGELDOCS] Error Management System
JGEL defines an error to be an unintended and unexpected failiure in code execution within any given method, whose side effects may directly cause other code to fail or cause undesired operation, including a total halt of the runtime.

JGEL EMS is designed to recieve and handle all errors that occour within execution, with the main goal of attempting to keep running.

A single error can be logged, and the runtime can continue - but if the error causes a cascade effect then the error tollerance will be exceeded, and the runtime will shutdown safely. 

EMS detects error cascades using time signatures, and tollerances specified in the configuration class.

In case the cascade corrupts the user data, the EMS also takes backups of save data when errors first occor, and store them seperatly to the main save file. These can be used if the safe shutdown fails to save data correctly.

A save data management tool will also be added when the backup management is developed.

# Unhandled Exception Handler
EMS Contains an exception handler to be used by threads to forwards exceptions to JGEL EMS automatically.

It can be retrieved with the API call 

```Java 
getExceptionHandler()
```
However, this practice is not recommended since threads should be created and managed by JGEL. See Thread Management.

# Manually parsing exceptions
Exceptions caught outside of JGEL can be parsed to the API via the wrapper

```Java 
EMSHandle(Exception e)
```

# Error notification
A message box can be displayed by the EMS to notify of error, and possible inexpected behaviour - and that a save data backup has been made.

All threads under JGEL's management are paused whilst this message is displayed to force the game to pause. Threads resume when the message is dismissed.

If thread interupption causes more issues for your client, the error notification can be disabled with the configuration parameter

```Java 
public static bool AllowErrorNotif = true;
```

The possibility for custom, client side notification handling may be added in the future.

# Error tollerance
The EMS will notify of errors, log them and take a backup of save data when errors occour, but from there the EMS can ignore them.

EMS can be given a tollerance integer for quantity of exceptions to ignore before taking action. If too many errors occour, and the tollerance is surpassed, the EMS will attempt an EIS.

Allowances can be altered with the configuration parameter 

```Java 
public static int ErrorMargin = 5;
```
# Cascade detection
EIS takes action if multiple errors occour within a configuration specified time frame.

If enabled, this happens automatically within the API. It's behaviour is entirely customisable using configuration propeperties.

```Java
AllowCascadeDetection
```
Determins if JGEL attempts to detect error cascades

```Java
MillisTollerance = 3000;
```
The maximum timeframe in which errors must occour to be counted.


```Java
public static int CascadeTollerance = 3;
```
The number or errors that must be exceeded before action is taken.

If EIS is enabled, then it will be invoked, else the user is warned to save and restart.

# Error induced shutdowns
An EIS shuts down JGEL systems and the client safely, including the saving of user data.

EIS' can be disabled with the configuration parameter

```Java 
public static boolean AllowEIS = true;
```

# EMS Clear
The API Call
```Java 
ClearEMS();
```
can be made to clear the EMS's memory. Logs of previous exceptions will still remain, but the EMS will forget about them.

This call is intended for resetting the error allowance for transistions within your client from different areas that could cause error, such as returning to the menu from the game.

This would help prevent errors caused on the menu from expiring the error management allowance from existing errors from the game.

# Warnings
JGEL EMS Warns log non-crucial failure discreetly. External warnings can be parsed to the API using the wrapper

```Java 
EMSWarn(String s);
```

Warnings are defined as non crucial execution problems whose side effects do not directly cause other code to fail, throw runtime exceptions or otherwise warrent stopping the client.

Warnings can be used for things such as intended cancellations / rejections within methods, or outofbounds exceptions, logging these problems to help locate issues whilst trying best not to hinder the user's experience.