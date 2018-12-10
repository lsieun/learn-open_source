Some of you might have been already using Apache Spark in your day-to-day life and might have been wondering if I have Spark why I need to use Flink? The question is quite expected and the comparison is natural. Let me try to answer that in brief. 

The very first thing we need to understand here is Flink is based on the **streaming first principle** which means it is real streaming processing engine and not a fast processing engine that collects streams as mini batches. 

Flink considers batch processing as a special case of streaming whereas it is vice-versa in the case of Spark. 