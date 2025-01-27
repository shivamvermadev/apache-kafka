What is a leader in apache kafka ?
A leader is responsible for handling all reads and writes requests for partitions in a topic. It leads and manages all operations for topic partitions.
If a kafka client wants to write a message into a topic partition, it will need to go through a leader. Once the message is successfully persisted in topic partition, it will be replicated to other kafka brokers that acts as followers.
Followers replicate data from the leader in exact order it was written, maintaining data consistency accross cluster.
So leader is a single source of truth for all writes and reads in a topic partition.
If followers are allowed to accept read and writes, it could lead to incosistencies and conflict because different followers might have different versions of data.


What is a follower ?
A follower in kafka is a replica of a partition. It copies all the data from the leader. These followers are important for redundancy and fault tolerance.


It is not always that the same broker is a leader, and all other kafka brokers are always followers. If the same kafka broker is always a leader, this will mean that all read and write operations are always done through the same server and this would create a bottleneck.

Each partition will have a leader assigned to it, and a leader to a partition is assigned when the topic is created. Kafka through its internal processes assigns a leader for each partition right away, so each partition will have its own leader and followers.
