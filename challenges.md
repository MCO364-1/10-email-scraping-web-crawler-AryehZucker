# Challenges & Decisions:
- I made a queue that does not allow an element to be added multiple times to encapsulate the requirement not to check a site twice
- I made my scraper follow DIP so that I can test it with a mock implementation of a website
- I decided to use 4x the number of processors available for the num of threads
