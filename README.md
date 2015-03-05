# Assignment 1
In this assignment, you are asked to implement a new transaction - **UpdateItemPriceTxn**, which will randomly select some items and update their price, in the benchmark project.

## Steps
To complete this assignment, you need to

1. Fork the Assignment 1 project
2. Trace the benchmark project code yourself
3. Implement the **UpdateItemPriceTxn** using JDBC and stored procedures
4. Modify the RTE so that you can control the ratio between **SampleTxn** and **UpdateItemPriceTxn**. (You can now compare the performance difference between different read-only / read-write transaction ratio. For example, 40% **SampleTxn** and 60% **UpdateItemPriceTxn**)
5. Run a few experiments and write a report

### UpdateItemPriceTxn 
In the assignment 1 project, the SchemaBuilder and TestbedLoader are already provided. If you try to `load()` the testbed, it will create an **item** table and populate 100000 items. You should implement a new transaction - **UpdateItemPriceTxn**, which will randomly select some items and update their price values. The detailed information of this transaction is described as following:

- Randomly select 10 item ids
- Randomly generate 10 new price values
- For each selected item, update their `i_price` to the new price value

### The report
- How you implement the transaction using JDBC and stored procedures briefly
- Your experiement enviornment
- The performance comparison between JDBC and stored procedures
- The performance comparison under different ratio between **SampleTxn** and **UpdateItemPriceTxn**
- Anything worth mentioning

	Note: There is no strict limitation to the length of your report. Generally, a 2~3 pages report with some figures and tables is fine.



## Submission

The procedure of submission is as following:

1. Fork our [Assignment 1](http://shwu10.cs.nthu.edu.tw/2015-cloud-database/assignment-1) on GitLab
2. Clone the repository you forked
3. Finish your work and write the report
4. Commit your work, push to GitLab and then open a merge request to submit. The repository should contain
	- *[Project directory]*
	- *[Your student ID]*_assignment1_report.pdf (e.g. 102062563_assignment1_reprot.pdf)

## Hints
You can reference the [Introducing Benchmark Project](http://netdb-db.appspot.com/slides/tas/IV_Introducing_Benchmark_Project.pdf) on the [course website](http://netdb-db.appspot.com/). Also, you can reference the provided **SampleTxn** before implementing your own.

## Deadline
Sumbit your work before **2015/03/18 (Wed.) 23:59:59**.
