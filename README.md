IR
==

improve google+ search efficiency
Project title: Improve search with Google+ 
Background: Google+ generates lots of interests because it is from Google, because it is social network, and because Google integrate Google+ into its search results. The goal of this project is to explore an implementation of Google+ into Google search. We will learn a lot about both Google services after finishing this project. 
Outcome: an online search engine for searching Google with the extra information from Google+.
Google+  provide a close interest  platform for user, they share  and post what they love, but  they have limited activities or friends, there are a few stuffs which can trigger their interests.
In this project, I imported  Google+ API , Custom Search, Web Search, Image Search API, also follow  and overwrite Lucene's to build Index , and read Index,  use BM25 formula to calculate and sort  results, except context-based search, import  P-Hash method to make image pattern recognition.

1.Get Similar results from your plusers
Find similar activities 's people who used to plus your activities before , get all their own  activities, It is the first keyword search, show results by action, such as "post" and  "share"; the second column is to show relevance activities according to your searched keyword.   
2.Expand your search in custom google search and web google search

when the first search has done,  there are a few results, even no result shown in the page, so it is necessary to expand search scope to get more  similar related results from custom search engine, kind of recommendation, you  search in your google+ scope, and also and extend to any other website which you want. 

3.Image Pattern Recognition

When you mouseover the custom searched image, it mean popup a div box,  remind you to input a search scope , a url of website , which you can search all <img> tags in to match similar images.
Use P-HASH function, get reference work from Elliot Shepherd.
