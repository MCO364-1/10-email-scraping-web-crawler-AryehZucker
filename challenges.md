# Challenges & Decisions:
- I made a queue that does not allow an element to be added multiple times to encapsulate the requirement not to check a site twice
- I made my scraper follow DIP so that I can test it with a mock implementation of a website
- I decided to use 4x the number of processors available for the num of threads
- I had an issue that some links would be too long to fit in the database field which would prevent the emails from being saved. I made the database field larger and added a try-catch so that the program wouldn't exit on the first SQLException
- The scraper was finding image file names with an '@' in them thinking they were emails, so I made the regex filter more restrictive to include only a handful of TLDs
