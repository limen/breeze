# Breeze 

A light and enjoyable job broker.

## Features

- Supports instant jobs and delayed jobs.
- Could deploy to a singleton or a cluster.
- Every job has its own executor, which points to the proxy who really do the job.

## Light

- In singleton mode, mysql/pgsql is the only dependency.
- In cluster mode, mysql/pgsql and redis are depended.
- Low resource required, memory usage usually less than 500 MB.

## Security

- Every single modification is audited.
- Authorize via token.

## Performance

## Concepts

### Executor

proxy who really do the job, such as a http service. 

### Coordinator

Coordinates the clock and assigns duty to JSD.

### Job scanner dispatcher(JSD)

get duty from coordinator and dispatch to JS 

### Job scanner(JS)

scans the job sequence and deliver id to job queue 

### Job queue

holds job ids being executed. FIFO.

### Job consumption

Utilize thread pool to improve throughput.

## How the delayed job implemented

Job is identified by an id, which is a (long) value, and is 
positively correlated to its execution time.

Job id is computed by its execution time and an auto-increment sequence number.
Set the job's execution timestamp to 1632041675, and the sequence number is 1, 
job id is computed by

```
1632041675 << 20 + 1
```

When got the timestamp, we got the lowest job id and highest job id for it

```
1632041675 << 20 + 1
1632041675 << 20 + (1 << 20 - 1)
```

The truth is near.

The job is stored in a table whose primary key is the job id. When the clock arrives
 a timestamp, the job scanner starts to scan the table by the primary key which in the range
[lowest job id, highest job id].

Got it!

## How the cluster implemented

The cluster utilizes Redis to sync information.

In the cluster, every node could be a coordinator or a follower 
and all the nodes are equal.

Follower does the work to consume jobs in the job queue 
and prepares to be coordinator periodically.

Coordinator is also a follower and takes more work. It scans the job table
and dispatch job ids to the queue.

Coordinator's duty period is very short, normally one second. 


