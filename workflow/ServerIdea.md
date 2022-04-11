# Server Idea

This File Contains My Ideas about how server will work, or still working :)

> My Idea Is Currently Unstable I am still thinking about how will make that working

## Current Server Flow Will Be

### Server Starts With 4 Threads

- Main thread will be used to Map Controllers then run the server to accept more connections
- A thread to read the data from the client and send it when it's available
- 2 threads which is active only in case there is clients connected else would be blocked/paused

### How it gonna work

Will my idea is to create the main thread to do our normal mapping and save it in memory then start server and wait for
clients when a client connected it will unblock the thread if blocked and start read data this thread has only to read
and write which means when read it will check only the HttpMethod if Get/Head Will pass it to their thread if
POST/PUT/DELETE will pass them to their thread.

I guess by that I will add more servants to the server to reduce the blocking into the thread by make the main thread
can accept more clients and read meanwhile the other 2 threads working on them by the type of it.

### Things Running inside my head before writing Code

- What if something big running like database?
    - my answer for that question not clear yet, but I have 3 ideas
        - first is to start by creating a connection to database on every connection (Will be like blocked mode so not
          preferable to me.
        - create a database instance and use it on every client will make the connection is busy all the time would be
          worse
        - Invoke a way to create a database pool which contains number of database connections which is on idle mode (
          Using Data Source)
- What if I wanted to send something big like file like 2gb or something?
    - In such case I will go unfortunately to create a thread to handle it.

So A small Summery

- Thread To Handle fast requests. Else create a thread to handle it so speed of the request depends on how much time
  will be needed and how many steps

## How will I program it then?

It's a quite simple, When our main thread is done doing its work, it will run my main server and wait for connections,
its connection will get a unique id to write data to client moment is ready, then ready the second thread read data and
determine which http request type then send it to its thread. On each thread there is some problems like we can't define
how much time the request will take or the type of the request...etc. So my idea is using ThreadPool contains like 2 to
4 threads, on each and queue mode to handle tht request 
