# Momentum Batch Management

Momentum Batch Management (MBM) is a distributed management system for batch jobs. MBM is based on micro-services and collect job execution, step execution
and job logging events centrally and displays them in an intuitive React app.

Other then JSR-352 based batch management systems, MBM is highly distributed and implemented using self-contained services. The actual batch jobs are run as
indenpendent pieces of code, which are only loosly coupled with the central system. Worthwhile to mention is also, that different to JSR-352 based batch
systems, the small agents are the only constantly running pieces of code. The actual batch jobs are implemented as executable JAR files or as docker images,
which can run in a cloud environment. When they finished their work, they will simple shutdown until the next execution.

See the [wiki](https://github.com/vogje01/mbm-batch/wiki) on how to compile, install and configure the batch management system.

# Screenshots
Job execution list with an open step execution list:
![mbm01](https://user-images.githubusercontent.com/18611563/84593606-49143f80-ae4d-11ea-9971-59edd8b45d24.png)