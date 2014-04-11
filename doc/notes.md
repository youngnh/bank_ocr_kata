After 2 user stories:
I'm kind of surprised that I got away with "parsing" digits by matching triples of strings.
There are likely better representations for a 8-segment digit out there, but so far I haven't been forced to move to any of them.
The checksum story was pretty simple with the built-in functions that Clojure comes with out of the box. I could see that being more typing-work in a language like Java.
For just over an hour's worth of work, I'm fairly happy that I've written so far works and hasn't limited my ability to extend the code to the new user stories.
We'll see if that holds up :D

I now have use for a better digit representation, so we'll see how flexible the code I put in place originally is as I finish User Story 4 as quickly as I can.
Then I intend on going back over everything and trying to make the code cleaner. It doesn't help any future maintainers to have to figure out twisted pages of threading macros.