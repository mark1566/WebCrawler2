WebCrawler Playground
Author: Mark Asplen-Taylor
Date: 2nd July 2021

This crawler is very simple and all code is included into one class. Import into your Java IDE and compile.

You will need to include the Jsoup jar file from:

https://jsoup.org/download

Initially this was written with the Oracle documented reader and text. However that did not seem to support all URLs properly and there were some issues with regexp.
I then discovered Jsoup and decided to make my life easier. It seems to be a great package and does much of the work for you.

To run, please update the startUrl variable. By default it is populated with ttps://www.cotswoldwildlifepark.co.uk/ ... which is an interesting site and place in many ways.

The big trade off is completing this in a very short space of time, not having created a crawler before. It was certainly a bit of fun.

There are plenty of improvements that can be made:
- HTTP errors are not properly trapped. These could be detected and reported properly, for example links which do not work
- The code will try to open files too. At present I've trapped these in a very simple way. This should be more robust.
- It would be better to sort and order the output before display
- There could be better error detection in place in several places including opening the start page
- There are no junit tests with this. There really should be.

Kind regards,
Mark
