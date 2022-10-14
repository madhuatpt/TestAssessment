Part one: UI Test
-----------------------
Pre-Requisities:
==> Ext Libraries
	- jUnit.jar
	- Selenium Server [4.0.0]
	- Selenium Server Standalone [4.0.0]


PPart two: API Test
-----------------------
jMeter Script - GetswAPI.jmx
Pre-Requisites:
==> To open the script, kindly install the Custom Thread Groups plugin
How the Script Works:
==> When the user executes the script -
	1) RegExp Extractor captures the skin color for R2D2
	2) JSR223 Assertion - asserts the value captured in RegExp Extractor and if the result is unexpected then the test is marked fail.
	3) Whilst the response assertion also asserts the response

