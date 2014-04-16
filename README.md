SentimentalTwitterAndroid
=========================

 * Tweets searched for are limited to English, as the Sentiment search language is restricted to English too.
 * Only up to 10 tweets are requested, as Sentiment API only allows 500 "free" queries per day, after that they start charging.
   This value can be changed in strings.xml (max_tweets)
 * The MaShape key needs to be specified, and is located in "strings.xml" (mashape_key)
   If no key specified, a Toast message is shown when searching, and all faces are Indifferent